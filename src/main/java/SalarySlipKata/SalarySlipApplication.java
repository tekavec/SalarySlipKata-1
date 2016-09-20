package SalarySlipKata;

import static java.lang.String.format;
import static java.lang.String.valueOf;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.Employee;

public class SalarySlipApplication {

  public List<String> generateFor(Employee employee) {
    List<String> salarySlip = new ArrayList<>();

    salarySlip.add(format("Employee ID: %s%n", valueOf(employee.id())));
    salarySlip.add(format("Employee Name: %s%n", employee.name()));

    final int annualSalary = employee.annualSalary();
    salarySlip.add(format("Gross Salary: %s%n", formatAmount(perMonth(annualSalary))));
    double taxFreeAllowance = 11_000.00;
    salarySlip.add(format("Tax-free allowance: %s%n", formatAmount(perMonth(taxFreeAllowance))));
    double taxableIncome = annualSalary - taxFreeAllowance;
    salarySlip.add(format("Taxable income: %s%n", formatAmount(perMonth(taxableIncome))));
    double nationInsuranceContributions = (annualSalary - 8060) * 0.12;
    salarySlip.add(format("National Insurance contributions: %s%n", formatAmount(perMonth(nationInsuranceContributions))));
    double taxPayable = (annualSalary - taxFreeAllowance) * 0.20;
    salarySlip.add(format("Tax Payable: %s%n", formatAmount(perMonth(taxPayable))));
    double netPayable = annualSalary - (nationInsuranceContributions + taxPayable);
    salarySlip.add(format("Net Payable: %s%n", formatAmount(perMonth(netPayable))));

    return salarySlip;
  }

  private String formatAmount(double amount) {
    return format("£%.2f", amount);
  }

  private double perMonth(double value) {
    return value / 12;
  }
}
