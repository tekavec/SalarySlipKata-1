package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.personal_allowance.PersonalAllowanceCalculator;

public class HigherTaxWithPersonalAllowanceReductionRuleBand extends TaxBand {
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      Money lowerLimit, Money upperLimit, double rate, PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  @Override
  public Money calculateTaxFrom(Money annualSalary) {
    Money personalAllowanceAdjustmentForOver100K =
        personalAllowanceCalculator.calculateAdjustmentForExcessIncomeOver100KFrom(annualSalary);

    Money excessIncome = calculateExcessFrom(annualSalary, upperLimit, lowerLimit);

    Money taxableIncomeForThisBand = excessIncome.add(personalAllowanceAdjustmentForOver100K);

    return taxableIncomeForThisBand.times(rate);
  }
}
