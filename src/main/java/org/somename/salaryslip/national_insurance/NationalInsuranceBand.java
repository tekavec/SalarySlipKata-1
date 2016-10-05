package org.somename.salaryslip.national_insurance;

import static org.somename.salaryslip.components.Money.minimum;

import org.somename.salaryslip.components.Money;

public class NationalInsuranceBand {
  private final Money lowerLimit;
  private Money upperLimit;
  private final double rate;

  public NationalInsuranceBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  public Money calculateContributionsFor(Money annualSalary) {
    final Money excessIncome = calculateExcessIncomeFor(annualSalary);
    return excessIncome.times(rate);
  }

  private Money calculateExcessIncomeFor(Money annualSalary) {
    Money minimumOfTheTwo = minimum(annualSalary, upperLimit);
    return minimumOfTheTwo.subtract(lowerLimit);
  }
}
