package org.neomatrix369.salaryslip.national_insurance;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Money;

public class NationalInsuranceCalculator {
  private static final int TWELVE_MONTHS = 12;

  private static final double TWO_PERCENT = 0.02;
  private static final double TWELVE_PERCENT = 0.12;

  private NationalInsuranceBand higherContributionsBand =
      new NationalInsuranceBand(new Money(43_000.00), new Money(MAX_VALUE), TWO_PERCENT);
  private NationalInsuranceBand basicContributionsBand =
      new NationalInsuranceBand(new Money( 8_060.00), new Money(43_000.00), TWELVE_PERCENT);

  public Money calculateMonthlyContributionsFor(Money annualSalary) {
    final Money higherContributions = higherContributionsBand.calculateContributionsFor(annualSalary);
    final Money basicContributions = basicContributionsBand.calculateContributionsFor(annualSalary);

    return basicContributions.add(higherContributions).divideBy(TWELVE_MONTHS);
  }
}
