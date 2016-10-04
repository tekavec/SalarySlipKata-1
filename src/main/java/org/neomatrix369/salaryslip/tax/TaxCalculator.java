package org.neomatrix369.salaryslip.tax;

import static org.neomatrix369.salaryslip.components.Money.zero;
import static java.lang.Double.MAX_VALUE;

import java.util.ArrayList;
import java.util.List;

import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.personal_allowance.PersonalAllowanceCalculator;

public class TaxCalculator {

  private static final int TWELVE_MONTHS = 12;

  private PersonalAllowanceCalculator personalAllowanceCalculator;

  private List<TaxBand> taxBands = new ArrayList<>();

  public TaxCalculator(PersonalAllowanceCalculator personalAllowanceCalculator) {
    this.personalAllowanceCalculator = personalAllowanceCalculator;

    taxBands.add(new StandardTaxBand(new Money(150_000.00), new Money( MAX_VALUE), 0.45));
    taxBands.add(new HigherTaxWithPersonalAllowanceReductionRuleBand(
        new Money( 43_000.00), new Money(150_000.00), 0.40, this.personalAllowanceCalculator)
    );
    taxBands.add(new StandardTaxBand(new Money( 11_000.00), new Money( 43_000.00), 0.20));
    taxBands.add(new StandardTaxBand(new Money(      0.00), new Money( 11_000.00), 0.00));
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
    return annualSalary.subtract(calculateTaxFreeAllowance(annualSalary));
  }

  private Money calculateMonthlyTaxPayableFor(Money annualSalary) {
    return convertToMonthly(calculateTaxPayableFor(annualSalary));
  }

  private Money calculateTaxPayableFor(Money annualSalary) {
    Money totalTaxPayable = zero();

    for (TaxBand taxBand: taxBands) {
      Money taxPayableForTheBand = taxBand.calculateTaxFrom(annualSalary);
      totalTaxPayable = totalTaxPayable.add(taxPayableForTheBand);
    }

    return totalTaxPayable;
  }

  private Money convertToMonthly(Money amount) {return amount.divisionBy(TWELVE_MONTHS);}
}
