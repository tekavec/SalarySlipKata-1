package SalarySlipKata.domain_service;

public class SalaryCalculator {

  private static final double UPTO_AMOUNT_NI_CONTRIBUTION_IS_EXEMPTED = 8_060.00;
  private static final double NI_BASIC_CONTRIBUTIONS_RATE = 0.12;

  private static final double PERSONAL_ALLOWANCE = 11_000.00;
  private static final double TAX_BASIC_RATE = 0.20;

  public final double getNationInsuranceContributions(double annualSalary) {return (annualSalary - UPTO_AMOUNT_NI_CONTRIBUTION_IS_EXEMPTED) * NI_BASIC_CONTRIBUTIONS_RATE;}

  public final double getTaxFreeAllowance() {return PERSONAL_ALLOWANCE;}

  public final double getTaxableIncome(double annualSalary) {return annualSalary - getTaxFreeAllowance();}

  public final double getTaxPayable(double annualSalary) {return (annualSalary - getTaxFreeAllowance()) * TAX_BASIC_RATE;}

  public final double getNetPayable(double annualSalary) {return annualSalary - (getNationInsuranceContributions(annualSalary) + getTaxPayable(annualSalary));}
}
