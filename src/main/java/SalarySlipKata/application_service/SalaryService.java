package SalarySlipKata.application_service;

import SalarySlipKata.domain.Employee;

public class SalaryService {

  private NationalInsuranceService nationalInsuranceService;
  private TaxService taxService;

  public SalaryService(NationalInsuranceService nationalInsuranceService, TaxService taxService) {
    this.nationalInsuranceService = nationalInsuranceService;
    this.taxService = taxService;
  }

  public double getTaxFreeAllowanceFor(Employee employee) {
    return taxService.getTaxFreeAllowanceFor(employee.annualSalary());
  }

  public double getTaxableIncomeFor(Employee employee) {
    return taxService.getTaxableIncomeFor(employee.annualSalary());
  }

  public double getNationalInsuranceFor(Employee employee) {
    return nationalInsuranceService.calculateFor(employee.annualSalary());
  }

  public double getTaxPayableFor(Employee employee) {
    return taxService.calculateFor(employee.annualSalary());
  }

  public double getNetPayableFor(Employee employee) {
    double deductions = getNationalInsuranceFor(employee) + getTaxPayableFor(employee);
    return employee.annualSalary() - deductions;
  }
}
