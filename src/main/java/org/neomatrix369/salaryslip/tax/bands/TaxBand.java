package org.neomatrix369.salaryslip.tax.bands;

import static org.neomatrix369.salaryslip.components.Money.smallerOf;

import org.neomatrix369.salaryslip.components.Money;

public class TaxBand {
  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  public TaxBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  public Money calculateTaxPayableFor(Money annualSalary) {
    return amountWithinBandFor(annualSalary).times(rate);
  }

  protected Money amountWithinBandFor(Money annualSalary) {
    return smallerOf(annualSalary, upperLimit).subtract(lowerLimit);
  }

}
