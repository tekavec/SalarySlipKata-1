package org.neomatrix369.salaryslip.tax.bands;

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

    return taxPayableFor(excessIncome);
  }

  protected Money calculateExcessIncomeFrom(Money annualSalary) {
    Money lessOrEqualToTheUpperLimit = minimum(annualSalary, upperLimit);

    return lessOrEqualToTheUpperLimit.subtract(lowerLimit);
  }

  private Money taxPayableFor(Money amount) {
    return amount.times(rate);
  }
}
