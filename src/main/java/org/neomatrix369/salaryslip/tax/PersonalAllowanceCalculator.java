package org.neomatrix369.salaryslip.tax;

import static org.neomatrix369.salaryslip.components.Money.minimum;

import org.neomatrix369.salaryslip.components.Money;

public class PersonalAllowanceCalculator {
  private static final Money FULL_PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money PERSONAL_ALLOWANCE_REDUCTION_THRESHOLD = new Money(100_000.00);

  public Money calculateTaxFreeAllowance(Money annualSalary) {
    Money personalAllowanceReduction = personalAllowanceReductionFor(annualSalary);

    return FULL_PERSONAL_ALLOWANCE.subtract(personalAllowanceReduction);
  }

  public Money personalAllowanceReductionFor(Money annualSalary) {
    Money excessOver100K = calculateExcessOver100K(annualSalary);

    final Money personalAllowanceReduction = reduceBy_1_PoundForEvery_2_PoundsEarned(excessOver100K);

    return minimum(personalAllowanceReduction, FULL_PERSONAL_ALLOWANCE);
  }

  private Money reduceBy_1_PoundForEvery_2_PoundsEarned(Money amount) {
    return amount.divisionBy(2);
  }

  private Money calculateExcessOver100K(Money annualSalary) {
    return annualSalary.subtract(PERSONAL_ALLOWANCE_REDUCTION_THRESHOLD);
  }
}
