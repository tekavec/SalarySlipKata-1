package org.neomatrix369.salaryslip.national_insurance;

import static org.neomatrix369.salaryslip.components.Money.minimum;

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
    final Money excess = calculateExcessFrom(annualSalary, upperLimit, lowerLimit);
    return excess.multiplyBy(rate);
  }

  private Money calculateExcessFrom(Money annualSalary, Money upperLimit, Money lowerLimit) {
    Money actualUpperLimit = minimum(annualSalary, upperLimit);
    return actualUpperLimit.minus(lowerLimit);
  }

  @Override
  public String toString() {
    return String.format("lowerLimit=%s, upperLimit=%s, rate=%s", lowerLimit, upperLimit, rate);
  }
}
