package org.neomatrix369.salaryslip.tax;

import static org.neomatrix369.salaryslip.tax.TaxDetailsBuilder.aTaxDetails;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.neomatrix369.salaryslip.personal_allowance.PersonalAllowanceCalculator;

import org.neomatrix369.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class TaxCalculatorShould {

  private final Money annualSalary;
  private final TaxDetails expectedMonthlyTaxDetails;

  private TaxCalculator taxCalculator;

  @Before
  public void setUp() throws Exception {
    PersonalAllowanceCalculator personalAllowanceCalculator = new PersonalAllowanceCalculator();
    taxCalculator = new TaxCalculator(personalAllowanceCalculator);
  }

  @Parameterized.Parameters(name = "For an annual salary of {0}, the monthly tax details are {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {    new Money(5_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(11_000.00))
                    .withTaxableIncome(Money.zero())
                    .withTaxPayable(Money.zero())
                    .build()
            },
            {   new Money(11_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(11_000.00))
                    .withTaxableIncome(Money.zero())
                    .withTaxPayable(Money.zero())
                    .build()
            },
            {   new Money(12_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(11_000.00))
                    .withTaxableIncome(new Money(1_000.00))
                    .withTaxPayable(new Money(200.00))
                    .build()
            },
            {   new Money(43_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(11_000.00))
                    .withTaxableIncome(new Money(32_000.00))
                    .withTaxPayable(new Money(6400.00))
                    .build()
            },
            {  new Money(100_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(11000.00))
                    .withTaxableIncome(new Money(89_000.00))
                    .withTaxPayable(new Money(29200.00))
                    .build()
            },
            {  new Money(105_500.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(8250.00))
                    .withTaxableIncome(new Money(97_250.00))
                    .withTaxPayable(new Money(32500.00))
                    .build()
            },
            {  new Money(111_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(5500.00))
                    .withTaxableIncome(new Money(105_500.00))
                    .withTaxPayable(new Money(35800.00))
                    .build()
            },
            {  new Money(122_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(0.00))
                    .withTaxableIncome(new Money(122_000.00))
                    .withTaxPayable(new Money(42400.00))
                    .build()
            },
            {  new Money(150_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(0.00))
                    .withTaxableIncome(new Money(150_000.00))
                    .withTaxPayable(new Money(53600.00))
                    .build()
            },
            {  new Money(160_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(0.00))
                    .withTaxableIncome(new Money(160000.00))
                    .withTaxPayable(new Money(58100.00))
                    .build()
            }
        }
    );
  }

  public TaxCalculatorShould(Money annualSalary, TaxDetails expectedMonthlyTaxDetails) {
    this.annualSalary = annualSalary;
    this.expectedMonthlyTaxDetails = expectedMonthlyTaxDetails;
  }
  
  @Test public void
  return_the_annual_payable_tax_for_a_given_annual_salary() {
    final TaxDetails actualTaxDetails = taxCalculator.calculateTaxDetailsFor(annualSalary);
    assertThat(actualTaxDetails, is(expectedMonthlyTaxDetails));
  }
}
