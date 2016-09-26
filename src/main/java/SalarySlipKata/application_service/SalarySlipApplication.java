package SalarySlipKata.application_service;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.SalarySlip;
import SalarySlipKata.domain.TaxDetails;
import SalarySlipKata.domain_service.NationalInsuranceCalculator;
import SalarySlipKata.domain_service.TaxCalculator;

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

  public SalarySlip generateFor(Employee employee) {
    final Money monthlySalary = calculateMonthlySalaryOf(employee);
    final TaxDetails monthlyTaxDetails =
        taxCalculator.calculateMonthlyTaxDetailsFor(employee.annualSalary());
    final Money monthlyNIContributions =
        monthly(nationalInsuranceCalculator.calculateContributionsFor(employee.annualSalary()));

    final Money monthlyNetPayable =
        calculateMonthlyNetPayableWith(monthlySalary, monthlyTaxDetails.taxPayable(), monthlyNIContributions);

    return new SalarySlip(
                  employee,
                  monthlySalary,
                  monthlyTaxDetails,
                  monthlyNIContributions,
                  monthlyNetPayable
    );
  }

  private Money calculateMonthlySalaryOf(Employee employee) {
    return employee.annualSalary().divideBy(TWELVE_MONTHS);
  }

  private Money calculateMonthlyNetPayableWith(
      Money monthlySalary, Money monthlyTaxPayable, Money monthlyNIContributions) {
    return monthlySalary
              .minus(monthlyTaxPayable)
              .minus(monthlyNIContributions);
  }

  private Money monthly(Money amount) {
    return amount.divideBy(TWELVE_MONTHS);
  }
}
