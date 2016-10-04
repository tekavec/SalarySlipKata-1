package org.neomatrix369.salaryslip.tax;

import static org.neomatrix369.salaryslip.components.Money.minimum;

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

  public Money calculateTaxPayable(Money annualSalary) {
    Money actualUpperLimit = minimum(annualSalary, upperLimit);
    Money excessIncome = actualUpperLimit.subtract(lowerLimit);

    return excessIncome.times(rate);
  }
}
