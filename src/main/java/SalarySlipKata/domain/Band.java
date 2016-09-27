package SalarySlipKata.domain;

import static SalarySlipKata.domain.Money.zero;

public class Band {
  private final Money lowerLimit;
  private final Money upperLimit;
  private final double rate;

  public Band(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  private Money differenceBetweenLimits() {
    return upperLimit.minus(lowerLimit);
  }

  public Money calculateFrom(Money annualSalary) {
    if (annualSalary.isBetween(lowerLimit, upperLimit)) {
      final Money excessIncomeOverLowerLimit = annualSalary.minus(lowerLimit);
      return bandValueFor(excessIncomeOverLowerLimit);
    }

    if (annualSalary.isGreaterThan(upperLimit)) {
      Money excessIncome = differenceBetweenLimits();
      return bandValueFor(excessIncome);
    }

    return zero();
  }

  private Money bandValueFor(Money amount) {return amount.multiplyBy(rate);}
}
