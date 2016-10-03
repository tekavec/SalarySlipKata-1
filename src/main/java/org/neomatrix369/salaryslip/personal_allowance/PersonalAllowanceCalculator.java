package org.neomatrix369.salaryslip.personal_allowance;

import static org.neomatrix369.salaryslip.components.Money.minimumOf;
import static org.neomatrix369.salaryslip.components.Money.zero;

import org.neomatrix369.salaryslip.components.Money;

public class PersonalAllowanceCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE = new Money(100_000.00);

  public Money calculateAdjustmentForExcessIncomeOver100K(Money annualSalary) {
    final Money excessIncomeOver100K = calculateExcessIncomeOver100kFrom(annualSalary);

    if (excessIncomeOver100K.isGreaterThanZero()) {
      final Money adjustmentForPersonalAllowance =
          reduce1PoundForEvery2PoundsEarnedOn(excessIncomeOver100K);

      return adjustmentForPersonalAllowance.isGreaterThanZero()
                ? adjustmentForPersonalAllowance
                : PERSONAL_ALLOWANCE;
    }

    return zero();
  }

  private Money calculateExcessIncomeOver100kFrom(Money annualSalary) {
    return annualSalary.minus(SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);
  }

  public Money calculateTaxFreeAllowanceFor(Money annualSalary) {
    final Money excessIncomeOver100K = calculateExcessIncomeOver100kFrom(annualSalary);

    if (excessIncomeOver100K.isGreaterThanZero()) {
      final Money adjustmentForPersonalAllowance =
          reduce1PoundForEvery2PoundsEarnedOn(excessIncomeOver100K);

      return adjustmentForPersonalAllowance.isGreaterThanZero()
                ? PERSONAL_ALLOWANCE.minus(adjustmentForPersonalAllowance)
                : adjustmentForPersonalAllowance;
    }

    return PERSONAL_ALLOWANCE;
  }

  private Money reduce1PoundForEvery2PoundsEarnedOn(Money excessIncomeOver100K) {
    final Money reducedEarnings = excessIncomeOver100K.divideBy(2);
    return minimumOf(PERSONAL_ALLOWANCE, reducedEarnings);
  }
}
