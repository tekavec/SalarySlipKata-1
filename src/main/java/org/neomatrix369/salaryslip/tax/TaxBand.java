package org.neomatrix369.salaryslip.tax;

import static org.neomatrix369.salaryslip.components.Money.minimumOf;

import org.neomatrix369.salaryslip.components.Money;

public abstract class TaxBand {
  protected Money lowerLimit;
  protected Money upperLimit;
  protected double rate;

  abstract Money calculateTaxFrom(Money annualSalary);

  protected Money calculateExcessFrom(Money annualSalary, Money upperLimit, Money lowerLimit) {
    Money minimumOfTheTwoLimits = minimumOf(annualSalary, upperLimit);
    return minimumOfTheTwoLimits.minus(lowerLimit);
  }
}
