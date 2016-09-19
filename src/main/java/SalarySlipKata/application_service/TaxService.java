package SalarySlipKata.application_service;

import SalarySlipKata.domain.Employee;

public class TaxService {
  private static final double PERSONAL_ALLOWANCE = 11000.00;
  private static final double BASIC_RATE = 0.20;

  public double getTaxFreeAllowanceFor(Employee employee) {
    return PERSONAL_ALLOWANCE;
  }

  public double getTaxableIncomeFor(Employee employee) {
    return employee.annualSalary() - getTaxFreeAllowanceFor(employee);
  }

  public double calculateFor(Employee employee) {
    return getTaxableIncomeFor(employee) * BASIC_RATE;
  }
}
