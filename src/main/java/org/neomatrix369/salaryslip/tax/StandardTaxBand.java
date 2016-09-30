package org.neomatrix369.salaryslip.tax;

import static org.neomatrix369.salaryslip.components.Money.minimum;

import org.neomatrix369.salaryslip.components.Money;

public class StandardTaxBand implements TaxBand {
  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  public StandardTaxBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  @Override
  public Money calculateFrom(Money annualSalary) {
    final Money excess = calculateExcessFrom(annualSalary, upperLimit, lowerLimit);
    return excess.multiplyBy(rate);
  }

  @Override
  public Money calculateExcessFrom(Money annualSalary, Money upperLimit, Money lowerLimit) {
    Money actualUpperLimit = minimum(annualSalary, upperLimit);
    return actualUpperLimit.minus(lowerLimit);
  }

  @Override
  public Money lowerLimit() {
    return new Money(lowerLimit);
  }

  @Override
  public Money upperLimit() {
    return new Money(upperLimit);
  }

  @Override
  public double rate() {
    return rate;
  }

  @Override
  public String toString() {
    return String.format("lowerLimit=%s, upperLimit=%s, rate=%s", lowerLimit, upperLimit, rate);
  }
}
