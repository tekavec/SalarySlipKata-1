package org.somename.salaryslip.personal_allowance;

import static org.somename.salaryslip.components.Money.minimum;

import org.somename.salaryslip.components.Money;

public class PersonalAllowanceCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money PERSONAL_ALLOWANCE_REDUCTION_RULE_THRESHOLD = new Money(100_000.00);

  public Money calculateTaxFreeAllowance(Money annualSalary) {
    Money reducedExcess = calculateReductionToPersonalAllowanceOn(annualSalary);

    return PERSONAL_ALLOWANCE.subtract(reducedExcess);
  }

  public Money calculateAdjustmentForExcessOver100K(Money annualSalary) {
    final Money adjustmentForExcessOver100K = calculateReductionToPersonalAllowanceOn(annualSalary);

    return minimum(adjustmentForExcessOver100K, PERSONAL_ALLOWANCE);
  }

  private Money calculateReductionToPersonalAllowanceOn(Money annualSalary) {
    Money excessOver100K = annualSalary.subtract(PERSONAL_ALLOWANCE_REDUCTION_RULE_THRESHOLD);

    return reduce1PoundForEvery2PoundsEarnedOn(excessOver100K);
  }

  private Money reduce1PoundForEvery2PoundsEarnedOn(Money excessOver100K) {
    return excessOver100K.divisionBy(2);
  }
}
