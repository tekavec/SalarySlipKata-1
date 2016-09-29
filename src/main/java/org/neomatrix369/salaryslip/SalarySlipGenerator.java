package org.neomatrix369.salaryslip;

import org.neomatrix369.salaryslip.components.SalarySlip;
import org.neomatrix369.salaryslip.national_insurance.NationalInsuranceCalculator;
import org.neomatrix369.salaryslip.tax.TaxDetails;
import org.neomatrix369.salaryslip.components.Employee;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.tax.TaxCalculator;

public class SalarySlipGenerator {

  private TaxCalculator taxCalculator;
  private NationalInsuranceCalculator nationalInsuranceCalculator;

  public SalarySlipGenerator(
      NationalInsuranceCalculator nationalInsuranceCalculator,
      TaxCalculator taxCalculator) {
    this.nationalInsuranceCalculator = nationalInsuranceCalculator;
    this.taxCalculator = taxCalculator;
  }

  public SalarySlip generateFor(Employee employee) {
    final TaxDetails monthlyTaxDetails = taxCalculator.calculateMonthlyTaxDetailsFor(employee.annualSalary());
    final Money monthlyNIContributions =
        nationalInsuranceCalculator.calculateMonthlyContributionsFor(employee.annualSalary());

    return new SalarySlip(
                  employee,
                  employee.monthlySalary(),
                  monthlyTaxDetails,
                  monthlyNIContributions
    );
  }
}
