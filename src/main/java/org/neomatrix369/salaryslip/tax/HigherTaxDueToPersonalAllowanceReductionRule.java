package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.tax.bands.TaxBand;

public class HigherTaxDueToPersonalAllowanceReductionRule {

  private final TaxBand higherTaxBand;
  private final PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator;

  public HigherTaxDueToPersonalAllowanceReductionRule(
      TaxBand higherTaxBand, PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator) {
    this.higherTaxBand = higherTaxBand;
    this.personalAllowanceReductionCalculator = personalAllowanceReductionCalculator;
  }

  public Money calculateTaxPayableFor(Money annualSalary) {
    return personalAllowanceReductionCalculator.reductionFor(annualSalary).times(higherTaxBand.rate());
  }
}
