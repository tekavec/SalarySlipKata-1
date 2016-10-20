package org.neomatrix369.salaryslip.national_insurance;

import static org.neomatrix369.salaryslip.components.Money.smallerOf;
import static java.lang.Double.MAX_VALUE;
import static java.lang.String.format;

import org.neomatrix369.salaryslip.components.Money;

public enum NationalInsuranceBand {
  BASIC_CONTRIBUTIONS_BAND(new Money(8_060.00), new Money(43_000.00), Rate.TWELVE_PERCENT),
  HIGHER_CONTRIBUTIONS_BAND(new Money(43_000.00), new Money(MAX_VALUE), Rate.TWO_PERCENT);

  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  NationalInsuranceBand(Money lowerLimit, Money upperLimit, double rate) {
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

  private static class Rate {
    private static final double TWO_PERCENT = 0.02;
    private static final double TWELVE_PERCENT = 0.12;
  }
}
