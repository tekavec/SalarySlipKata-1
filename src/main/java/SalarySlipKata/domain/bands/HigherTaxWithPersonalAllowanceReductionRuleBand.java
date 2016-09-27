package SalarySlipKata.domain.bands;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.rule.PersonalAllowanceReductionOver100K;

public class HigherTaxWithPersonalAllowanceReductionRuleBand extends StandardBand {
  private PersonalAllowanceReductionOver100K personalAllowanceReductionOver100K;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(StandardBand standardBand, PersonalAllowanceReductionOver100K personalAllowanceReductionOver100K) {
    super(standardBand.lowerLimit, standardBand.upperLimit, standardBand.rate);
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
