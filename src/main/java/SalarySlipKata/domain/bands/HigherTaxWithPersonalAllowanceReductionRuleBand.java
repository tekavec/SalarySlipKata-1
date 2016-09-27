package SalarySlipKata.domain.bands;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain_service.PersonalAllowanceCalculator;

public class HigherTaxWithPersonalAllowanceReductionRuleBand extends StandardBand {
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      Band band, PersonalAllowanceCalculator personalAllowanceCalculator) {
    super(band.lowerLimit(), band.upperLimit(), band.rate());
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  @Override
  public Money calculateFrom(Money annualSalary) {
    Money personalAllowanceAdjustmentForOver100K = zero();
    if (personalAllowanceCalculator.reductionRuleAppliesBetween(lowerLimit(), upperLimit())) {
      personalAllowanceAdjustmentForOver100K =
          personalAllowanceCalculator.calculateAdjustmentForExcessOver100K(annualSalary);
    }

    if (annualSalary.isBetweenAndInclusiveOf(lowerLimit(), upperLimit())) {
      final Money excessIncome = calculateExcessFrom(annualSalary, lowerLimit());
      return excessIncome.plus(personalAllowanceAdjustmentForOver100K).multiplyBy(rate());
    }

    if (annualSalary.isGreaterThan(upperLimit())) {
      final Money excessIncome = calculateExcessFrom(upperLimit(), lowerLimit());
      return excessIncome.plus(personalAllowanceAdjustmentForOver100K).multiplyBy(rate());
    }

    return zero();
  }
}
