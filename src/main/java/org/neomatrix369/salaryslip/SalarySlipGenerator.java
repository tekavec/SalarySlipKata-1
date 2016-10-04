package org.neomatrix369.salaryslip;

import static org.neomatrix369.salaryslip.components.Money.zero;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Employee;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.SalarySlip;
import org.neomatrix369.salaryslip.national_insurance.NationalInsuranceBand;
import org.neomatrix369.salaryslip.tax.TaxDetails;

public class SalarySlipGenerator {

  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final int TWELVE_MONTHS = 12;

  private NationalInsuranceBand basicContributions = new NationalInsuranceBand(new Money(8_060.00), new Money(43_000.00), 0.12);
  private NationalInsuranceBand higherContributions = new NationalInsuranceBand(new Money(43_000.00), new Money(MAX_VALUE) , 0.02);

  public SalarySlip generateFor(Employee employee) {
    Money monthlyNIContributions = convertToMonthly(calculateContributionsFor(employee.annualSalary()));

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

  private Money calculateContributionsFor(Money annualSalary) {
    return basicContributions.calculateContributionsFor(annualSalary)
        .add(higherContributions.calculateContributionsFor(annualSalary));
  }

  private Money convertToMonthly(Money amount) {return amount.divisionBy(TWELVE_MONTHS);}
}
