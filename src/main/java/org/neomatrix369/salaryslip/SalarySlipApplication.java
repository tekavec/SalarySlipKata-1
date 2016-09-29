package org.neomatrix369.salaryslip;

import org.neomatrix369.salaryslip.components.SalarySlip;
import org.neomatrix369.salaryslip.national_insurance.NationalInsuranceCalculator;
import org.neomatrix369.salaryslip.tax.TaxDetails;
import org.neomatrix369.salaryslip.components.Employee;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.tax.TaxCalculator;

public class SalarySlipApplication {

  private static final int TWELVE_MONTHS = 12;

  private TaxCalculator taxCalculator;
  private NationalInsuranceCalculator nationalInsuranceCalculator;

  public SalarySlipApplication(
      NationalInsuranceCalculator nationalInsuranceCalculator,
      TaxCalculator taxCalculator) {
    this.nationalInsuranceCalculator = nationalInsuranceCalculator;
    this.taxCalculator = taxCalculator;
  }

  public SalarySlip salarySlipFor(Employee employee) {
    final Money monthlySalary = monthly(employee.annualSalary());
    final TaxDetails monthlyTaxDetails =
        monthly(taxCalculator.calculateTaxDetailsFor(employee.annualSalary()));
    final Money monthlyNIContributions =
        monthly(nationalInsuranceCalculator.calculateContributionsFor(employee.annualSalary()));

    final Money monthlyNetPayable = calculateMonthlyNetPayableWith(
        monthlySalary, monthlyTaxDetails.taxPayable(), monthlyNIContributions);

    return new SalarySlip(
                  employee,
                  monthlySalary,
                  monthlyTaxDetails,
                  monthlyNIContributions,
                  monthlyNetPayable
    );
  }

  private Money calculateMonthlyNetPayableWith(
      Money monthlySalary, Money monthlyTaxPayable, Money monthlyNIContributions) {
    return monthlySalary
              .minus(monthlyTaxPayable)
              .minus(monthlyNIContributions);
  }

  private TaxDetails monthly(TaxDetails taxDetails) {
    return new TaxDetails(
        monthly(taxDetails.taxFreeAllowance()),
        monthly(taxDetails.taxableIncome()),
        monthly(taxDetails.taxPayable())
    );
  }

  private Money monthly(Money amount) {
    return amount.divideBy(TWELVE_MONTHS);
  }
}
