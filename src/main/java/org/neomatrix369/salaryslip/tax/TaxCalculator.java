package org.neomatrix369.salaryslip.tax;

import static java.lang.Double.MAX_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.personal_allowance.PersonalAllowanceCalculator;

public class TaxCalculator {

  private static final int TWELVE_MONTHS = 12;

  private PersonalAllowanceCalculator personalAllowanceCalculator;

  private StandardTaxBand higherTax     = new StandardTaxBand(new Money( 43_000.00), new Money(150_000.00), 0.40);
  private StandardTaxBand additionalTax = new StandardTaxBand(new Money(150_000.00), new Money( MAX_VALUE), 0.45);
  private StandardTaxBand basicTax      = new StandardTaxBand(new Money( 11_000.00), new Money( 43_000.00), 0.20);
  private StandardTaxBand zeroTax       = new StandardTaxBand(new Money(      0.00), new Money( 11_000.00), 0.00);

  private List<TaxBand> taxBands = new ArrayList<>();

  public TaxCalculator(PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.personalAllowanceCalculator = personalAllowanceCalculator;

    populateTaxBands();
  }

  private void populateTaxBands() {
    HigherTaxWithPersonalAllowanceReductionRuleBand higherTaxWithPersonalAllowanceReductionRule =
        new HigherTaxWithPersonalAllowanceReductionRuleBand(higherTax, personalAllowanceCalculator);

    taxBands.add(additionalTax);
    taxBands.add(higherTaxWithPersonalAllowanceReductionRule);
    taxBands.add(basicTax);
    taxBands.add(zeroTax);
  }

  public TaxDetails calculateMonthlyTaxDetailsFor(Money annualSalary) {
    return new TaxDetails(
        calculateMonthlyTaxFreeAllowance(annualSalary),
        calculateMonthlyTaxableIncomeFor(annualSalary),
        calculateMonthlyTaxPayableFor(annualSalary)
    );
  }

  private Money calculateMonthlyTaxFreeAllowance(Money annualSalary) {
    return convertToMonthly(calculateTaxFreeAllowance(annualSalary));
  }

  private Money calculateTaxFreeAllowance(Money annualSalary) {
    return personalAllowanceCalculator.calculateTaxFreeAllowanceFor(annualSalary);
  }

  private Money calculateMonthlyTaxableIncomeFor(Money annualSalary) {
    return convertToMonthly(calculateTaxableIncomeFor(annualSalary));
  }

  private Money calculateTaxableIncomeFor(Money annualSalary) {
    return annualSalary.minus(calculateTaxFreeAllowance(annualSalary));
  }

  private Money calculateMonthlyTaxPayableFor(Money annualSalary) {
    return convertToMonthly(calculateTaxPayableFor(annualSalary));
  }

  private Money calculateTaxPayableFor(Money annualSalary) {
    Money totalTaxPayable = Money.zero();

    for (TaxBand taxBand: taxBands) {
      Money taxPayableForTheBand = taxBand.calculateTaxFrom(annualSalary);
      totalTaxPayable = totalTaxPayable.plus(taxPayableForTheBand);
    }

    return totalTaxPayable;
  }

  private Money convertToMonthly(Money amount) {return amount.divideBy(TWELVE_MONTHS);}
}
