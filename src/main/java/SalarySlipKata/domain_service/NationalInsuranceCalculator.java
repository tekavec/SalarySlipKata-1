package SalarySlipKata.domain_service;

import static SalarySlipKata.domain.GBP.zero;

import SalarySlipKata.domain.GBP;

public class NationalInsuranceCalculator {
  private static final GBP HIGHER_CONTRIBUTIONS_LOWER_LIMIT = new GBP(43_000.00);
  private static final double HIGHER_CONTRIBUTIONS_RATE = 0.02;

  private static final GBP BASIC_CONTRIBUTIONS_UPPER_LIMIT = new GBP(43_000.00);
  private static final GBP BASIC_CONTRIBUTIONS_LOWER_LIMIT = new GBP(8_060.00);
  private static final GBP BASIC_CONTRIBUTIONS_LIMIT_DIFFERENCE =
      BASIC_CONTRIBUTIONS_UPPER_LIMIT.minus(BASIC_CONTRIBUTIONS_LOWER_LIMIT);
  private static final double BASIC_CONTRIBUTIONS_RATE = 0.12;

  public final GBP getContributionsFor(GBP annualSalary) {
    final GBP salaryDifferenceFromHigherContributionsLimit = annualSalary.minus(HIGHER_CONTRIBUTIONS_LOWER_LIMIT);
    if (salaryDifferenceFromHigherContributionsLimit.isGreaterThanZero()) {
      return calculateHigherContributionsFrom(salaryDifferenceFromHigherContributionsLimit)
          .plus(calculateBasicContributionsFrom(BASIC_CONTRIBUTIONS_LIMIT_DIFFERENCE));
    }

    final GBP salaryDifferenceFromBasicContributionsLimit = annualSalary.minus(BASIC_CONTRIBUTIONS_LOWER_LIMIT);
    if (salaryDifferenceFromBasicContributionsLimit.isGreaterThanZero()) {
      return calculateBasicContributionsFrom(salaryDifferenceFromBasicContributionsLimit);
    }

    return zero();
  }

  private GBP calculateBasicContributionsFrom(GBP amount) {return amount.multiplyBy(BASIC_CONTRIBUTIONS_RATE);}

  private GBP calculateHigherContributionsFrom(GBP higherContributionsThresholdDifference) {
    return higherContributionsThresholdDifference.multiplyBy(HIGHER_CONTRIBUTIONS_RATE);}
}
