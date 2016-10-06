package org.neomatrix369.salaryslip.tax;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.TaxDetails;

public class TaxCalculator {
  private static final int TWELVE_MONTHS = 12;

  private final TaxBand additionalTaxRateBand =
      new TaxBand(new Money(150_000.00), new Money(MAX_VALUE), 0.45);
  private TaxBand higherTaxRateBand;
  private final TaxBand basicTaxRateBand =
      new TaxBand(new Money(11_000.00), new Money(43_000.00), 0.20);

  private final PersonalAllowanceCalculator personalAllowanceCalculator;

  public TaxCalculator(PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.personalAllowanceCalculator = personalAllowanceCalculator;
    higherTaxRateBand =
        new HigherTaxWithPersonalAllowanceReductionRuleBand(
            new Money(43_000.00), new Money(150_000.00), 0.40, new PersonalAllowanceCalculator());
  }

  public TaxDetails calculateMonthlyTaxDetailsFor(Money annualSalary) {
    return new TaxDetails(
        monthlyTaxFreeAllowance(annualSalary),
        monthlyTaxableIncome(annualSalary),
        monthlyTaxPayable(annualSalary)
    );
  }

  private Money monthlyTaxFreeAllowance(Money annualSalary) {
    return convertToMonthly(personalAllowanceCalculator.calculateTaxFreeAllowance(annualSalary));
  }

  private Money monthlyTaxableIncome(Money annualSalary) {
    final Money taxableIncome = annualSalary.subtract(
        personalAllowanceCalculator.calculateTaxFreeAllowance(annualSalary)
    );
    return convertToMonthly(taxableIncome);
  }

  private Money monthlyTaxPayable(Money annualSalary) {
    final Money taxPayable =
        basicTaxRateBand.calculateTaxPayableFor(annualSalary)
            .add(
                higherTaxRateBand.calculateTaxPayableFor(annualSalary)
            )
            .add(
                additionalTaxRateBand.calculateTaxPayableFor(annualSalary)
            );
    return convertToMonthly(taxPayable);
  }

  private Money convertToMonthly(Money amount) {
    return amount.divisionBy(TWELVE_MONTHS);
  }
}
