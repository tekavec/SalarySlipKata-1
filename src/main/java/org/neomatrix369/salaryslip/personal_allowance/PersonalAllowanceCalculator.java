package org.neomatrix369.salaryslip.personal_allowance;

import static org.neomatrix369.salaryslip.components.Money.minimumOf;
import static org.neomatrix369.salaryslip.components.Money.zero;

import org.neomatrix369.salaryslip.components.Money;

public class PersonalAllowanceCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE = new Money(100_000.00);

  public Money calculateAdjustmentForExcessIncomeOver100KFrom(Money annualSalary) {
    Money excessIncomeOver100K = calculateExcessIncomeOver100KFrom(annualSalary);

    if (excessIncomeOver100K.isGreaterThanZero()) {
      final Money adjustmentForPersonalAllowance =
          reduce1PoundForEvery2PoundsEarnedOn(excessIncomeOver100K);

      return adjustmentForPersonalAllowance.isGreaterThanZero()
          ? adjustmentForPersonalAllowance
          : PERSONAL_ALLOWANCE;
    }

    return zero();
  }

  private Money reduce1PoundForEvery2PoundsEarnedOn(Money excessIncomeOver100K) {
    Money reducedEarnings = excessIncomeOver100K.divideBy(2);
    return minimumOf(PERSONAL_ALLOWANCE, reducedEarnings);
  }

  public Money calculateTaxFreeAllowanceFor(Money annualSalary) {
    Money adjustmentForExcessIncomeOver100K = calculateAdjustmentForExcessIncomeOver100KFrom(annualSalary);
    return PERSONAL_ALLOWANCE.minus(adjustmentForExcessIncomeOver100K);
  }

  private Money calculateExcessIncomeOver100KFrom(Money annualSalary) {
    return annualSalary.minus(SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);
  }
}
