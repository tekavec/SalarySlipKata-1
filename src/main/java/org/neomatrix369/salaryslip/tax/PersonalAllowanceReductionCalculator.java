package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public class PersonalAllowanceReductionCalculator {

  private static final Money
      SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION = new Money(100_000.00);
  private static final Money FULL_PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final Money MAXIMUM_ALLOWED_EXCESS_OVER_100K = FULL_PERSONAL_ALLOWANCE.times(2);

  public Money reductionFor(Money annualSalary) {
    Money excessOver100K = excessOver100KFrom(annualSalary);

    if (excessOver100K.isGreaterThan(new Money(MAXIMUM_ALLOWED_EXCESS_OVER_100K))) {
      return FULL_PERSONAL_ALLOWANCE;
    }

    return reduceToOnePoundForEveryTwoPoundsEarned(excessOver100K);
  }

  private Money excessOver100KFrom(Money annualSalary) {
    return annualSalary.subtract(SALARY_THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION);
  }

  private Money reduceToOnePoundForEveryTwoPoundsEarned(Money excessOver100K) {return excessOver100K.divisionBy(2);}
}
