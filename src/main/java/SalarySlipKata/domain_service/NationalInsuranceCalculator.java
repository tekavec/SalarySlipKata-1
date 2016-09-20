package SalarySlipKata.domain_service;

public class NationalInsuranceCalculator {
  private static final double AMOUNT_UP_TO_WHICH_NI_CONTRIBUTION_IS_EXEMPTED = 8_060.00;
  private static final double NI_BASIC_CONTRIBUTIONS_RATE = 0.12;

  public final double getContributionsFor(double annualSalary) {return (annualSalary - AMOUNT_UP_TO_WHICH_NI_CONTRIBUTION_IS_EXEMPTED) * NI_BASIC_CONTRIBUTIONS_RATE;}
}
