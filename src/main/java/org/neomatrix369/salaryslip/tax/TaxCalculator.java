package org.neomatrix369.salaryslip.tax;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.TaxDetails;

public class TaxCalculator {
  private static final int TWELVE_MONTHS = 12;
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);

  private final TaxBand additionalRateTaxBand;
  private final TaxBand higherRateTaxBand;
  private final TaxBand basicRateTaxBand;
  private PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator;

  public TaxCalculator(PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator) {
    this.personalAllowanceReductionCalculator = personalAllowanceReductionCalculator;
    additionalRateTaxBand = new TaxBand(new Money(150_000.00), new Money(MAX_VALUE), 0.45);
    higherRateTaxBand = new HigherTaxBandWithReductionRule(
        new Money(43_000.00), new Money(150_000.00), 0.40, personalAllowanceReductionCalculator);
    basicRateTaxBand = new TaxBand(new Money(11_000.00), new Money(43_000.00), 0.20);
  }

  private Money monthlyTaxPayable(Money annualSalary) {
    final Money annualTaxPayable = taxPayable(annualSalary);
    return convertToMonthly(annualTaxPayable);
  }

  private Money taxPayable(Money annualSalary) {
    return additionalRateTaxBand.calculateTaxPayableFor(annualSalary)
        .add(higherRateTaxBand.calculateTaxPayableFor(annualSalary))
        .add(basicRateTaxBand.calculateTaxPayableFor(annualSalary));
  }

  private Money monthlyTaxFreeAllowance(Money annualSalary) {
    return convertToMonthly(taxFreeAllowance(annualSalary));
  }

  private Money taxFreeAllowance(Money annualSalary) {
    return PERSONAL_ALLOWANCE.subtract(personalAllowanceReductionCalculator.reductionFor(annualSalary));
  }

  private Money monthlyTaxableIncome(Money annualSalary) {
    return convertToMonthly(taxableIncome(annualSalary));
  }

  private Money taxableIncome(Money annualSalary) {
    return annualSalary.subtract(taxFreeAllowance(annualSalary));
  }

  private Money convertToMonthly(Money amount) {
    return amount.divisionBy(TWELVE_MONTHS);
  }

  public TaxDetails monthlyTaxDetails(Money annualSalary) {
    final Money monthlyTaxFreeAllowance = monthlyTaxFreeAllowance(annualSalary);
    final Money monthlyTaxableIncome = monthlyTaxableIncome(annualSalary);
    final Money monthlyTaxPayable = monthlyTaxPayable(annualSalary);

    return new TaxDetails(
          monthlyTaxFreeAllowance,
          monthlyTaxableIncome,
          monthlyTaxPayable
      );
  }
}
