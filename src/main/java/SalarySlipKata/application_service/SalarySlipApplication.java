package SalarySlipKata.application_service;

import static java.lang.String.format;
import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain_service.SalaryCalculator;

public class SalarySlipApplication {

  private final SalaryCalculator salaryCalculator;

  public SalarySlipApplication(SalaryCalculator salaryCalculator) {this.salaryCalculator = salaryCalculator;}

  public List<String> generateFor(Employee employee) {

    List<String> salarySlip = new ArrayList<>();

    salarySlip.add(format("Employee ID: %s%n", valueOf(employee.id())));
    salarySlip.add(format("Employee Name: %s%n", employee.name()));
    salarySlip.add(format("Gross Salary: %s%n", formatAmount(perMonth(employee.annualSalary()))));
    salarySlip.add(format("Tax-free allowance: %s%n", formatAmount(perMonth(salaryCalculator.getTaxFreeAllowance()))));
    salarySlip.add(format("Taxable income: %s%n", formatAmount(perMonth(salaryCalculator.getTaxableIncomeFor(employee.annualSalary())))));
    salarySlip.add(format("National Insurance contributions: %s%n", formatAmount(perMonth(salaryCalculator.getNationInsuranceContributionsFor(employee.annualSalary())))));
    salarySlip.add(format("Tax Payable: %s%n", formatAmount(perMonth(salaryCalculator.getTaxPayableFor(employee.annualSalary())))));
    salarySlip.add(format("Net Payable: %s%n", formatAmount(perMonth(salaryCalculator.getNetPayableFor(employee.annualSalary())))));

    return salarySlip;
  }

  private String formatAmount(double amount) {
    return format("£%.2f", amount);
  }

  private double perMonth(double value) {
    return value / 12;
  }
}
