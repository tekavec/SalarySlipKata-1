package org.neomatrix369.salaryslip.national_insurance;

import org.neomatrix369.salaryslip.components.Money;

public class NationalInsuranceBand {
  private final Money lowerLimit;
  private final double rate;

  public NationalInsuranceBand(Money lowerLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.rate = rate;
  }

  public Money calculateContributionsFor(Money annualSalary) {
    final Money excessIncome = annualSalary.subtract(lowerLimit);

    if (excessIncome.isGreaterThanZero()) {
      return excessIncome.times(rate);
    }

    return new Money(0.00);
  }
}
