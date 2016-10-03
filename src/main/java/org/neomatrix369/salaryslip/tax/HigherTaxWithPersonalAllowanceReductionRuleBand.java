package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.personal_allowance.PersonalAllowanceCalculator;

public class HigherTaxWithPersonalAllowanceReductionRuleBand extends TaxBand {
  private StandardTaxBand standardTaxBand;
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      StandardTaxBand standardTaxBand, PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.standardTaxBand = standardTaxBand;
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  @Override
  public Money calculateTaxFrom(Money annualSalary) {
    Money personalAllowanceAdjustmentForOver100K =
        personalAllowanceCalculator.calculateAdjustmentForExcessIncomeOver100K(annualSalary);

    Money excessIncome = standardTaxBand.calculateExcessFrom(
            annualSalary, standardTaxBand.upperLimit(), standardTaxBand.lowerLimit());

    Money taxableIncomeForThisBand = excessIncome.plus(personalAllowanceAdjustmentForOver100K);

    return taxableIncomeForThisBand.multiplyBy(standardTaxBand.rate());
  }
}
