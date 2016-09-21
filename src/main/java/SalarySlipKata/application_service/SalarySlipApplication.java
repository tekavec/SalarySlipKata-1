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
    return new SalarySlip(
                  employee,
                  perMonth(employee.annualSalary()),
                  getTaxDetailsFor(employee),
                  perMonth(nationalInsuranceCalculator.getContributionsFor(employee.annualSalary())),
                  perMonth(getNetPayable(employee.annualSalary()))
    );
  }

  private TaxDetails getTaxDetailsFor(Employee employee) {
    return new TaxDetails(
        perMonth(taxCalculator.getTaxFreeAllowance(employee.annualSalary())),
        perMonth(taxCalculator.getTaxableIncomeFor(employee.annualSalary())),
        perMonth(taxCalculator.getTaxPayableFor(employee.annualSalary()))
    );
  }

  private Money getNetPayable(Money annualSalary) {
    final Money niContributions = nationalInsuranceCalculator.getContributionsFor(annualSalary);
    final Money taxPayable = taxCalculator.getTaxPayableFor(annualSalary);
    return new Money(
        annualSalary
            .minus(niContributions)
            .minus(taxPayable)
    );
  }

  private Money perMonth(Money value) {
    return value.divideBy(TWELVE_MONTHS);
  }
}
