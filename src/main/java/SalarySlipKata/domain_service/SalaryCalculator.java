package SalarySlipKata.domain_service;

public class SalaryCalculator {

  public final double getNationInsuranceContributions(double annualSalary) {return (annualSalary - 8060) * 0.12;}

  public final double getTaxFreeAllowance() {return 11_000.00;}

  public final double getTaxableIncome(double annualSalary) {return annualSalary - getTaxFreeAllowance();}

  public final double getTaxPayable(double annualSalary) {return (annualSalary - getTaxFreeAllowance()) * 0.20;}

  public final double getNetPayable(double annualSalary) {return annualSalary - (getNationInsuranceContributions(annualSalary) + getTaxPayable(annualSalary));}
}
