package org.neomatrix369.salaryslip.tax;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.neomatrix369.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class TaxCalculatorShould {
  private TaxCalculator taxCalculator;

  private Money annualSalary;
  private TaxDetails expectedMonthlyTaxDetails;

  @Parameters(name = "For an annual salary of {0}, the monthly tax details are {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                annualSalaryOf(12_000.00),
                expectedMonthlyTaxDetailsOf(
                      monthlyTaxFreeAllowanceOf(916.67),
                      monthlyTaxableIncomeOf(83.33),
                      monthlyTaxPayableOf(16.67)
                )
            },
            {
                annualSalaryOf(45_000.00),
                expectedMonthlyTaxDetailsOf(
                    monthlyTaxFreeAllowanceOf(916.67),
                    monthlyTaxableIncomeOf(2_833.33),
                    monthlyTaxPayableOf(600.00)
                )
            }
        }
    );
  }

  @Before
  public void setUp() throws Exception {
    taxCalculator = new TaxCalculator();
  }

  public TaxCalculatorShould(Money annualSalary, TaxDetails expectedMonthlyTaxDetails) {
    this.annualSalary = annualSalary;
    this.expectedMonthlyTaxDetails = expectedMonthlyTaxDetails;
  }

  @Test public void
  return_the_monthly_tax_payable_for_a_given_annual_salary() {
    assertThat(taxCalculator.calculateMonthlyTaxDetailsFor(annualSalary), is(expectedMonthlyTaxDetails));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money monthlyTaxFreeAllowanceOf(double amount) {
    return new Money(amount);
  }

  private static Money monthlyTaxableIncomeOf(double amount) {
    return new Money(amount);
  }

  private static Money monthlyTaxPayableOf(double amount) {
    return new Money(amount);
  }

  private static TaxDetails expectedMonthlyTaxDetailsOf(
      Money taxFreeAllowance, Money taxableIncome, Money taxPayable) {
    return new TaxDetails(
        taxFreeAllowance,
        taxableIncome,
        taxPayable
    );
  }
}
