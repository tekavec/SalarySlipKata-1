package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public class PersonalAllowanceReductionCalculator {

  private static final Money SALARY_THRESHOLD = new Money(100_000.00);

  private static final Money FULL_PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money MAXIMUM_ALLOWED_EXCESS_FOR_REDUCTION = FULL_PERSONAL_ALLOWANCE.times(2);

  public Money reductionFor(Money annualSalary) {
    Money salaryExcess = annualSalary.subtract(SALARY_THRESHOLD);

    return salaryExcess.isGreaterThan(MAXIMUM_ALLOWED_EXCESS_FOR_REDUCTION)
              ? FULL_PERSONAL_ALLOWANCE
              : reduceByOnePoundForEveryTwoPoundsEarned(salaryExcess);
  }

  private Money reduceByOnePoundForEveryTwoPoundsEarned(Money amount) {
    return amount.divideBy(2);
  }
}
