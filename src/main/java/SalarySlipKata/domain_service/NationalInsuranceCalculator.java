package SalarySlipKata.domain_service;

public class NationalInsuranceCalculator {
  private static final double AMOUNT_UP_TO_WHICH_NI_CONTRIBUTION_IS_EXEMPTED = 8_060.00;
  private static final double NI_BASIC_CONTRIBUTIONS_RATE = 0.12;

  public final double getContributionsFor(double annualSalary) {
    final double nationalInsuranceThresholdDifference = annualSalary - AMOUNT_UP_TO_WHICH_NI_CONTRIBUTION_IS_EXEMPTED;

    if (annualSalary > 43_000.00) {
      return (annualSalary - 43_000.00) * 0.02 +
          (43_000.00 - 8_060) * NI_BASIC_CONTRIBUTIONS_RATE;
    }

    if (nationalInsuranceThresholdDifference > 0) {
      return nationalInsuranceThresholdDifference * NI_BASIC_CONTRIBUTIONS_RATE;
    }
    return 0.0;
  }
}
