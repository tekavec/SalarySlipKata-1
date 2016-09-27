package SalarySlipKata.domain.rule;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;

public class PersonalAllowanceReductionOver100K {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money SALARY_THRESHOLD_FOR_RULE = new Money(100_000.00);

  public Money calculateDifferenceAbove100kOf(Money annualSalary) {
    return annualSalary.minus(SALARY_THRESHOLD_FOR_RULE);
  }

  public Money reduce1PoundForEvery2PoundsEarnedOn(Money differenceAbove100k) {
    final Money reduced1PoundForEvery2PoundsEarned = differenceAbove100k.divideBy(2);
    return PERSONAL_ALLOWANCE.minus(reduced1PoundForEvery2PoundsEarned);
  }

  public Money calculateAdjustmentForExcessIn(Money annualSalary) {
    final Money differenceAbove100K = calculateDifferenceAbove100kOf(annualSalary);

    if (differenceAbove100K.isGreaterThanZero()) {
      final Money adjustedPersonalAllowance = reduce1PoundForEvery2PoundsEarnedOn(differenceAbove100K);
      return adjustedPersonalAllowance.isGreaterThanZero()
          ? adjustedPersonalAllowance
          : PERSONAL_ALLOWANCE;
    }

    return zero();
  }

  public boolean appliesBetweenLimits(Money lowerLimit, Money upperLimit) {
    return SALARY_THRESHOLD_FOR_RULE.isBetweenAndInclusiveOf(lowerLimit, upperLimit);
  }

  public Money calculateTaxFreeAllowance(Money annualSalary) {
    final Money differenceAbove100k = calculateDifferenceAbove100kOf(annualSalary);

    return differenceAbove100k.isGreaterThanZero()
        ? reduce1PoundForEvery2PoundsEarnedOn(differenceAbove100k)
        : PERSONAL_ALLOWANCE;
  }
}
