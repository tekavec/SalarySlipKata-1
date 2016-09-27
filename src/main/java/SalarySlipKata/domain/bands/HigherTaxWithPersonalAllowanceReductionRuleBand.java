package SalarySlipKata.domain.bands;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.rule.PersonalAllowanceReductionOver100K;

public class HigherTaxWithPersonalAllowanceReductionRuleBand extends Band {
  private PersonalAllowanceReductionOver100K personalAllowanceReductionOver100K;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(Band band, PersonalAllowanceReductionOver100K personalAllowanceReductionOver100K) {
    super(band.lowerLimit, band.upperLimit, band.rate);
    this.personalAllowanceReductionOver100K = personalAllowanceReductionOver100K;
  }

  public Money calculateFrom(Money annualSalary) {
    Money personalAllowanceAdjustmentForOver100K = zero();
    if (personalAllowanceReductionOver100K.appliesBetweenLimits(lowerLimit, upperLimit)) {
      personalAllowanceAdjustmentForOver100K = personalAllowanceReductionOver100K.calculateAdjustmentForExcessIn(annualSalary);
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
