package org.neomatrix369.salaryslip.tax;

import static java.lang.Double.MAX_VALUE;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.TaxDetails;
import org.neomatrix369.salaryslip.tax.bands.HigherTaxWithPersonalAllowanceReductionRuleBand;
import org.neomatrix369.salaryslip.tax.bands.TaxBand;

public class TaxCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
  private static final int TWELVE_MONTHS = 12;

  private TaxBand additionalTaxRateBand;
  private TaxBand higherTaxRateBand;
  private TaxBand basicTaxRateBand;

  private PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator;

  public TaxCalculator(PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator) {
    this.personalAllowanceReductionCalculator = personalAllowanceReductionCalculator;

    additionalTaxRateBand = new TaxBand(new Money(150_000.00), new Money(MAX_VALUE),  0.45);
    higherTaxRateBand     = new HigherTaxWithPersonalAllowanceReductionRuleBand(
                                        new Money( 43_000.00), new Money(150_000.00), 0.40, personalAllowanceReductionCalculator);
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
    return convertToMonthly(taxFreeAllowance(annualSalary));
  }

  private Money taxFreeAllowance(Money annualSalary) {
    Money reduction = personalAllowanceReductionCalculator.reductionFor(annualSalary);

    return PERSONAL_ALLOWANCE.subtract(reduction);
  }

  private Money monthlyTaxableIncome(Money annualSalary) {
    final Money taxableIncome = annualSalary.subtract(taxFreeAllowance(annualSalary));
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
