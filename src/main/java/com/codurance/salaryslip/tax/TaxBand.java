package com.codurance.salaryslip.tax;

import static com.codurance.salaryslip.components.Money.zero;

import com.codurance.salaryslip.components.Money;
import com.codurance.salaryslip.tax_bands.Band;

public class TaxBand implements Band {
  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  public TaxBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  @Override
  public Money calculateFrom(Money annualSalary) {
    if (annualSalary.isBetweenAndInclusiveOf(lowerLimit, upperLimit)) {
      return calculateExcessFrom(annualSalary, lowerLimit).multiplyBy(rate);
    }

    if (annualSalary.isGreaterThan(upperLimit)) {
      return calculateExcessFrom(upperLimit, lowerLimit).multiplyBy(rate);
    }

    return zero();
  }

  @Override
  public Money calculateExcessFrom(Money upperLimit, Money lowerLimit) {
    return upperLimit.minus(lowerLimit);
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
