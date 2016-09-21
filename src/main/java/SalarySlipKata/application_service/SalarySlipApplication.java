package SalarySlipKata.application_service;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.GBP;
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

  private GBP getNetPayable(GBP annualSalary) {
    final GBP niContributions = nationalInsuranceCalculator.getContributionsFor(annualSalary);
    final GBP taxPayable = taxCalculator.getTaxPayableFor(annualSalary);
    return new GBP(
        annualSalary
            .minus(niContributions)
            .minus(taxPayable)
    );
  }

  private GBP perMonth(GBP value) {
    return value.divideBy(TWELVE_MONTHS);
  }
}
