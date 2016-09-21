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
    final TaxDetails monthlyTaxDetails = taxCalculator.calculateMonthlyTaxDetailsFor(employee.annualSalary());
    final Money monthlyNIContributions =
        nationalInsuranceCalculator.calculateMonthlyContributionsFor(employee.annualSalary());

    Money monthlySalary = calculateMonthlySalaryOf(employee);
    final Money monthlyNetPayable =
        calculateMonthlyNetPayable(monthlySalary, monthlyTaxDetails, monthlyNIContributions);

    return new SalarySlip(
                  employee,
                  monthlySalary,
                  monthlyTaxDetails,
                  monthlyNIContributions,
                  monthlyNetPayable
    );
  }

  private Money calculateMonthlySalaryOf(Employee employee) {return employee.annualSalary().divideBy(TWELVE_MONTHS);}

  private Money calculateMonthlyNetPayable(Money monthlySalary, TaxDetails monthlyTaxDetails, Money monthlyNIContributions) {
    return monthlySalary
              .minus(monthlyTaxDetails.taxPayable())
              .minus(monthlyNIContributions);
  }
}
