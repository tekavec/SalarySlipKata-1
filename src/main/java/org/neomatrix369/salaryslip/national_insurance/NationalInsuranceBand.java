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
    final Money excessIncome = calculateExcessFrom(annualSalary);

    return contributionsOn(excessIncome);
  }

  private Money calculateExcessFrom(Money annualSalary) {
    final Money lessOrEqualToTheUpperLimit = minimum(annualSalary, upperLimit);

    return lessOrEqualToTheUpperLimit.subtract(lowerLimit);
  }

  private Money contributionsOn(Money excessIncome) {return excessIncome.times(rate);}

  @Override
  public String toString() {
    return format("lowerLimit=%s, upperLimit=%s, rate=%s",
        lowerLimit, upperLimit, rate);
  }
}
