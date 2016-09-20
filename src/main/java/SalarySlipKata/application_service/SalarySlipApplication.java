package SalarySlipKata.application_service;

import static java.lang.String.format;
import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain_service.NationalInsuranceCalculator;
import SalarySlipKata.domain_service.TaxCalculator;

public class SalarySlipApplication {

  private TaxCalculator taxCalculator;
  private NationalInsuranceCalculator nationalInsuranceCalculator;

  public SalarySlipApplication(NationalInsuranceCalculator nationalInsuranceCalculator, TaxCalculator taxCalculator) {
    this.nationalInsuranceCalculator = nationalInsuranceCalculator;
    this.taxCalculator = taxCalculator;
  }

  public List<String> generateFor(Employee employee) {

    List<String> salarySlip = new ArrayList<>();

    salarySlip.add(format("Employee ID: %s%n", valueOf(employee.id())));
    salarySlip.add(format("Employee Name: %s%n", employee.name()));
    salarySlip.add(format("Gross Salary: %s%n", formatAmount(perMonth(employee.annualSalary()))));
    salarySlip.add(format("Tax-free allowance: %s%n", formatAmount(perMonth(taxCalculator.getTaxFreeAllowance()))));
    salarySlip.add(format("Taxable income: %s%n", formatAmount(perMonth(taxCalculator.getTaxableIncomeFor(employee.annualSalary())))));
    salarySlip.add(format("National Insurance contributions: %s%n", formatAmount(perMonth(nationalInsuranceCalculator.getContributionsFor(employee.annualSalary())))));
    salarySlip.add(format("Tax Payable: %s%n", formatAmount(perMonth(taxCalculator.getTaxPayableFor(employee.annualSalary())))));
    salarySlip.add(format("Net Payable: %s%n", formatAmount(perMonth(getNetPayable(employee.annualSalary())))));

    return salarySlip;
  }

  private double getNetPayable(double annualSalary) {
    return annualSalary - (nationalInsuranceCalculator.getContributionsFor(annualSalary)
        + taxCalculator.getTaxPayableFor(annualSalary));
  }

  private String formatAmount(double amount) {
    return format("£%.2f", amount);
  }

  private double perMonth(double value) {
    return value / 12;
  }
}
