package org.neomatrix369.salaryslip.tax;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.TaxDetails;

public class TaxCalculator {
  private static final int TWELVE_MONTHS = 12;

  private TaxBand additionalTaxRateBand;
  private TaxBand higherTaxRateBand;
  private TaxBand basicTaxRateBand;

  private final PersonalAllowanceCalculator personalAllowanceCalculator;

  public TaxCalculator(PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.personalAllowanceCalculator = personalAllowanceCalculator;

    additionalTaxRateBand = new TaxBand(new Money(150_000.00), new Money(MAX_VALUE),  0.45);
    higherTaxRateBand     = new HigherTaxWithPersonalAllowanceReductionRuleBand(
                                        new Money( 43_000.00), new Money(150_000.00), 0.40, new PersonalAllowanceCalculator());
    basicTaxRateBand      = new TaxBand(new Money( 11_000.00), new Money( 43_000.00), 0.20);
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
                    additionalTaxRateBand.calculateTaxPayableFor(annualSalary)
                        .add(higherTaxRateBand.calculateTaxPayableFor(annualSalary))
                        .add(basicTaxRateBand.calculateTaxPayableFor(annualSalary));

    return convertToMonthly(taxPayable);
  }

  private Money convertToMonthly(Money amount) {
    return amount.divisionBy(TWELVE_MONTHS);
  }
}
