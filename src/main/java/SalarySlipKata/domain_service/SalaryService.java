package SalarySlipKata.domain_service;

import SalarySlipKata.domain.Employee;

public class SalaryService {
  private NationalInsuranceService nationalInsuranceService;
  private TaxService taxService;

  public SalaryService(NationalInsuranceService nationalInsuranceService, TaxService taxService) {
    this.nationalInsuranceService = nationalInsuranceService;
    this.taxService = taxService;
  }

  public double getTaxFreeAllowanceFor(Employee employee) {
    return 11000.00;
  }

  public double getTaxableIncomeFor(Employee employee) {
    return employee.annualSalary() - getTaxFreeAllowanceFor(employee);
  }

  public double getNationalInsuranceFor(Employee employee) {
    return nationalInsuranceService.calculateFor(employee);
  }

  public double getTaxPayableFor(Employee employee) {
    return taxService.calculateFor(employee);
  }

  public double getNetPayableFor(Employee employee) {
    double deductions = getNationalInsuranceFor(employee) + getTaxPayableFor(employee);
    return employee.annualSalary() - deductions;
  }
}
