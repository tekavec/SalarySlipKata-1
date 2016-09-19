package SalarySlipKata.application_service;

import SalarySlipKata.domain.Employee;

public class NationalInsuranceService {

  private static final double PERSONAL_ALLOWANCE = 8060.00;
  private static final double BASIC_CONTRIBUTIONS_RATE = 0.12;

  public double calculateFor(Employee employee) {
    double deductionSalary = employee.annualSalary() - PERSONAL_ALLOWANCE;
    return deductionSalary * BASIC_CONTRIBUTIONS_RATE;
  }
}
