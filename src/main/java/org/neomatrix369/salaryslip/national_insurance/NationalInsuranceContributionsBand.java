package org.neomatrix369.salaryslip.national_insurance;

import static org.neomatrix369.salaryslip.components.Money.minimum;

import org.neomatrix369.salaryslip.components.Money;

public class NationalInsuranceContributionsBand {

  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  public NationalInsuranceContributionsBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  public Money calculateContributionsFor(Money annualSalary) {
    Money minimumOfTheTwoLimits = minimum(annualSalary, upperLimit);
    Money excessIncome = minimumOfTheTwoLimits.subtract(lowerLimit);

    return excessIncome.times(rate);
  }
}
