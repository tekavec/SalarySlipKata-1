package org.somename.salaryslip.tax;

import org.somename.salaryslip.components.Money;
import org.somename.salaryslip.personal_allowance.PersonalAllowanceCalculator;

public class HigherTaxBandWithReductionRule extends TaxBand {
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public HigherTaxBandWithReductionRule(
      Money lowerLimit, Money upperLimit, double rate,
      PersonalAllowanceCalculator personalAllowanceCalculator) {
    super(lowerLimit, upperLimit, rate);
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  @Override
  public Money calculateTaxPayableFor(Money annualSalary) {
    Money excessIncome = calculateExcessIncomeFor(annualSalary);

    Money adjustmentDueToExcessOver100 =
        personalAllowanceCalculator.calculateAdjustmentForExcessOver100K(annualSalary);

    Money newExcessIncome = excessIncome.add(adjustmentDueToExcessOver100);

    return newExcessIncome.times(rate());
  }
}
