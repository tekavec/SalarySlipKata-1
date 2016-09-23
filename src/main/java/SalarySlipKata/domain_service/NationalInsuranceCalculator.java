package SalarySlipKata.domain_service;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;

public class NationalInsuranceCalculator {

  private static final int TWELVE_MONTHS = 12;

  public Money calculateMonthlyContributionsFor(Money annualSalary) {
    return calculateContributionsFor(annualSalary).divideBy(TWELVE_MONTHS);
  }

  private Money calculateContributionsFor(Money originalAnnualSalary) {
    Money annualSalary = new Money(originalAnnualSalary);
    Money contributions = zero();

    for(Rates contribution: Rates.values()) {
      Money difference = differenceBetween(annualSalary, contribution.limit);

      contributions = contributions.plus(
          calculateContribution(contribution.rate, difference)
      );

      annualSalary = annualSalary.minus(difference);
    }

    return contributions;
  }

  private Money calculateContribution(Double contributionRate, Money difference) {
    return difference.multiplyBy(contributionRate);
  }

  private Money differenceBetween(Money annualSalary, Money contributionStartAmount) {
    return annualSalary.minus(contributionStartAmount);
  }

  private enum Rates {
    HIGHER_RATE(new Money(43_000.00), 0.02),
    BASIC_RATE(new Money(8_060.00), 0.12);

    private final Money limit;
    private final double rate;

    Rates(Money limit, double rate) {
      this.limit = limit;
      this.rate = rate;
    }
  }
}
