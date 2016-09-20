package SalarySlipKata.domain_service;

public class TaxCalculator {
  private static final double PERSONAL_ALLOWANCE = 11_000.00;
  private static final double TAX_BASIC_RATE = 0.20;

  public final double getTaxFreeAllowance() {return PERSONAL_ALLOWANCE;}

  public final double getTaxableIncomeFor(double annualSalary) {return annualSalary - getTaxFreeAllowance();}

  public final double getTaxPayableFor(double annualSalary) {return (annualSalary - getTaxFreeAllowance()) * TAX_BASIC_RATE;}
}
