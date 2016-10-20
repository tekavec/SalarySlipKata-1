package org.neomatrix369.salaryslip.tax;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.TaxDetails;
import org.neomatrix369.salaryslip.tax.bands.TaxBand;

public class TaxCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final int TWELVE_MONTHS = 12;

  private TaxBand additionalTaxRateBand;
  private TaxBand higherTaxRateBand;
  private TaxBand basicTaxRateBand;

  private PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator;
  private HigherTaxDueToPersonalAllowanceReductionRule higherTaxDueToPersonalAllowanceReductionRule;

  public TaxCalculator(PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator) {
    this.personalAllowanceReductionCalculator = personalAllowanceReductionCalculator;

    additionalTaxRateBand = new TaxBand(new Money(150_000.00), new Money(MAX_VALUE),  0.45);
    higherTaxRateBand     = new TaxBand(new Money( 43_000.00), new Money(150_000.00), 0.40);
    basicTaxRateBand      = new TaxBand(new Money( 11_000.00), new Money( 43_000.00), 0.20);

    higherTaxDueToPersonalAllowanceReductionRule =
                            new HigherTaxDueToPersonalAllowanceReductionRule(0.40, personalAllowanceReductionCalculator);
  }

  public TaxDetails calculateMonthlyTaxDetailsFor(Money annualSalary) {
    return new TaxDetails(
        monthlyTaxFreeAllowance(annualSalary),
        monthlyTaxableIncome(annualSalary),
        monthlyTaxPayable(annualSalary)
    );
  }

  private Money monthlyTaxFreeAllowance(Money annualSalary) {
    return taxFreeAllowance(annualSalary).divideBy(TWELVE_MONTHS);
  }

  private Money taxFreeAllowance(Money annualSalary) {
    Money reduction = personalAllowanceReductionCalculator.reductionFor(annualSalary);

    return PERSONAL_ALLOWANCE.subtract(reduction);
  }

  private Money monthlyTaxableIncome(Money annualSalary) {
    return annualSalary.subtract(taxFreeAllowance(annualSalary)).divideBy(TWELVE_MONTHS);
  }

  private Money monthlyTaxPayable(Money annualSalary) {
    final Money additionalTax = additionalTaxRateBand.calculateTaxPayableFor(annualSalary);
    final Money higherTax = higherTaxRateBand.calculateTaxPayableFor(annualSalary);
    final Money higherTaxOnReducedPersonalAllowance =
        higherTaxDueToPersonalAllowanceReductionRule.calculateTaxPayableFor(annualSalary);
    final Money basicTax = basicTaxRateBand.calculateTaxPayableFor(annualSalary);

    return basicTax.add(higherTax).add(higherTaxOnReducedPersonalAllowance).add(additionalTax).divideBy(TWELVE_MONTHS);
  }
}
