package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public class PersonalAllowanceCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money PERSONAL_ALLOWANCE_REDUCTION_THRESHOLD = new Money(100_000.00);

  public Money calculateTaxFreeAllowance(Money annualSalary) {
    Money reducedExcess = calculateAdjustmentDueToReductionRule(annualSalary);

    return PERSONAL_ALLOWANCE.subtract(reducedExcess);
  }

  public Money calculateAdjustmentDueToReductionRule(Money annualSalary) {
    Money excessOver100K = calculateExcessOver100K(annualSalary);

    return reduceBy1PoundForEveryTwoPoundsEarned(excessOver100K);
  }

  private Money reduceBy1PoundForEveryTwoPoundsEarned(Money excessOver100K) {return excessOver100K.divisionBy(2);}

  private Money calculateExcessOver100K(Money annualSalary) {
    return annualSalary.subtract(PERSONAL_ALLOWANCE_REDUCTION_THRESHOLD);
  }
}
