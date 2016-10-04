package org.neomatrix369.salaryslip;

import org.neomatrix369.salaryslip.components.Employee;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.SalarySlip;
import org.neomatrix369.salaryslip.national_insurance.NationalInsuranceCalculator;
import org.neomatrix369.salaryslip.tax.TaxBand;
import org.neomatrix369.salaryslip.tax.TaxDetails;

public class SalarySlipGenerator {

  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final int TWELVE_MONTHS = 12;

  private final NationalInsuranceCalculator nationalInsuranceCalculator;

  private final TaxBand basicTaxRateBand = new TaxBand(new Money(11_000.00), new Money(43_000.00), 0.20);
  private final TaxBand higherTaxRateBand = new TaxBand(new Money(43_000.00), new Money(150_000.00), 0.40);

  public SalarySlipGenerator(NationalInsuranceCalculator nationalInsuranceCalculator) {
    this.nationalInsuranceCalculator = nationalInsuranceCalculator;
  }

  public SalarySlip generateFor(Employee employee) {
    Money monthlyNIContributions =
        nationalInsuranceCalculator.calculateMonthlyContributionsFor(employee.annualSalary());

    TaxDetails monthlyTaxDetails = calculateMonthlyTaxDetails(employee.annualSalary());

    return new SalarySlip(
        employee,
        employee.monthlySalary(),
        monthlyNIContributions,
        monthlyTaxDetails
    );
  }

  private TaxDetails calculateMonthlyTaxDetails(Money annualSalary) {
    return new TaxDetails(
                monthlyTaxFreeAllowance(),
                monthlyTaxableIncome(annualSalary),
                monthlyTaxPayable(annualSalary)
      );
  }

  private Money monthlyTaxFreeAllowance() {return convertToMonthly(PERSONAL_ALLOWANCE);}

  private Money monthlyTaxableIncome(Money annualSalary) {
    final Money taxableIncome = annualSalary.subtract(PERSONAL_ALLOWANCE);
    return convertToMonthly(taxableIncome);
  }

  private Money monthlyTaxPayable(Money annualSalary) {
    final Money taxPayable = basicTaxRateBand.calculateTaxPayable(annualSalary)
        .add(higherTaxRateBand.calculateTaxPayable(annualSalary));
    return convertToMonthly(taxPayable);
  }

  private Money convertToMonthly(Money amount) {return amount.divisionBy(TWELVE_MONTHS);}
}
