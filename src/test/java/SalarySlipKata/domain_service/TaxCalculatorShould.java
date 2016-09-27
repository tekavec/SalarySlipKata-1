package SalarySlipKata.domain_service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;
import static SalarySlipKata.domain.TaxDetailsBuilder.aTaxDetails;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.TaxDetails;

@RunWith(Parameterized.class)
public class TaxCalculatorShould {

  private static final int TWELVE_MONTHS = 12;

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
                    .withTaxFreeAllowance(new Money(916.67))
                    .withTaxableIncome(new Money(0.00))
                    .withTaxPayable(new Money(0.00))
                    .build()
            },
            {   new Money(11_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(916.67))
                    .withTaxableIncome(new Money(0.00))
                    .withTaxPayable(new Money(0.00))
                    .build()
            },
            {   new Money(12_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(916.67))
                    .withTaxableIncome(new Money(83.33))
                    .withTaxPayable(new Money(16.67))
                    .build()
            },
            {   new Money(43_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(916.67))
                    .withTaxableIncome(new Money(2_666.67))
                    .withTaxPayable(new Money(533.33))
                    .build()
            },
            {  new Money(100_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(916.67))
                    .withTaxableIncome(new Money(7_416.67))
                    .withTaxPayable(new Money(2_433.33))
                    .build()
            },
            {  new Money(111_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(458.33))
                    .withTaxableIncome(new Money(8_791.67))
                    .withTaxPayable(new Money(2_983.33))
                    .build()
            },
            {  new Money(122_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(0.00))
                    .withTaxableIncome(new Money(10_166.67))
                    .withTaxPayable(new Money(3_533.33))
                    .build()
            },
            {  new Money(150_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(0.00))
                    .withTaxableIncome(new Money(12_500.00))
                    .withTaxPayable(new Money(4_466.67))
                    .build()
            },
            {  new Money(160_000.00),
                aTaxDetails()
                    .withTaxFreeAllowance(new Money(0.00))
                    .withTaxableIncome(new Money(13_333.33))
                    .withTaxPayable(new Money(4_841.67))
                    .build()
            },
        }
    );
  }

  public TaxCalculatorShould(Money annualSalary, TaxDetails expectedMonthlyTaxDetails) {
    this.annualSalary = annualSalary;
    this.expectedMonthlyTaxDetails = expectedMonthlyTaxDetails;
  }
  
  @Test public void
  return_a_monthly_payable_tax_for_a_given_annual_salary() {
    final TaxDetails actualTaxDetails = taxCalculator.calculateTaxDetailsFor(annualSalary);
    assertThat(monthly(actualTaxDetails), is(expectedMonthlyTaxDetails));
  }

  private TaxDetails monthly(TaxDetails actualTaxDetails) {
    return aTaxDetails()
              .withTaxFreeAllowance(monthly(actualTaxDetails.taxFreeAllowance()))
              .withTaxableIncome(monthly(actualTaxDetails.taxableIncome()))
              .withTaxPayable(monthly(actualTaxDetails.taxPayable()))
              .build();
  }

  private Money monthly(Money amount) {
    return amount.divideBy(TWELVE_MONTHS);
  }
}
