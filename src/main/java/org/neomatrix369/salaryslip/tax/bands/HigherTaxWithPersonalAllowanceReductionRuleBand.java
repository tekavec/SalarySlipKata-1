package org.neomatrix369.salaryslip.tax.bands;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.tax.PersonalAllowanceReductionCalculator;

public class HigherTaxWithPersonalAllowanceReductionRuleBand extends TaxBand {
  private PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      Money lowerLimit, Money upperLimit, double rate, PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator) {
    super(lowerLimit, upperLimit, rate);
    this.personalAllowanceReductionCalculator = personalAllowanceReductionCalculator;
  }

  @Override
  protected Money amountWithinBandFor(Money annualSalary) {
    Money excessIncome = super.amountWithinBandFor(annualSalary);

    Money taxableReduction = personalAllowanceReductionCalculator.reductionFor(annualSalary);

    return excessIncome.add(taxableReduction);
  }
}
