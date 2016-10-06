package org.neomatrix369.salaryslip.tax.bands;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.tax.PersonalAllowanceCalculator;

public class HigherTaxWithPersonalAllowanceReductionRuleBand extends TaxBand {
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      Money lowerLimit, Money upperLimit, double rate,
      PersonalAllowanceCalculator personalAllowanceCalculator) {
    super(lowerLimit, upperLimit, rate);
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  @Override
  protected Money calculateExcessIncomeFrom(Money annualSalary) {
    Money excessIncome = super.calculateExcessIncomeFrom(annualSalary);

    Money reducedExcess =
        personalAllowanceCalculator.calculateReducedExcessForSalaryOver100KFrom(annualSalary);

    return excessIncome.add(reducedExcess);
  }
}
