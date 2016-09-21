package SalarySlipKata.application_service;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.SalarySlip;
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
    return new SalarySlip(
                  employee,
                  monthlySalaryOf(employee),
                  taxCalculator.getMonthlyTaxDetailsFor(employee.annualSalary()),
                  nationalInsuranceCalculator.getMonthlyContributionsFor(employee.annualSalary()),
                  getMonthlyNetPayable(employee.annualSalary())
    );
  }

  private Money monthlySalaryOf(Employee employee) {return employee.annualSalary().divideBy(TWELVE_MONTHS);}

  private Money getMonthlyNetPayable(Money annualSalary) {
    final Money nationalInsuranceContributions = nationalInsuranceCalculator.getMonthlyContributionsFor(annualSalary);
    final Money taxPayable = taxCalculator.getMonthlyTaxPayableFor(annualSalary);

    return annualSalary.divideBy(TWELVE_MONTHS)
              .minus(nationalInsuranceContributions)
              .minus(taxPayable);
  }
}
