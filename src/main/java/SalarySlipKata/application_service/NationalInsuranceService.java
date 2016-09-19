package SalarySlipKata.application_service;

public class NationalInsuranceService {

  private static final double NO_CONTRIBUTIONS_ALLOWANCE = 8060.00;
  private static final double BASIC_CONTRIBUTIONS_RATE = 0.12;

  public double calculateFor(double annualSalary) {
    double deductibleSalary = annualSalary - NO_CONTRIBUTIONS_ALLOWANCE;

    if (annualSalary > 43000.00) {
      double excessOver43000 = annualSalary - 43000.00;
      return ((43000 - NO_CONTRIBUTIONS_ALLOWANCE) * BASIC_CONTRIBUTIONS_RATE) + (excessOver43000 * 0.02);
    }

    if (deductibleSalary > 0.0) {
      return deductibleSalary * BASIC_CONTRIBUTIONS_RATE;
    }

    return 0.0;
  }
}
