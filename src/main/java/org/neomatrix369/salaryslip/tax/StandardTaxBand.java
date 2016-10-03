package org.neomatrix369.salaryslip.tax;

import static org.neomatrix369.salaryslip.components.Money.minimumOf;

import org.neomatrix369.salaryslip.components.Money;

public class StandardTaxBand extends TaxBand {
  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  public StandardTaxBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  @Override
  public Money calculateTaxFrom(Money annualSalary) {
    final Money excessIncomeForThisBand = calculateExcessFrom(annualSalary, upperLimit, lowerLimit);
    return excessIncomeForThisBand.multiplyBy(rate);
  }

  protected Money calculateExcessFrom(Money annualSalary, Money upperLimit, Money lowerLimit) {
    Money minimumOfTheTwoLimits = minimumOf(annualSalary, upperLimit);
    return minimumOfTheTwoLimits.minus(lowerLimit);
  }

  protected Money lowerLimit() {
    return new Money(lowerLimit);
  }

  protected Money upperLimit() {
    return new Money(upperLimit);
  }

  protected double rate() {
    return rate;
  }

  @Override
  public String toString() {
    return String.format("lowerLimit=%s, upperLimit=%s, rate=%s", lowerLimit, upperLimit, rate);
  }
}
