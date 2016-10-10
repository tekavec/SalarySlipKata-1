package org.neomatrix369.salaryslip;

import org.neomatrix369.salaryslip.components.Employee;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.SalarySlip;
import org.neomatrix369.salaryslip.components.TaxDetails;
import org.neomatrix369.salaryslip.national_insurance.NationalInsuranceCalculator;
import org.neomatrix369.salaryslip.tax.PersonalAllowanceReductionCalculator;
import org.neomatrix369.salaryslip.tax.TaxCalculator;

public class SalarySlipGenerator {

  private static final int TWELVE_MONTHS = 12;
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);

  private final NationalInsuranceCalculator nationalInsuranceCalculator;
  private final TaxCalculator taxCalculator;
  private final PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator;

  public SalarySlipGenerator(
      NationalInsuranceCalculator nationalInsuranceCalculator,
      TaxCalculator taxCalculator,
      PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator) {
    this.nationalInsuranceCalculator = nationalInsuranceCalculator;
    this.taxCalculator = taxCalculator;
    this.personalAllowanceReductionCalculator = personalAllowanceReductionCalculator;
  }

  public SalarySlip generateFor(Employee employee) {
    Money monthlyGrossSalary = employee.monthlySalary();

    final Money monthlyTaxFreeAllowance = convertToMonthly(taxFreeAllowance(employee.annualSalary()));
    final Money monthlyTaxableIncome = convertToMonthly(taxableIncome(employee.annualSalary()));
    final Money monthlyTaxPayable = monthlyTaxPayable(employee.annualSalary());

    TaxDetails monthlyTaxDetails = new TaxDetails(
        monthlyTaxFreeAllowance,
        monthlyTaxableIncome,
        monthlyTaxPayable
    );

    Money monthlyNationalInsuranceContributions =
        nationalInsuranceCalculator.monthlyNationalInsuranceContributionsFor(employee.annualSalary());

    return new SalarySlip(
        employee,
        monthlyGrossSalary,
        monthlyNationalInsuranceContributions,
        monthlyTaxDetails
    );
  }

  private Money taxFreeAllowance(Money annualSalary) {
    return PERSONAL_ALLOWANCE.subtract(personalAllowanceReductionCalculator.reductionFor(annualSalary));
  }

  private Money taxableIncome(Money annualSalary) {
    return annualSalary.subtract(taxFreeAllowance(annualSalary));
  }

  private Money monthlyTaxPayable(Money annualSalary) {
    return taxCalculator.monthlyTaxPayable(annualSalary);
  }

  private Money convertToMonthly(Money amount) {return amount.divisionBy(TWELVE_MONTHS);}
}
