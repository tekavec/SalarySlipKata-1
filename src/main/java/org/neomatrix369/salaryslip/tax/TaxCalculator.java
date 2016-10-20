package org.neomatrix369.salaryslip.tax;

import static org.neomatrix369.salaryslip.tax.bands.TaxBand.ADDITIONAL_RATE_TAX_BAND;
import static org.neomatrix369.salaryslip.tax.bands.TaxBand.BASIC_RATE_TAX_BAND;
import static org.neomatrix369.salaryslip.tax.bands.TaxBand.HIGHER_RATE_TAX_BAND;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.TaxDetails;

public class TaxCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final int TWELVE_MONTHS = 12;

  private PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator;
  private HigherTaxDueToPersonalAllowanceReductionRule higherTaxDueToPersonalAllowanceReductionRule;

  public TaxCalculator(PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator) {
    this.personalAllowanceReductionCalculator = personalAllowanceReductionCalculator;

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
    final Money additionalTax = ADDITIONAL_RATE_TAX_BAND.calculateTaxPayableFor(annualSalary);
    final Money higherTax = HIGHER_RATE_TAX_BAND.calculateTaxPayableFor(annualSalary);
    final Money higherTaxOnReducedPersonalAllowance =
        higherTaxDueToPersonalAllowanceReductionRule.calculateTaxPayableFor(annualSalary);
    final Money basicTax = BASIC_RATE_TAX_BAND.calculateTaxPayableFor(annualSalary);

    return basicTax.add(higherTax).add(higherTaxOnReducedPersonalAllowance).add(additionalTax).divideBy(TWELVE_MONTHS);
  }
}
