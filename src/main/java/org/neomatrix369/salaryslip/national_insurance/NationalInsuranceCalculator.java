package org.neomatrix369.salaryslip.national_insurance;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Money;

public class NationalInsuranceCalculator {
  private static final int TWELVE_MONTHS = 12;

  private NationalInsuranceBand basicContributions =
      new NationalInsuranceBand(new Money( 8_060.00), new Money(43_000.00), 0.12);
  private NationalInsuranceBand higherContributions =
      new NationalInsuranceBand(new Money(43_000.00), new Money(MAX_VALUE), 0.02);

  public Money calculateMonthlyContributionsFor(Money annualSalary) {
    final Money totalContributions = basicContributions.calculateContributionsFor(annualSalary)
        .add(higherContributions.calculateContributionsFor(annualSalary));

    return convertToMonthly(totalContributions);
  }

  private Money convertToMonthly(Money amount) {return amount.divisionBy(TWELVE_MONTHS);}
}
