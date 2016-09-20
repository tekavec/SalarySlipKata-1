package SalarySlipKata.domain_service;

public class NationalInsuranceCalculator {
  private static final double HIGHER_CONTRIBUTIONS_LOWER_LIMIT = 43_000.00;
  private static final double HIGHER_CONTRIBUTIONS_RATE = 0.02;

  private static final double BASIC_CONTRIBUTIONS_UPPER_LIMIT = 43_000.00;
  private static final double BASIC_CONTRIBUTIONS_LOWER_LIMIT = 8_060.00;
  private static final double BASIC_CONTRIBUTIONS_LIMIT_DIFFERENCE = BASIC_CONTRIBUTIONS_UPPER_LIMIT - BASIC_CONTRIBUTIONS_LOWER_LIMIT;
  private static final double BASIC_CONTRIBUTIONS_RATE = 0.12;

  public final double getContributionsFor(double annualSalary) {
    final double salaryDifferenceFromHigherContributionsLimit = annualSalary - HIGHER_CONTRIBUTIONS_LOWER_LIMIT;
    if (salaryDifferenceFromHigherContributionsLimit > 0) {
      return calculateHigherContributionsFrom(salaryDifferenceFromHigherContributionsLimit) +
          calculateBasicContributionsFrom(BASIC_CONTRIBUTIONS_LIMIT_DIFFERENCE);
    }

    final double salaryDifferenceFromBasicContributionsLimit = annualSalary - BASIC_CONTRIBUTIONS_LOWER_LIMIT;
    if (salaryDifferenceFromBasicContributionsLimit > 0) {
      return calculateBasicContributionsFrom(salaryDifferenceFromBasicContributionsLimit);
    }

    return 0.0;
  }

  private double calculateBasicContributionsFrom(double amount) {return amount * BASIC_CONTRIBUTIONS_RATE;}

  private double calculateHigherContributionsFrom(double higherContributionsThresholdDifference) {return higherContributionsThresholdDifference * HIGHER_CONTRIBUTIONS_RATE;}
}
