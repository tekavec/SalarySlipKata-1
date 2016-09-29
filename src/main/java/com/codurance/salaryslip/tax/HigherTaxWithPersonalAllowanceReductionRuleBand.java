package com.codurance.salaryslip.tax;

import static com.codurance.salaryslip.components.Money.zero;

import com.codurance.salaryslip.components.Money;
import com.codurance.salaryslip.personal_allowance.PersonalAllowanceCalculator;

public class HigherTaxWithPersonalAllowanceReductionRuleBand implements TaxBand {
  private TaxBand taxBand;
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      TaxBand taxBand, PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.taxBand = taxBand;
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  public Money calculateFrom(Money annualSalary) {
    Money personalAllowanceAdjustmentForOver100K = zero();
    if (personalAllowanceCalculator.reductionRuleAppliesBetween(taxBand.lowerLimit(), taxBand.upperLimit())) {
      personalAllowanceAdjustmentForOver100K =
          personalAllowanceCalculator.calculateAdjustmentForExcessOver100K(annualSalary);
    }

    if (annualSalary.isBetweenAndInclusiveOf(taxBand.lowerLimit(), taxBand.upperLimit())) {
      final Money excessIncome = calculateExcessFrom(annualSalary, taxBand.lowerLimit());
      return excessIncome.plus(personalAllowanceAdjustmentForOver100K).multiplyBy(taxBand.rate());
    }

    if (annualSalary.isGreaterThan(taxBand.upperLimit())) {
      final Money excessIncome = calculateExcessFrom(taxBand.upperLimit(), taxBand.lowerLimit());
      return excessIncome.plus(personalAllowanceAdjustmentForOver100K).multiplyBy(taxBand.rate());
    }

    return zero();
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

  @Override
  public Money calculateExcessFrom(Money upperLimit, Money lowerLimit) {
    return taxBand.calculateExcessFrom(upperLimit, lowerLimit);
  }
}
