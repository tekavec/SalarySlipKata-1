package SalarySlipKata.domain_service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

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
    taxCalculator = new TaxCalculator();
  }

  @Parameterized.Parameters(name = "For an annual salary of {0}, the monthly tax details are {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {    new Money(5_000.00),
                taxDetailsWith(freeTaxAllowance(916.67),      taxableIncome(0.00),     taxPayable(0.00)) },
            {   new Money(11_000.00),
                taxDetailsWith(freeTaxAllowance(916.67),      taxableIncome(0.00),     taxPayable(0.00)) },
            {   new Money(12_000.00),
                taxDetailsWith(freeTaxAllowance(916.67),     taxableIncome(83.33),    taxPayable(16.67)) },
            {   new Money(43_000.00),
                taxDetailsWith(freeTaxAllowance(916.67),  taxableIncome(2_666.67),   taxPayable(533.33)) },
            {  new Money(100_000.00),
                taxDetailsWith(freeTaxAllowance(916.67),  taxableIncome(7_416.67), taxPayable(2_433.33)) },
            {  new Money(111_000.00),
                taxDetailsWith(freeTaxAllowance(458.33),  taxableIncome(8_791.67), taxPayable(2_983.33)) },
            {  new Money(122_000.00),
                taxDetailsWith(freeTaxAllowance(  0.00), taxableIncome(10_166.67), taxPayable(3_533.33)) },
            {  new Money(150_000.00),
                taxDetailsWith(freeTaxAllowance(  0.00), taxableIncome(12_500.00), taxPayable(4_466.67)) },
            {  new Money(160_000.00),
                taxDetailsWith(freeTaxAllowance(  0.00), taxableIncome(13_333.33), taxPayable(4_841.67)) },

        }
    );
  }

  private static Money freeTaxAllowance(double amount) {return new Money(amount);}

  private static Money taxableIncome(double amount) {return new Money(amount);}

  private static Money taxPayable(double amount) {return new Money(amount);}

  private static TaxDetails taxDetailsWith(Money freeTaxAllowance, Money taxableIncome, Money taxPayable) {
    return new TaxDetails(freeTaxAllowance, taxableIncome, taxPayable);
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
    return new TaxDetails(
        monthly(actualTaxDetails.taxFreeAllowance()),
        monthly(actualTaxDetails.taxableIncome()),
        monthly(actualTaxDetails.taxPayable())
    );
  }

  private Money monthly(Money amount) {
    return amount.divideBy(TWELVE_MONTHS);
  }
}
