package org.somename.salaryslip;

import org.somename.salaryslip.components.Employee;
import org.somename.salaryslip.components.Money;
import org.somename.salaryslip.components.SalarySlip;
import org.somename.salaryslip.tax.TaxDetails;

public class SalarySlipGenerator {

  private final NationalInsuranceCalculator nationalInsuranceCalculator;
  private final TaxCalculator taxCalculator;

  public SalarySlipGenerator(
      NationalInsuranceCalculator nationalInsuranceCalculator, TaxCalculator taxCalculator) {
    this.nationalInsuranceCalculator = nationalInsuranceCalculator;
    this.taxCalculator = taxCalculator;
  }

  public SalarySlip generateFor(Employee employee) {
    Money monthlyGrossSalary = employee.monthlySalary();
    Money monthlyNIContributions = nationalInsuranceCalculator.calculateMonthlyContributionsFor(employee.annualSalary());

    TaxDetails monthlyTaxDetails = taxCalculator.calculateMonthlyTaxDetailsFor(employee.annualSalary());

    return new SalarySlip(
        employee,
        monthlyGrossSalary,
        monthlyNIContributions,
        monthlyTaxDetails
    );
  }
}
