package SalarySlipKata.domain.bands;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain_service.PersonalAllowanceCalculator;

public class HigherTaxWithPersonalAllowanceReductionRuleBand implements Band {
  private Band band;
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      Band band, PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.band = band;
    this.personalAllowanceCalculator = personalAllowanceCalculator;
  }

  @Override
  public Money calculateFrom(Money annualSalary) {
    Money personalAllowanceAdjustmentForOver100K = zero();
    if (personalAllowanceCalculator.reductionRuleAppliesBetween(band.lowerLimit(), band.upperLimit())) {
      personalAllowanceAdjustmentForOver100K =
          personalAllowanceCalculator.calculateAdjustmentForExcessOver100K(annualSalary);
    }

    if (annualSalary.isBetweenAndInclusiveOf(lowerLimit(), upperLimit())) {
      final Money excessIncome = band.calculateExcessFrom(annualSalary, band.lowerLimit());
      return excessIncome.plus(personalAllowanceAdjustmentForOver100K).multiplyBy(band.rate());
    }

    if (annualSalary.isGreaterThan(upperLimit())) {
      final Money excessIncome = band.calculateExcessFrom(band.upperLimit(), band.lowerLimit());
      return excessIncome.plus(personalAllowanceAdjustmentForOver100K).multiplyBy(band.rate());
    }

    return zero();
  }

  @Override
  public Money calculateExcessFrom(Money upperLimit, Money lowerLimit) {
    return band.calculateExcessFrom(upperLimit, lowerLimit);
  }

  @Override
  public Money lowerLimit() {
    return band.lowerLimit();
  }

  @Override
  public Money upperLimit() {
    return band.upperLimit();
  }

  @Override
  public double rate() {
    return band.rate();
  }
}
