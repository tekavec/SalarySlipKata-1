package SalarySlipKata.domain_service;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;

public class NationalInsuranceCalculator {

  private enum ContributionBands {
    HIGHER_CONTRIBUTION(new Money(43_000.00), 0.02),
    BASIC_CONTRIBUTION(  new Money(8_060.00), 0.12),
    NO_CONTRIBUTION(         new Money(0.00), 0.0);

    private final Money threshold;
    private final double rate;

    ContributionBands(Money threshold, double rate) {
      this.threshold = threshold;
      this.rate = rate;
    }
  }

  public Money calculateContributionsFor(Money originalSalary) {
    Money remainingSalary = new Money(originalSalary);
    Money contributions = zero();

    for(ContributionBands contribution: ContributionBands.values()) {
      Money excessIncomeForBand = calculateExcessIncomeForBandWith(remainingSalary, contribution.threshold);

      contributions = contributions.plus(
          calculateContribution(contribution.rate, excessIncomeForBand)
      );

      remainingSalary = remainingSalary.minus(excessIncomeForBand);
    }

    return contributions;
  }

  private Money calculateContribution(Double contributionRate, Money difference) {
    return difference.multiplyBy(contributionRate);
  }

  private Money calculateExcessIncomeForBandWith(Money annualSalary, Money contributionStartAmount) {
    return annualSalary.minus(contributionStartAmount);
  }
}
