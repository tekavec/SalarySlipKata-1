package org.somename.salaryslip.tax;

import static org.somename.salaryslip.components.Money.minimum;

import org.somename.salaryslip.components.Money;

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
    final Money excessIncome = calculateExcessIncomeFor(annualSalary);
    return excessIncome.times(rate);
  }

  protected Money calculateExcessIncomeFor(Money annualSalary) {
    final Money minimumOfTheTwo = minimum(annualSalary, upperLimit);
    return minimumOfTheTwo.subtract(lowerLimit);
  }

  protected double rate() {
    return rate;
  }
}
