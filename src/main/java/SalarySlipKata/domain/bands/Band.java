package SalarySlipKata.domain.bands;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;

public class Band {
  protected Money lowerLimit;
  protected final Money upperLimit;
  protected final double rate;

  public Band(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  private Money differenceBetweenLimits() {
    return upperLimit.minus(lowerLimit);
  }

  public Money calculateFrom(Money annualSalary) {
    if (annualSalary.isBetweenAndInclusiveOf(lowerLimit, upperLimit)) {
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
