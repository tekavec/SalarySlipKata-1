package com.codurance.salaryslip.tax;

import static com.codurance.salaryslip.components.Money.zero;
import static java.lang.Double.MAX_VALUE;

import java.util.ArrayList;
import java.util.List;

import com.codurance.salaryslip.components.Money;
import com.codurance.salaryslip.tax_bands.Band;
import com.codurance.salaryslip.personal_allowance.PersonalAllowanceCalculator;

public class TaxCalculator {

  private PersonalAllowanceCalculator personalAllowanceCalculator;

  private TaxBand higherTax     = new TaxBand(new Money( 43_000.00), new Money(150_000.00), 0.40);
  private TaxBand additionalTax = new TaxBand(new Money(150_000.00), new Money( MAX_VALUE), 0.45);
  private TaxBand basicTax      = new TaxBand(new Money( 11_000.00), new Money( 43_000.00), 0.20);
  private TaxBand zeroTax       = new TaxBand(new Money(      0.00), new Money( 11_000.00), 0.00);

  private List<Band> taxBands = new ArrayList<>();

  public TaxCalculator(PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.personalAllowanceCalculator = personalAllowanceCalculator;

    populateTaxBands();
  }

  private void populateTaxBands() {
    Band higherTaxWithPersonalAllowanceReductionRule =
        new HigherTaxWithPersonalAllowanceReductionRuleBand(higherTax, personalAllowanceCalculator);

    taxBands.add(additionalTax);
    taxBands.add(higherTaxWithPersonalAllowanceReductionRule);
    taxBands.add(basicTax);
    taxBands.add(zeroTax);
  }

  public TaxDetails calculateTaxDetailsFor(Money annualSalary) {
    return new TaxDetails(
        calculateTaxFreeAllowanceFor(annualSalary),
        calculateTaxableIncomeFor(annualSalary),
        calculateTaxPayableFor(annualSalary)
    );
  }

  private Money calculateTaxFreeAllowanceFor(Money annualSalary) {
    return personalAllowanceCalculator.calculateTaxFreeAllowance(annualSalary);
  }

  private Money calculateTaxableIncomeFor(Money annualSalary) {
    return annualSalary.minus(calculateTaxFreeAllowanceFor(annualSalary));
  }

  private Money calculateTaxPayableFor(Money annualSalary) {
    Money totalTaxPayable = zero();

    for (Band taxBand : taxBands) {
      Money taxPayableForTheBand = taxBand.calculateFrom(annualSalary);
      totalTaxPayable = totalTaxPayable.plus(taxPayableForTheBand);
    }

    return totalTaxPayable;
  }
}
