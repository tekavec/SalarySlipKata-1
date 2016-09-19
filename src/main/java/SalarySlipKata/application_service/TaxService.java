package SalarySlipKata.application_service;

public class TaxService {
  private static final double PERSONAL_ALLOWANCE = 11_000.00;
  private static final double BASIC_RATE_LOWER_LIMIT = 11_000.00;
  private static final double BASIC_RATE_UPPER_LIMIT = 43_000.00;
  private static final double
      BASIC_RATE_LIMITS_DIFFERENCE = BASIC_RATE_UPPER_LIMIT - BASIC_RATE_LOWER_LIMIT;
  private static final double BASIC_RATE = 0.20;

  private static final double PERSONAL_ALLOWANCE_REDUCTION_RULE_SALARY_THRESHOLD = 100_000.00;

  private static final double HIGHER_RATE_UPPER_LIMIT = 150_000.00;
  private static final double HIGHER_RATE = 0.40;

  private static final double ADDITIONAL_RATE_LOWER_LIMIT = 150_000.00;
  private static final double ADDITIONAL_RATE = 0.45;

  public double getTaxFreeAllowanceFor(double annualSalary) {
    if (annualSalary > PERSONAL_ALLOWANCE_REDUCTION_RULE_SALARY_THRESHOLD) {
      double difference = annualSalary - PERSONAL_ALLOWANCE_REDUCTION_RULE_SALARY_THRESHOLD;
      double halfDifference = difference / 2;
      return PERSONAL_ALLOWANCE > halfDifference
                ? PERSONAL_ALLOWANCE - halfDifference
                : 0.0;
    }
    return PERSONAL_ALLOWANCE;
  }

  public double getTaxableIncomeFor(double annualSalary) {
    final double taxableIncome = annualSalary - getTaxFreeAllowanceFor(annualSalary);
    return taxableIncome > 0
              ? taxableIncome
              : 0;
  }

  public double calculateFor(double annualSalary) {
    final double taxableIncome = getTaxableIncomeFor(annualSalary);

    if (taxableIncome <= 0.0) {
      return 0.0;
    }

    if (annualSalary > ADDITIONAL_RATE_LOWER_LIMIT) {
      return getAdditionalRateTax(annualSalary)
          + getHigherRateTaxWhen100KRuleApplies(HIGHER_RATE_UPPER_LIMIT)
          + getBasicRateTax();
    }

    if (annualSalary > PERSONAL_ALLOWANCE_REDUCTION_RULE_SALARY_THRESHOLD) {
      return getHigherRateTaxWhen100KRuleApplies(taxableIncome)
          + getBasicRateTax();
    }

    if (annualSalary > BASIC_RATE_UPPER_LIMIT) {
      return getHigherRateTaxOnRemainingSalary(annualSalary)
          + getBasicRateTax();
    }

    return taxableIncome * BASIC_RATE;
  }

  protected double getAdditionalRateTax(double annualSalary) {return (annualSalary - ADDITIONAL_RATE_LOWER_LIMIT) * ADDITIONAL_RATE;}

  protected double getHigherRateTaxWhen100KRuleApplies(double taxableIncome) {return (taxableIncome - BASIC_RATE_LIMITS_DIFFERENCE) * HIGHER_RATE;}

  private double getHigherRateTaxOnRemainingSalary(double annualSalary) {return (annualSalary - BASIC_RATE_UPPER_LIMIT) * HIGHER_RATE;}

  private double getBasicRateTax() {return BASIC_RATE_LIMITS_DIFFERENCE * BASIC_RATE;}
}
