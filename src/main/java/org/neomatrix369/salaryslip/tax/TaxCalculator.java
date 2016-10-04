package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public class TaxCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final int TWELVE_MONTHS = 12;

  private final TaxBand basicTaxRateBand =
      new TaxBand(new Money(11_000.00), new Money(43_000.00), 0.20);
  private final TaxBand higherTaxRateBand =
      new TaxBand(new Money(43_000.00), new Money(150_000.00), 0.40);

  public TaxDetails calculateMonthlyTaxDetailsFor(Money annualSalary) {
    return new TaxDetails(
        monthlyTaxFreeAllowance(),
        monthlyTaxableIncome(annualSalary),
        monthlyTaxPayable(annualSalary)
    );
  }

  private Money monthlyTaxPayable(Money annualSalary) {
    final Money taxPayable =
        basicTaxRateBand.calculateTaxPayable(annualSalary)
        .add(
            higherTaxRateBand.calculateTaxPayable(annualSalary)
        );
    return convertToMonthly(taxPayable);
  }

  private Money monthlyTaxFreeAllowance() {return convertToMonthly(PERSONAL_ALLOWANCE);}

  private Money monthlyTaxableIncome(Money annualSalary) {
    final Money taxableIncome = annualSalary.subtract(PERSONAL_ALLOWANCE);
    return convertToMonthly(taxableIncome);
  }

  private Money convertToMonthly(Money amount) {return amount.divisionBy(TWELVE_MONTHS);}
}
