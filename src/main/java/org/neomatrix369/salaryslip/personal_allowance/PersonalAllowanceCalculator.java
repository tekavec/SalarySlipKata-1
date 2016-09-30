package org.neomatrix369.salaryslip.personal_allowance;

import static org.neomatrix369.salaryslip.components.Money.zero;

import org.neomatrix369.salaryslip.components.Money;

public class PersonalAllowanceCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE = new Money(100_000.00);

  private Money getPersonalAllowance() {
    return PERSONAL_ALLOWANCE;
  }

  public Money calculateAdjustmentForExcessOver100K(Money annualSalary) {
    final Money excessOver100K = calculateExcessOver100kOf(annualSalary);

    if (excessOver100K.isGreaterThanZero()) {
      final Money adjustmentForPersonalAllowance = reduce1PoundForEvery2PoundsEarnedOn(excessOver100K);
      return adjustmentForPersonalAllowance.isGreaterThanZero()
                ? adjustmentForPersonalAllowance
                : getPersonalAllowance();
    }

    return zero();
  }

  public boolean reductionRuleAppliesBetween(Money lowerLimit, Money upperLimit) {
    return SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE.isBetweenAndInclusiveOf(lowerLimit, upperLimit);
  }

  private Money calculateExcessOver100kOf(Money annualSalary) {
    return annualSalary.minus(SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);
  }

  public Money calculateTaxFreeAllowanceFor(Money annualSalary) {
    final Money excessOver100k = calculateExcessOver100kOf(annualSalary);
    final Money reduce1PoundForEvery2PoundsEarned = reduce1PoundForEvery2PoundsEarnedOn(excessOver100k);

    return excessOver100k.isGreaterThanZero()
              ? adjustedPersonalAllowance(reduce1PoundForEvery2PoundsEarned)
              : getPersonalAllowance();
  }

  private Money reduce1PoundForEvery2PoundsEarnedOn(Money excessOver100k) {
    final Money reducedEarnings = excessOver100k.divideBy(2);

    return getPersonalAllowance().isGreaterThan(reducedEarnings)
        ? reducedEarnings
        : zero();
  }

  private Money adjustedPersonalAllowance(Money amount) {
    return amount.isGreaterThanZero()
              ? getPersonalAllowance().minus(amount)
              : zero();
  }
}
