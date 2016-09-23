package SalarySlipKata.domain_service;

import static java.lang.Double.valueOf;
import static SalarySlipKata.domain.Money.zero;

import java.util.LinkedHashMap;
import java.util.Map;

import SalarySlipKata.domain.Money;

public class NationalInsuranceCalculator {
  private static final Map<Money, Double> RATES_TABLE = new LinkedHashMap<Money, Double>() {
    { put(new Money(43_000.00), valueOf(0.02)); }
    { put( new Money(8_060.00), valueOf(0.12)); }
  };

  private static final int TWELVE_MONTHS = 12;

  public Money calculateMonthlyContributionsFor(Money annualSalary) {
    return calculateContributionsFor(annualSalary).divideBy(TWELVE_MONTHS);
  }

  private Money calculateContributionsFor(Money originalAnnualSalary) {
    Money annualSalary = new Money(originalAnnualSalary);
    Money contributions = zero();

    for(Map.Entry<Money, Double> band: RATES_TABLE.entrySet()) {
      Money difference = differenceBetween(annualSalary, band.getKey());

      contributions = contributions.plus(
          calculateContribution(band.getValue(), difference)
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
}
