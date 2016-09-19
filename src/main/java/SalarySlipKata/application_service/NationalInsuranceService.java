package SalarySlipKata.application_service;

public class NationalInsuranceService {

  private static final double NO_CONTRIBUTIONS_ALLOWANCE = 8060.00;

  private static final double BASIC_CONTRIBUTIONS_LOWER_LIMIT = 8060.00;
  private static final double BASIC_CONTRIBUTIONS_HIGHER_LIMIT = 43000.00;
  private static final double BASIC_CONTRIBUTIONS_DIFFERENCE =
      BASIC_CONTRIBUTIONS_HIGHER_LIMIT - BASIC_CONTRIBUTIONS_LOWER_LIMIT;
  private static final double BASIC_CONTRIBUTIONS_RATE = 0.12;

  private static final double HIGHER_CONTRIBUTIONS_LIMIT = 43000.00;
  private static final double HIGHER_CONTRIBUTIONS_RATE = 0.02;

  public double calculateFor(double annualSalary) {
    if (greaterThanHigherContributionsLimit(annualSalary)) {
      return calculateBasicContributions(BASIC_CONTRIBUTIONS_DIFFERENCE) +
          getHigherContributionsLimit(annualSalary);
    }

    double deductibleSalary = getDeductibleSalary(annualSalary);
    if (deductibleSalary > 0.0) {
      return calculateBasicContributions(deductibleSalary);
    }

    return 0.0;
  }

  private boolean greaterThanHigherContributionsLimit(double annualSalary) {return annualSalary > HIGHER_CONTRIBUTIONS_LIMIT;}

  private double getDeductibleSalary(double annualSalary) {return annualSalary - NO_CONTRIBUTIONS_ALLOWANCE;}

  private double getHigherContributionsLimit(double annualSalary) {
    return (annualSalary - HIGHER_CONTRIBUTIONS_LIMIT) * HIGHER_CONTRIBUTIONS_RATE;
  }

  private double calculateBasicContributions(double basicContributionDifference) {return basicContributionDifference * BASIC_CONTRIBUTIONS_RATE;}
}
