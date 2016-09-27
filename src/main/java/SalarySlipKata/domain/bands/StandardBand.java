package SalarySlipKata.domain.bands;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;

public class StandardBand {
  protected final Money lowerLimit;
  protected final Money upperLimit;
  protected final double rate;

  public StandardBand(Money lowerLimit, Money upperLimit, double rate) {
    this.lowerLimit = lowerLimit;
    this.upperLimit = upperLimit;
    this.rate = rate;
  }

  public Money calculateFrom(Money annualSalary) {
    if (annualSalary.isBetweenAndInclusiveOf(lowerLimit, upperLimit)) {
      return bandValueFor(calculateExcessFrom(annualSalary, lowerLimit));
    }

    if (annualSalary.isGreaterThan(upperLimit)) {
      return bandValueFor(calculateExcessFrom(upperLimit, lowerLimit));
    }

    return zero();
  }

  protected Money calculateExcessFrom(Money upperLimit, Money lowerLimit) {
    return upperLimit.minus(lowerLimit);
  }

  protected Money bandValueFor(Money amount) {return amount.multiplyBy(rate);}
}
