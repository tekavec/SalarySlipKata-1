package org.neomatrix369.salaryslip.tax;

import static org.neomatrix369.salaryslip.components.Money.minimum;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.personal_allowance.PersonalAllowanceCalculator;

public class HigherTaxWithPersonalAllowanceReductionRuleBand implements TaxBand {
  private TaxBand taxBand;
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      TaxBand taxBand, PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.taxBand = taxBand;
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  @Override
  public Money calculateFrom(Money annualSalary) {
    Money personalAllowanceAdjustmentForOver100K = Money.zero();
    if (personalAllowanceCalculator.reductionRuleAppliesBetween(taxBand.lowerLimit(), taxBand.upperLimit())) {
      personalAllowanceAdjustmentForOver100K =
          personalAllowanceCalculator.calculateAdjustmentForExcessOver100K(annualSalary);
    }

    Money excess = calculateExcessFrom(annualSalary, taxBand.upperLimit(), taxBand.lowerLimit());
    excess = excess.plus(personalAllowanceAdjustmentForOver100K);

    return excess.multiplyBy(taxBand.rate());
  }

  @Override
  public Money calculateExcessFrom(Money annualSalary, Money upperLimit, Money lowerLimit) {
    Money actualUpperLimit = minimum(annualSalary, upperLimit);
    return actualUpperLimit.minus(lowerLimit);
  }

  @Override
  public Money lowerLimit() {
    return taxBand.lowerLimit();
  }

  @Override
  public Money upperLimit() {
    return taxBand.upperLimit();
  }

  @Override
  public double rate() {
    return taxBand.rate();
  }
}
