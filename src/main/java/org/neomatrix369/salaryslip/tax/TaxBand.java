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

  public Money calculateTaxPayableFor(Money annualSalary) {
    Money excessIncome = calculateExcessIncomeFrom(annualSalary);

    return excessIncome.times(rate);
  }

  protected Money calculateExcessIncomeFrom(Money annualSalary) {
    Money lessOrEqualToTheUpperLimit = minimum(annualSalary, upperLimit);

    return lessOrEqualToTheUpperLimit.subtract(lowerLimit);
  }

  protected double rate() {
    return rate;
  }
}
