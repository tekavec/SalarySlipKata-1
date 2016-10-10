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
    Money excessIncome = excessIncomeFrom(annualSalary);

    return taxablePayable(excessIncome);
  }

  protected Money excessIncomeFrom(Money annualSalary) {
    Money lowerOfTheTwoLimits = minimum(annualSalary, upperLimit);
    return lowerOfTheTwoLimits.subtract(lowerLimit);
  }

  private Money taxablePayable(Money excessIncome) {return excessIncome.times(rate);}
}
