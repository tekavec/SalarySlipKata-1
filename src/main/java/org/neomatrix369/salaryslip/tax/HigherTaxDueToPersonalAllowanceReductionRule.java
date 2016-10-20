package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public class HigherTaxDueToPersonalAllowanceReductionRule {
  private final double rate;
  private final PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator;

  public HigherTaxDueToPersonalAllowanceReductionRule(
      double rate, PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator) {
    this.rate = rate;
    this.personalAllowanceReductionCalculator = personalAllowanceReductionCalculator;
  }

  public Money calculateTaxPayableFor(Money annualSalary) {
    final Money reduction = personalAllowanceReductionCalculator.reductionFor(annualSalary);
    return reduction.times(rate);
  }
}
