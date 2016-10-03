package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public class StandardTaxBand extends TaxBand {
  public StandardTaxBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  @Override
  public Money calculateTaxFrom(Money annualSalary) {
    final Money excessIncomeForThisBand = calculateExcessFrom(annualSalary, upperLimit, lowerLimit);
    return excessIncomeForThisBand.times(rate);
  }

  @Override
  public String toString() {
    return String.format("lowerLimit=%s, upperLimit=%s, rate=%s", lowerLimit, upperLimit, rate);
  }
}
