package SalarySlipKata.domain_service;

public class SalaryCalculator {

  private final NationalInsuranceCalculator nationalInsuranceCalculator;
  private final TaxCalculator taxCalculator;

  public SalaryCalculator(
      NationalInsuranceCalculator nationalInsuranceCalculator,
      TaxCalculator taxCalculator) {
    this.nationalInsuranceCalculator = nationalInsuranceCalculator;
    this.taxCalculator = taxCalculator;
  }

  public final double getNationInsuranceContributionsFor(double annualSalary) {return nationalInsuranceCalculator.getContributionsFor(annualSalary);}

  public final double getTaxFreeAllowance() {return taxCalculator.getTaxFreeAllowance();}

  public final double getTaxableIncomeFor(double annualSalary) {return taxCalculator.getTaxableIncomeFor(annualSalary);}

  public final double getTaxPayableFor(double annualSalary) {return taxCalculator.getTaxPayableFor(annualSalary);}

  public final double getNetPayableFor(double annualSalary) {return annualSalary - (nationalInsuranceCalculator.getContributionsFor(annualSalary) + taxCalculator.getTaxPayableFor(annualSalary));}
}
