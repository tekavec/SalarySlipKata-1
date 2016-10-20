package org.neomatrix369.salaryslip.tax.bands;

import static org.neomatrix369.salaryslip.components.Money.smallerOf;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Money;

public enum TaxBand {
  ADDITIONAL_RATE_TAX_BAND(new Money(150_000.00), new Money(MAX_VALUE), Rate.FORTY_FIVE_PERCENT),
  HIGHER_RATE_TAX_BAND    (new Money(43_000.00), new Money(150_000.00), Rate.FORTY_PERCENT),
  BASIC_RATE_TAX_BAND     (new Money(11_000.00), new Money( 43_000.00), Rate.TWENTY_PERCENT);

  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  TaxBand(Money lowerLimit, Money upperLimit, double rate) {
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

  public double rate() {
    return rate;
  }

  private static class Rate {
    private static final double FORTY_FIVE_PERCENT = 0.45;
    private static final double FORTY_PERCENT = 0.40;
    private static final double TWENTY_PERCENT = 0.20;
  }
}
