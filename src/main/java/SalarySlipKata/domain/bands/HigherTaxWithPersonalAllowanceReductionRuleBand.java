package SalarySlipKata.domain.bands;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.rule.PersonalAllowanceReductionOver100K;

public class HigherTaxWithPersonalAllowanceReductionRuleBand extends Band {
  private PersonalAllowanceReductionOver100K personalAllowanceReductionOver100K;

  public HigherTaxWithPersonalAllowanceReductionRuleBand(Band band, PersonalAllowanceReductionOver100K personalAllowanceReductionOver100K) {
    super(band.lowerLimit, band.upperLimit, band.rate);
    this.personalAllowanceReductionOver100K = personalAllowanceReductionOver100K;
  }

  public Money calculateFrom(Money annualSalary) {
    if (personalAllowanceReductionOver100K.appliesBetweenLimits(lowerLimit, upperLimit)) {
      Money excessOver100K = personalAllowanceReductionOver100K.calculateAdjustmentForExcessIn(annualSalary);
      lowerLimit = lowerLimit.minus(excessOver100K);
    }

    return super.calculateFrom(annualSalary);
  }
}
