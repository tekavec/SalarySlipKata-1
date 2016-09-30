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
  public Money calculateFrom(Money annualSalary) {
    Money personalAllowanceAdjustmentForOver100K = Money.zero();
    if (personalAllowanceCalculator.reductionRuleAppliesBetween(
        standardTaxBand.lowerLimit(), standardTaxBand.upperLimit())) {
      personalAllowanceAdjustmentForOver100K =
          personalAllowanceCalculator.calculateAdjustmentForExcessOver100K(annualSalary);
    }

    Money excess = standardTaxBand.calculateExcessFrom(
            annualSalary, standardTaxBand.upperLimit(), standardTaxBand.lowerLimit());
    excess = excess.plus(personalAllowanceAdjustmentForOver100K);

    return excess.multiplyBy(standardTaxBand.rate());
  }
}
