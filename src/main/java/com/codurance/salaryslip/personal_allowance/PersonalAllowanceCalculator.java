package com.codurance.salaryslip.personal_allowance;

import static com.codurance.salaryslip.components.Money.zero;

import com.codurance.salaryslip.components.Money;

public class PersonalAllowanceCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE = new Money(100_000.00);

  public Money getPersonalAllowance() {
    return PERSONAL_ALLOWANCE;
  }

  public Money calculateAdjustmentForExcessOver100K(Money annualSalary) {
    final Money differenceAbove100K = calculateDifferenceAbove100kOf(annualSalary);

    if (differenceAbove100K.isGreaterThanZero()) {
      final Money adjustmentForPersonalAllowance = reduce1PoundForEvery2PoundsEarnedOn(differenceAbove100K);
      return adjustmentForPersonalAllowance.isGreaterThanZero()
                ? adjustmentForPersonalAllowance
                : getPersonalAllowance();
    }

    return zero();
  }

  public boolean reductionRuleAppliesBetween(Money lowerLimit, Money upperLimit) {
    return SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE.isBetweenAndInclusiveOf(lowerLimit, upperLimit);
  }

  public Money calculateDifferenceAbove100kOf(Money annualSalary) {
    return annualSalary.minus(SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);
  }

  public Money reduce1PoundForEvery2PoundsEarnedOn(Money differenceAbove100k) {
    final Money reducedEarnings = differenceAbove100k.divideBy(2);

    return getPersonalAllowance().isGreaterThan(reducedEarnings)
              ? reducedEarnings
              : zero();
  }
}
