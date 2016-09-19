package SalarySlipKata.domain_service;

import SalarySlipKata.domain.Employee;

public class NationalInsuranceService {
  public double calculateFor(Employee employee) {
    double annualSalary = employee.annualSalary();
    double deducationSalary = annualSalary - 8060.00;
    return deducationSalary * 0.12;
  }
}
