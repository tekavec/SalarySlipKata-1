package org.neomatrix369.salaryslip.national_insurance;

import static org.neomatrix369.salaryslip.components.Money.smallerOf;
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

  public Money calculateContributionsFor(Money annualSalary) {
    return amountWithinBandFor(annualSalary).times(rate);
  }

  private Money amountWithinBandFor(Money annualSalary) {
    return smallerOf(annualSalary, upperLimit).subtract(lowerLimit);
  }

  @Override
  public String toString() {
    return format("lowerLimit=%s, upperLimit=%s, rate=%s",
        lowerLimit, upperLimit, rate);
  }
}
