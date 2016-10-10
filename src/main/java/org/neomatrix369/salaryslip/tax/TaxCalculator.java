package org.neomatrix369.salaryslip.tax;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Money;

public class TaxCalculator {
  private static final int TWELVE_MONTHS = 12;

  private final TaxBand additionalRateTaxBand;
  private final TaxBand higherRateTaxBand;
  private final TaxBand basicRateTaxBand;

  public TaxCalculator(PersonalAllowanceReductionCalculator personalAllowanceCalculator) {
    additionalRateTaxBand = new TaxBand(new Money(150_000.00), new Money(MAX_VALUE), 0.45);
    higherRateTaxBand = new HigherTaxBandWithReductionRule(
        new Money(43_000.00), new Money(150_000.00), 0.40, personalAllowanceCalculator);
    basicRateTaxBand = new TaxBand(new Money(11_000.00), new Money(43_000.00), 0.20);
  }

  public Money monthlyTaxPayable(Money annualSalary) {
    final Money annualTaxPayable = taxPayable(annualSalary);
    return convertToMonthly(annualTaxPayable);
  }

  private Money taxPayable(Money annualSalary) {
    return additionalRateTaxBand.calculateTaxPayableFor(annualSalary)
        .add(higherRateTaxBand.calculateTaxPayableFor(annualSalary))
        .add(basicRateTaxBand.calculateTaxPayableFor(annualSalary));
  }

  private Money convertToMonthly(Money amount) {
    return amount.divisionBy(TWELVE_MONTHS);
  }
}
