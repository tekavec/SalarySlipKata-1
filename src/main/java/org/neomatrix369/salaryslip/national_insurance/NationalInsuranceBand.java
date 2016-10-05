package org.neomatrix369.salaryslip.national_insurance;

import static org.neomatrix369.salaryslip.components.Money.minimum;
import static java.lang.String.format;

import org.neomatrix369.salaryslip.components.Money;

public class NationalInsuranceBand {
  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  public NationalInsuranceBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  public Money calculateContributionsFor(Money annualSalary) {
    final Money smallerOfTheTwo = minimum(annualSalary, upperLimit);

    final Money excessIncome = smallerOfTheTwo.subtract(lowerLimit);

    return excessIncome.times(rate);
  }

  @Override
  public String toString() {
    return format("lowerLimit=%s, upperLimit=%s, rate=%s",
        lowerLimit, upperLimit, rate);
  }
}
