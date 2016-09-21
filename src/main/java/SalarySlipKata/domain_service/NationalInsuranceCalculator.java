package SalarySlipKata.domain_service;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;

public class NationalInsuranceCalculator {
  private static final Money HIGHER_CONTRIBUTIONS_LOWER_LIMIT = new Money(43_000.00);
  private static final double HIGHER_CONTRIBUTIONS_RATE = 0.02;

  private static final Money BASIC_CONTRIBUTIONS_UPPER_LIMIT = new Money(43_000.00);
  private static final Money BASIC_CONTRIBUTIONS_LOWER_LIMIT = new Money(8_060.00);
  private static final Money BASIC_CONTRIBUTIONS_LIMIT_DIFFERENCE =
      BASIC_CONTRIBUTIONS_UPPER_LIMIT.minus(BASIC_CONTRIBUTIONS_LOWER_LIMIT);
  private static final double BASIC_CONTRIBUTIONS_RATE = 0.12;

  private static final int TWELVE_MONTHS = 12;

  public Money calculateMonthlyContributionsFor(Money annualSalary) {
    return calculateContributionsFor(annualSalary).divideBy(TWELVE_MONTHS);
  }

  private Money calculateContributionsFor(Money annualSalary) {
    final Money salaryDifferenceFromHigherContributionsLimit = annualSalary.minus(HIGHER_CONTRIBUTIONS_LOWER_LIMIT);
    if (salaryDifferenceFromHigherContributionsLimit.isGreaterThanZero()) {
      return calculateHigherContributionsFor(salaryDifferenceFromHigherContributionsLimit)
          .plus(calculateBasicContributionsFor(BASIC_CONTRIBUTIONS_LIMIT_DIFFERENCE));
    }

    final Money salaryDifferenceFromBasicContributionsLimit = annualSalary.minus(BASIC_CONTRIBUTIONS_LOWER_LIMIT);
    if (salaryDifferenceFromBasicContributionsLimit.isGreaterThanZero()) {
      return calculateBasicContributionsFor(salaryDifferenceFromBasicContributionsLimit);
    }

    return zero();
  }

  private Money calculateBasicContributionsFor(Money amount) {
    return amount.multiplyBy(BASIC_CONTRIBUTIONS_RATE);
  }

  private Money calculateHigherContributionsFor(Money higherContributionsThresholdDifference) {
    return higherContributionsThresholdDifference.multiplyBy(HIGHER_CONTRIBUTIONS_RATE);
  }
}
