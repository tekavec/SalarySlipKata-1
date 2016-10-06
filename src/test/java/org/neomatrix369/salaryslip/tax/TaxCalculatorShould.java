package org.neomatrix369.salaryslip.tax;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.neomatrix369.salaryslip.components.TaxDetailsBuilder.aTaxDetails;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.TaxDetails;

@RunWith(Parameterized.class)
public class TaxCalculatorShould {
  private TaxCalculator taxCalculator;

  private Money annualSalary;
  private TaxDetails expectedMonthlyTaxDetails;

  private PersonalAllowanceCalculator personalAllowanceCalculator;

  @Parameters(name = "For an annual salary of {0}, the monthly tax details are {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                annualSalaryOf(12_000.00),
                expectedMonthlyTaxDetailsOf(
                    aTaxDetails()
                        .withTaxFreeAllowance(monthly(916.67))
                        .withTaxableIncome(monthly(83.33))
                        .withTaxPayable(monthly(16.67))
                    .build()
                )
            },
            {
                annualSalaryOf(45_000.00),
                expectedMonthlyTaxDetailsOf(
                    aTaxDetails()
                        .withTaxFreeAllowance(monthly(916.67))
                        .withTaxableIncome(monthly(2_833.33))
                        .withTaxPayable(monthly(600.00))
                    .build()
                )
            },
            {
                annualSalaryOf(160_000.00),
                expectedMonthlyTaxDetailsOf(
                    aTaxDetails()
                        .withTaxFreeAllowance(monthly(0.00))
                        .withTaxableIncome(monthly(13_333.33))
                        .withTaxPayable(monthly(4_841.67))
                        .build()
                )
            }
        }
    );
  }

  @Before
  public void setUp() throws Exception {
    personalAllowanceCalculator = new PersonalAllowanceCalculator();
    taxCalculator = new TaxCalculator(personalAllowanceCalculator);
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

  private static TaxDetails expectedMonthlyTaxDetailsOf(TaxDetails taxDetails) {
    return taxDetails;
  }

  private static Money monthly(double amount) {
    return new Money(amount);
  }
}
