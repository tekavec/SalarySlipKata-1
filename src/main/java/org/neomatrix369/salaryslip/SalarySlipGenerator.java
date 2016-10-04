package org.neomatrix369.salaryslip;

import static org.neomatrix369.salaryslip.components.Money.zero;

import org.neomatrix369.salaryslip.components.Employee;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.SalarySlip;
import org.neomatrix369.salaryslip.national_insurance.NationalInsuranceCalculator;
import org.neomatrix369.salaryslip.tax.TaxDetails;

public class SalarySlipGenerator {

  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final int TWELVE_MONTHS = 12;

  private final NationalInsuranceCalculator nationalInsuranceCalculator;

  public SalarySlipGenerator(NationalInsuranceCalculator nationalInsuranceCalculator) {
    this.nationalInsuranceCalculator = nationalInsuranceCalculator;
  }

  public SalarySlip generateFor(Employee employee) {
    Money monthlyNIContributions =
        nationalInsuranceCalculator.calculateMonthlyContributionsFor(employee.annualSalary());

    Money monthlyTaxFreeAllowance = convertToMonthly(PERSONAL_ALLOWANCE);
    TaxDetails monthlyTaxDetails = new TaxDetails(
        monthlyTaxFreeAllowance,
        zero(),
        zero()
    );

    return new SalarySlip(
        employee,
        employee.monthlySalary(),
        monthlyNIContributions,
        monthlyTaxDetails
    );
  }

  private Money convertToMonthly(Money amount) {return amount.divisionBy(TWELVE_MONTHS);}
}
