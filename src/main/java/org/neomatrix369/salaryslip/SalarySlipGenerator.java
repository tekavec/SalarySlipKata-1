package org.neomatrix369.salaryslip;

import org.neomatrix369.salaryslip.components.Employee;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.SalarySlip;
import org.neomatrix369.salaryslip.components.TaxDetails;
import org.neomatrix369.salaryslip.national_insurance.NationalInsuranceCalculator;
import org.neomatrix369.salaryslip.tax.TaxCalculator;

public class SalarySlipGenerator {

  private final NationalInsuranceCalculator nationalInsuranceCalculator;
  private final TaxCalculator taxCalculator;

  public SalarySlipGenerator(
      NationalInsuranceCalculator nationalInsuranceCalculator,
      TaxCalculator taxCalculator) {
    this.nationalInsuranceCalculator = nationalInsuranceCalculator;
    this.taxCalculator = taxCalculator;
  }

  public SalarySlip generateFor(Employee employee) {
    Money monthlyGrossSalary = employee.monthlySalary();

    TaxDetails monthlyTaxDetails = taxCalculator.monthlyTaxDetails(employee.annualSalary());

    Money monthlyNationalInsuranceContributions =
        nationalInsuranceCalculator.monthlyNationalInsuranceContributionsFor(employee.annualSalary());

    return new SalarySlip(
        employee,
        monthlyGrossSalary,
        monthlyNationalInsuranceContributions,
        monthlyTaxDetails
    );
  }

}
