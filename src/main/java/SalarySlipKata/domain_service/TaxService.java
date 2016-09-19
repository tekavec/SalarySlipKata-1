package SalarySlipKata.domain_service;

import SalarySlipKata.domain.Employee;

public class TaxService {
  public double calculateFor(Employee employee) {
    double taxableIncome = employee.annualSalary() - 11000;
    return taxableIncome * 0.20;
  }
}
