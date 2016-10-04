package org.neomatrix369.salaryslip.national_insurance;

import static org.neomatrix369.salaryslip.components.Money.minimumOf;

import static java.lang.String.format;

import org.neomatrix369.salaryslip.components.Money;

public class NationalInsuranceBand {

  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  public NationalInsuranceBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  public Money calculateContributionsFrom(Money annualSalary) {
    final Money excessIncomeForThisBand = calculateExcessFrom(annualSalary, upperLimit, lowerLimit);
    return excessIncomeForThisBand.times(rate);
  }

  private Money calculateExcessFrom(Money annualSalary, Money upperLimit, Money lowerLimit) {
    Money minimumOfTheTwoLimits = minimumOf(annualSalary, upperLimit);
    return minimumOfTheTwoLimits.subtract(lowerLimit);
  }

  @Override
  public String toString() {
    return format("lowerLimit=%s, upperLimit=%s, rate=%s", lowerLimit, upperLimit, rate);
  }
}
