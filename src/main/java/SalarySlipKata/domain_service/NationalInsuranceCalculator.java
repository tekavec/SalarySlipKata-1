package SalarySlipKata.domain_service;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;

public class NationalInsuranceCalculator {
  private static final Money HIGHER_CONTRIBUTIONS_LOWER_LIMIT = new Money(43_000.00);
  private static final double HIGHER_CONTRIBUTIONS_RATE = 0.02;

  private static final Money BASIC_CONTRIBUTIONS_LOWER_LIMIT = new Money(8_060.00);
  private static final double BASIC_CONTRIBUTIONS_RATE = 0.12;

  private static final int TWELVE_MONTHS = 12;

  public Money calculateMonthlyContributionsFor(Money annualSalary) {
    return calculateContributionsFor(annualSalary).divideBy(TWELVE_MONTHS);
  }

  private Money calculateContributionsFor(Money annualSalary) {
    Money contributions = zero();

    Money difference = deductHigherContributionsLowerLimitFrom(annualSalary);
    if (difference.isGreaterThanZero()) {
      contributions = calculateHigherContributionsFor(difference);
      annualSalary = annualSalary.minus(difference);
    }

    difference = deductBasicContributionsLowerLimitFrom(annualSalary);
    if (difference.isGreaterThanOrEqualToZero()) {
      contributions = contributions.plus(calculateBasicContributionsFor(difference));
    }

    return contributions;
  }

  private Money deductBasicContributionsLowerLimitFrom(Money amount) {
    return amount.minus(BASIC_CONTRIBUTIONS_LOWER_LIMIT);
  }

  private Money deductHigherContributionsLowerLimitFrom(Money amount) {
    return amount.minus(HIGHER_CONTRIBUTIONS_LOWER_LIMIT);
  }

  private Money calculateBasicContributionsFor(Money amount) {
    return amount.multiplyBy(BASIC_CONTRIBUTIONS_RATE);
  }

  private Money calculateHigherContributionsFor(Money amount) {
    return amount.multiplyBy(HIGHER_CONTRIBUTIONS_RATE);
  }
}
