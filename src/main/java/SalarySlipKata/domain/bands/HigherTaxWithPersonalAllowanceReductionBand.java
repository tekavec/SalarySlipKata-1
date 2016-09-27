package SalarySlipKata.domain.bands;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain_service.PersonalAllowanceReductionOver100KRule;

public class HigherTaxWithPersonalAllowanceReductionBand extends Band {
  private PersonalAllowanceReductionOver100KRule personalAllowanceReductionOver100KRule;

  public HigherTaxWithPersonalAllowanceReductionBand(Band band, PersonalAllowanceReductionOver100KRule personalAllowanceReductionOver100KRule) {
    super(band.lowerLimit, band.upperLimit, band.rate);
    this.personalAllowanceReductionOver100KRule = personalAllowanceReductionOver100KRule;
  }

  public Money calculateFrom(Money annualSalary) {
    if (personalAllowanceReductionOver100KRule.appliesToLimits(lowerLimit, upperLimit)) {
      Money excessOver100K = personalAllowanceReductionOver100KRule.calculateAdjustmentForExcessIn(annualSalary);
       lowerLimit = lowerLimit.minus(excessOver100K);
    }

    return super.calculateFrom(annualSalary);
  }
}
