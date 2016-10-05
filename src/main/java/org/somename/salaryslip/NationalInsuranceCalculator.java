package org.somename.salaryslip;

import org.somename.salaryslip.components.Money;
import org.somename.salaryslip.national_insurance.NationalInsuranceBand;

public class NationalInsuranceCalculator {
  private static final int TWELVE_MONTHS = 12;

  private NationalInsuranceBand basicContributionsBand =
      new NationalInsuranceBand(new Money(8_060), new Money(43_000.00), 0.12);
  private NationalInsuranceBand higherContributionsBand =
      new NationalInsuranceBand(new Money(43_000.00), new Money(Double.MAX_VALUE), 0.02);


  public Money calculateMonthlyContributionsFor(Money annualSalary) {
    return monthly(calculateContributionsFor(annualSalary));
  }

  private Money calculateContributionsFor(Money annualSalary) {
    return basicContributionsBand.calculateContributionsFor(annualSalary)
        .add(higherContributionsBand.calculateContributionsFor(annualSalary));
  }

  private Money monthly(Money amount) {
    return amount.divisionBy(TWELVE_MONTHS);
  }
}
