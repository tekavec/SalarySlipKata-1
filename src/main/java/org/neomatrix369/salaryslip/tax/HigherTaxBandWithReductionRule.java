package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public class HigherTaxBandWithReductionRule extends TaxBand {
  private PersonalAllowanceReductionCalculator personalAllowanceCalculator;

  public HigherTaxBandWithReductionRule(Money lowerLimit, Money upperLimit, double rate,
      PersonalAllowanceReductionCalculator personalAllowanceCalculator) {
    super(lowerLimit, upperLimit, rate);
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  protected Money excessIncomeFrom(Money annualSalary) {
    final Money excessIncome = super.excessIncomeFrom(annualSalary);

    Money reduction = personalAllowanceCalculator.reductionFor(annualSalary);

    return excessIncome.add(reduction);
  }
}
