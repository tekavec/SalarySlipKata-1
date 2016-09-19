package SalarySlipKata;

import static java.lang.String.format;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain_service.SalaryService;

public class SalarySlipApplication {

  private SalaryService salaryService;

  public SalarySlipApplication(SalaryService salaryService) {this.salaryService = salaryService;}

  public List<String> generateFor(Employee employee) {
    final ArrayList<String> salarySlip = new ArrayList<>();

    salarySlip.add(format("Employee ID: %s\n", employee.id()));
    salarySlip.add(format("Employee Name: %s\n", employee.name()));
    salarySlip.add(format("Gross Salary: %s\n", formatAmount(monthly(employee.annualSalary()))));
    salarySlip.add(format("Tax-free allowance: %s\n", formatAmount(monthly(salaryService.getTaxFreeAllowanceFor(employee)))));
    salarySlip.add(format("Taxable income: %s\n", formatAmount(monthly(salaryService.getTaxableIncomeFor(employee)))));
    salarySlip.add(format("National Insurance contributions: %s\n", formatAmount(monthly(salaryService.getNationalInsuranceFor(employee)))));
    salarySlip.add(format("Tax Payable: %s\n", formatAmount(monthly(salaryService.getTaxPayableFor(employee)))));
    salarySlip.add(format("Net Payable: %s\n", formatAmount(monthly(salaryService.getNetPayableFor(employee)))));

    return salarySlip;
  }

  private String formatAmount(double amount) {
    return format("£%.2f", amount);
  }

  private double monthly(double value) {
    return value / 12;
  }
}
