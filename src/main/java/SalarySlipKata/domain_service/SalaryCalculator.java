package SalarySlipKata.domain_service;

public class SalaryCalculator {

  private static final double PERSONAL_ALLOWANCE = 11_000.00;
  private static final double TAX_BASIC_RATE = 0.20;
  private final NationalInsuranceCalculator nationalInsuranceCalculator;

  public SalaryCalculator(NationalInsuranceCalculator nationalInsuranceCalculator) {
    this.nationalInsuranceCalculator = nationalInsuranceCalculator;
  }

  public final double getNationInsuranceContributions(double annualSalary) {return nationalInsuranceCalculator.getNationInsuranceContributions(annualSalary);}

  public final double getTaxFreeAllowance() {return PERSONAL_ALLOWANCE;}

  public final double getTaxableIncome(double annualSalary) {return annualSalary - getTaxFreeAllowance();}

  public final double getTaxPayable(double annualSalary) {return (annualSalary - getTaxFreeAllowance()) * TAX_BASIC_RATE;}

  public final double getNetPayable(double annualSalary) {return annualSalary - (nationalInsuranceCalculator.getNationInsuranceContributions(annualSalary) + getTaxPayable(annualSalary));}
}
