package SalarySlipKata.application_service;

public class TaxService {
  private static final double PERSONAL_ALLOWANCE = 11000.00;
  private static final double BASIC_RATE = 0.20;

  public double getTaxFreeAllowanceFor(double annualSalary) {
    if (annualSalary > 100000) {
      double difference = annualSalary - 100000;
      double halfDifference = difference / 2;
      return PERSONAL_ALLOWANCE >= halfDifference
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

    if (annualSalary > 150000.00) {
      return (annualSalary - 150000.00) * 0.45 +
          ((150000 - 32000.00) * 0.40) +
          ((43000.00 - 11000.00) * BASIC_RATE);
    }

    if (annualSalary > 100000.00) {
      return ((taxableIncome - 32000.00) * 0.40) +
          ((43000.00 - 11000.00) * BASIC_RATE);
    }

    if (annualSalary > 43000.00) {
      return ((annualSalary - 43000.00) * 0.40) +
          ((43000.00 - 11000.00) * BASIC_RATE);
    }

    return taxableIncome * BASIC_RATE;
  }
}
