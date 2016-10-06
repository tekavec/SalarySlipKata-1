package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public class HigherTaxWithPersonalAllowanceReductionRuleBand extends TaxBand {
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      Money lowerLimit, Money upperLimit, double rate,
      PersonalAllowanceCalculator personalAllowanceCalculator) {
    super(lowerLimit, upperLimit, rate);
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  public Money calculateTaxPayableFor(Money annualSalary) {
    Money excessIncome = calculateExcessIncomeFrom(annualSalary);

    Money adjustmentForSalaryOver100K =
        personalAllowanceCalculator.calculateAdjustmentForSalaryOver100KFrom(annualSalary);

    Money newExcessIncome = excessIncome.add(adjustmentForSalaryOver100K);

    return applyTheBandRateTo(newExcessIncome);
  }
}
