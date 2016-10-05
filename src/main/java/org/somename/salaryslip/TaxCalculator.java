package org.somename.salaryslip;

import org.somename.salaryslip.components.Money;
import org.somename.salaryslip.personal_allowance.PersonalAllowanceCalculator;
import org.somename.salaryslip.tax.HigherTaxBandWithReductionRule;
import org.somename.salaryslip.tax.TaxBand;
import org.somename.salaryslip.tax.TaxDetails;

public class TaxCalculator {
  private static final int TWELVE_MONTHS = 12;

  private TaxBand additionalRateTaxBand =
      new TaxBand(new Money(150_000.00), new Money(Double.MAX_VALUE), 0.45);
  private TaxBand higherRateTaxBand;

  private TaxBand basicRateTaxBand = new TaxBand(new Money(11_000.00), new Money(43_000.00), 0.20);

  private PersonalAllowanceCalculator personalAllowanceCalculator;

  public TaxCalculator(PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.personalAllowanceCalculator = personalAllowanceCalculator;

    higherRateTaxBand = new HigherTaxBandWithReductionRule(
        new Money(43_000.00), new Money(150_000.00), 0.40, personalAllowanceCalculator);
  }

  public TaxDetails calculateMonthlyTaxDetailsFor(Money annualSalary) {
    Money monthlyTaxFreeAllowance = monthly(taxFreeAllowance(annualSalary));
    Money monthlyTaxableIncome = monthly(taxableIncome(annualSalary));
    Money monthlyTaxPayable = monthly(taxPayable(annualSalary));

    return new TaxDetails(
        monthlyTaxFreeAllowance,
        monthlyTaxableIncome,
        monthlyTaxPayable
    );
  }

  private Money taxFreeAllowance(Money annualSalary) {
    return personalAllowanceCalculator.calculateTaxFreeAllowance(annualSalary);
  }

  private Money taxableIncome(Money annualSalary) {
    return annualSalary.subtract(taxFreeAllowance(annualSalary));
  }

  private Money taxPayable(Money annualSalary) {
    return basicRateTaxBand.calculateTaxPayableFor(annualSalary)
        .add(higherRateTaxBand.calculateTaxPayableFor(annualSalary))
        .add(additionalRateTaxBand.calculateTaxPayableFor(annualSalary));
  }

  private Money monthly(Money amount) {
    return amount.divisionBy(TWELVE_MONTHS);
  }
}
