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
  private Money annualSalary;
  private Money expectedMonthlyTaxPayable;

  private TaxCalculator taxCalculator;

  @Before
  public void setUp() throws Exception {
    taxCalculator = new TaxCalculator(new PersonalAllowanceReductionCalculator());
  }

  @Parameters(name="For an annual salary of {0}, the monthly tax payable is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 40_000.00), expectedMonthlyTaxPayableOf(  483.33) },
            { annualSalaryOf( 43_000.00), expectedMonthlyTaxPayableOf(  533.33) },
            { annualSalaryOf( 45_000.00), expectedMonthlyTaxPayableOf(  600.00) },
            { annualSalaryOf(105_500.00), expectedMonthlyTaxPayableOf(2_708.33) },
            { annualSalaryOf(150_000.00), expectedMonthlyTaxPayableOf(4_466.67) },
            { annualSalaryOf(160_000.00), expectedMonthlyTaxPayableOf(4_841.67) }
        }
    );
  }

  public TaxCalculatorShould(Money annualSalary, Money expectedMonthlyTaxPayable) {
    this.annualSalary = annualSalary;
    this.expectedMonthlyTaxPayable = expectedMonthlyTaxPayable;
  }

  @Test
  public void
  return_the_monthly_tax_payable_for_the_band_for_a_given_salary() {
    assertThat(taxCalculator.monthlyTaxPayable(annualSalary), is(expectedMonthlyTaxPayable));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedMonthlyTaxPayableOf(double amount) {
    return new Money(amount);
  }
}
