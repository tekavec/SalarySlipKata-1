package org.somename.salaryslip;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.somename.salaryslip.components.Money;
import org.somename.salaryslip.personal_allowance.PersonalAllowanceCalculator;
import org.somename.salaryslip.tax.TaxDetails;

@RunWith(Parameterized.class)
public class TaxCalculatorShould {
  private PersonalAllowanceCalculator personalAllowanceCalculator;
  private TaxCalculator taxCalculator;

  private Money annualSalary;
  private TaxDetails expectedMonthlyTaxPayable;

  @Before
  public void setUp() throws Exception {
    personalAllowanceCalculator = new PersonalAllowanceCalculator();
    taxCalculator = new TaxCalculator(personalAllowanceCalculator);
  }

  @Parameters(name="For an annual salary of {0}, the monthly tax payable is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 40_000.00), expectedMonthlyTaxDetailsOf(916.67, 2416.67, 483.33) },
            { annualSalaryOf( 45_000.00), expectedMonthlyTaxDetailsOf(916.67, 2833.33, 600.00) },
            { annualSalaryOf(101_000.00), expectedMonthlyTaxDetailsOf(875.00, 7541.67, 2483.33)},
            { annualSalaryOf(150_000.00), expectedMonthlyTaxDetailsOf(0.00, 12500.00, 4466.67) },
            { annualSalaryOf(160_000.00), expectedMonthlyTaxDetailsOf(0.00, 13333.33, 4841.67) }
        }
    );
  }

  public TaxCalculatorShould(Money annualSalary, TaxDetails expectedMonthlyTaxPayable) {
    this.annualSalary = annualSalary;
    this.expectedMonthlyTaxPayable = expectedMonthlyTaxPayable;
  }

  @Test
  public void
  return_the_annual_tax_payable_for_the_given_tax_band_for_the_given_annual_salary() {
    assertThat(taxCalculator.calculateMonthlyTaxDetailsFor(annualSalary), is(expectedMonthlyTaxPayable));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static TaxDetails expectedMonthlyTaxDetailsOf(double taxFreeAllowance, double taxableIncome, double taxPayable) {
    return new TaxDetails(new Money(taxFreeAllowance), new Money(taxableIncome), new Money(taxPayable));
  }
}
