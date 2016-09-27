package SalarySlipKata.domain.bands;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;

public class HigherTaxWith100KRuleBand extends Band {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money
      THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE = new Money(100_000.00);

  public HigherTaxWith100KRuleBand(Money lowerLimit, Money upperLimit, double rate) {
    super(lowerLimit, upperLimit, rate);
  }

  public Money calculateFrom(Money annualSalary) {
    if (THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE.isBetweenAndInclusiveOf(lowerLimit, upperLimit)) {
      Money excessOver100K =
          calculateAdjustmentForExcessOver100KDueToPersonalAllowanceReductionRuleWith(annualSalary);
       lowerLimit = lowerLimit.minus(excessOver100K);
    }

    return super.calculateFrom(annualSalary);
  }

  private Money calculateAdjustmentForExcessOver100KDueToPersonalAllowanceReductionRuleWith(Money annualSalary) {
    final Money differenceAbove100K = calculateDifferenceAbove100kOf(annualSalary);

    if (differenceAbove100K.isGreaterThanZero()) {
      final Money adjustedPersonalAllowance = reduce1PoundForEvery2PoundsEarnedOn(differenceAbove100K);
      return adjustedPersonalAllowance.isGreaterThanZero()
          ? adjustedPersonalAllowance
          : PERSONAL_ALLOWANCE;
    }

    return zero();
  }

  private Money calculateDifferenceAbove100kOf(Money annualSalary) {
    return annualSalary.minus(THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);
  }

  private Money reduce1PoundForEvery2PoundsEarnedOn(Money differenceAbove100k) {
    final Money reduced1PoundForEvery2PoundsEarned = differenceAbove100k.divideBy(2);
    return PERSONAL_ALLOWANCE.minus(reduced1PoundForEvery2PoundsEarned);
  }
}
