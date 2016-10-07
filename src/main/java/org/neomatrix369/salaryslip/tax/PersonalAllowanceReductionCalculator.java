package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public class PersonalAllowanceReductionCalculator {

  private static final Money FULL_PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money PERSONAL_ALLOWANCE_REDUCTION_THRESHOLD = new Money(100_000.00);
  private static final Money MAXIMUM_ALLOWED_EXCESS_FOR_REDUCTION = FULL_PERSONAL_ALLOWANCE.times(2);

  public Money reductionFor(Money annualSalary) {
    Money excessOver100K = calculateExcessOver100K(annualSalary);

    if (excessOver100K.isGreaterThan(MAXIMUM_ALLOWED_EXCESS_FOR_REDUCTION)) {
      return FULL_PERSONAL_ALLOWANCE;
    }

    return reduceByOnePoundForEveryTwoPoundsEarned(excessOver100K);
  }

  private Money reduceByOnePoundForEveryTwoPoundsEarned(Money amount) {
    return amount.divisionBy(2);
  }

  private Money calculateExcessOver100K(Money annualSalary) {
    return annualSalary.subtract(PERSONAL_ALLOWANCE_REDUCTION_THRESHOLD);
  }
}
