package org.neomatrix369.salaryslip.national_insurance;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Money;

public class NationalInsuranceCalculator {
  private static final int TWELVE_MONTHS = 12;

  final private NationalInsuranceContributionsBand higherContributionsBand;
  final private NationalInsuranceContributionsBand basicContributionsBand;

  public NationalInsuranceCalculator() {
    higherContributionsBand =
        new NationalInsuranceContributionsBand(new Money(43_000.00), new Money(MAX_VALUE), 0.02);
    basicContributionsBand =
        new NationalInsuranceContributionsBand(new Money( 8_060.00), new Money(43_000.00), 0.12);
  }

  public Money monthlyNationalInsuranceContributionsFor(Money annualSalary) {
    return convertToMonthly(nationalInsuranceContributions(annualSalary));
  }

  private Money convertToMonthly(Money amount) {return amount.divisionBy(TWELVE_MONTHS);}

  private Money nationalInsuranceContributions(Money annualSalary) {
    return higherContributionsBand.calculateContributionsFor(annualSalary)
        .add(basicContributionsBand.calculateContributionsFor(annualSalary));
  }
}
