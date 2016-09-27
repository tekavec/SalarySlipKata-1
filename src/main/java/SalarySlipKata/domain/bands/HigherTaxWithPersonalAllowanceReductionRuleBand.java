package SalarySlipKata.domain.bands;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.rule.PersonalAllowanceReduction;

public class HigherTaxWithPersonalAllowanceReductionRuleBand extends StandardBand {
  private PersonalAllowanceReduction personalAllowanceReduction;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(
      Band band, PersonalAllowanceReduction personalAllowanceReduction) {
    super(band.lowerLimit(), band.upperLimit(), band.rate());
    this.personalAllowanceReduction = personalAllowanceReduction;
  }

  @Override
  public Money calculateFrom(Money annualSalary) {
    Money personalAllowanceAdjustmentForOver100K = zero();
    if (personalAllowanceReduction.appliesBetweenLimits(lowerLimit, upperLimit)) {
      personalAllowanceAdjustmentForOver100K = personalAllowanceReduction.calculateAdjustmentForExcessIn(annualSalary);
    }

    if (annualSalary.isBetweenAndInclusiveOf(lowerLimit, upperLimit)) {
      final Money excessIncome = calculateExcessFrom(annualSalary, lowerLimit);
      return bandValueFor(excessIncome.plus(personalAllowanceAdjustmentForOver100K));
    }

    if (annualSalary.isGreaterThan(upperLimit)) {
      final Money excessIncome = calculateExcessFrom(upperLimit, lowerLimit);
      return bandValueFor(excessIncome.plus(personalAllowanceAdjustmentForOver100K));
    }

    return zero();
  }
}
