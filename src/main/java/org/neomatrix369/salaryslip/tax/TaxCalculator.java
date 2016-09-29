package org.neomatrix369.salaryslip.tax;

import static java.lang.Double.MAX_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.personal_allowance.PersonalAllowanceCalculator;

public class TaxCalculator {

  private PersonalAllowanceCalculator personalAllowanceCalculator;

  private TaxBand higherTax     = new StandardTaxBand(new Money( 43_000.00), new Money(150_000.00), 0.40);
  private TaxBand additionalTax = new StandardTaxBand(new Money(150_000.00), new Money( MAX_VALUE), 0.45);
  private TaxBand basicTax      = new StandardTaxBand(new Money( 11_000.00), new Money( 43_000.00), 0.20);
  private TaxBand zeroTax       = new StandardTaxBand(new Money(      0.00), new Money( 11_000.00), 0.00);

  private List<TaxBand> taxBands = new ArrayList<>();

  public TaxCalculator(PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.personalAllowanceCalculator = personalAllowanceCalculator;

    populateTaxBands();
  }

  private void populateTaxBands() {
    TaxBand higherTaxWithPersonalAllowanceReductionRule =
        new HigherTaxWithPersonalAllowanceReductionRuleBand(higherTax, personalAllowanceCalculator);

    taxBands.add(additionalTax);
    taxBands.add(higherTaxWithPersonalAllowanceReductionRule);
    taxBands.add(basicTax);
    taxBands.add(zeroTax);
  }

  public TaxDetails calculateTaxDetailsFor(Money annualSalary) {
    return new TaxDetails(
        calculateTaxFreeAllowance(annualSalary),
        calculateTaxableIncomeFor(annualSalary),
        calculateTaxPayableFor(annualSalary)
    );
  }

  private Money calculateTaxFreeAllowance(Money annualSalary) {
    return personalAllowanceCalculator.calculateTaxFreeAllowanceFor(annualSalary);
  }

  private Money calculateTaxableIncomeFor(Money annualSalary) {
    return annualSalary.minus(calculateTaxFreeAllowance(annualSalary));
  }

  private Money calculateTaxPayableFor(Money annualSalary) {
    Money totalTaxPayable = Money.zero();

    for (TaxBand taxBand: taxBands) {
      Money taxPayableForTheBand = taxBand.calculateFrom(annualSalary);
      totalTaxPayable = totalTaxPayable.plus(taxPayableForTheBand);
    }

    return totalTaxPayable;
  }
}
