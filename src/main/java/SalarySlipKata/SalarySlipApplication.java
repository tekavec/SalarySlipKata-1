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
    salarySlip.add(format("Gross Salary: %s%n", formatAmount(perMonth(employee.annualSalary()))));
    salarySlip.add(format("Tax-free allowance: %s%n", formatAmount(perMonth(getTaxFreeAllowance()))));
    salarySlip.add(format("Taxable income: %s%n", formatAmount(perMonth(getTaxableIncome(employee.annualSalary())))));
    salarySlip.add(format("National Insurance contributions: %s%n", formatAmount(perMonth(getNationInsuranceContributions(employee.annualSalary())))));
    salarySlip.add(format("Tax Payable: %s%n", formatAmount(perMonth(getTaxPayable(employee.annualSalary())))));
    salarySlip.add(format("Net Payable: %s%n", formatAmount(perMonth(getNetPayable(employee.annualSalary())))));

    return salarySlip;
  }

  private double getNationInsuranceContributions(double annualSalary) {return (annualSalary - 8060) * 0.12;}

  private double getTaxFreeAllowance() {return 11_000.00;}

  private double getTaxableIncome(double annualSalary) {return annualSalary - getTaxFreeAllowance();}

  private double getTaxPayable(double annualSalary) {return (annualSalary - getTaxFreeAllowance()) * 0.20;}

  private double getNetPayable(double annualSalary) {return annualSalary - (getNationInsuranceContributions(annualSalary) + getTaxPayable(annualSalary));}

  private String formatAmount(double amount) {
    return format("£%.2f", amount);
  }

  private double perMonth(double value) {
    return value / 12;
  }
}
