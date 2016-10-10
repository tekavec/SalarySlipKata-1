package org.neomatrix369.salaryslip.tax;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.lang.Double.MAX_VALUE;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.neomatrix369.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class TaxBandShould {

  private static final TaxBand ADDITIONAL_TAX_RATE_BAND = new TaxBand(new Money(150_000.00), new Money(MAX_VALUE),  0.45);
  private static final TaxBand HIGHER_TAX_RATE_BAND     = new TaxBand(new Money(43_000),     new Money(150_000.00), 0.40);
  private static final TaxBand BASIC_TAX_RATE_BAND      = new TaxBand(new Money(11_000.00),  new Money(43_000),     0.20);

  private Money annualSalary;
  private TaxBand taxBand;
  private Money expectedAnnualTaxPayable;

  @Parameters(name="For an annual salary of {0}, the annual tax payable is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 40_000.00), BASIC_TAX_RATE_BAND,      expectedAnnualTaxPayableOf(5_800.00) },
            { annualSalaryOf( 43_000.00), BASIC_TAX_RATE_BAND,      expectedAnnualTaxPayableOf(6_400.00) },
            { annualSalaryOf( 45_000.00), BASIC_TAX_RATE_BAND,      expectedAnnualTaxPayableOf(6_400.00) },
            { annualSalaryOf( 45_000.00), HIGHER_TAX_RATE_BAND,     expectedAnnualTaxPayableOf(  800.00) },
            { annualSalaryOf(160_000.00), ADDITIONAL_TAX_RATE_BAND, expectedAnnualTaxPayableOf(4_500.00) },
        }
    );
  }

  public TaxBandShould(Money annualSalary, TaxBand taxBand, Money expectedAnnualTaxPayable) {
    this.annualSalary = annualSalary;
    this.taxBand = taxBand;
    this.expectedAnnualTaxPayable = expectedAnnualTaxPayable;
  }

  @Test public void
  return_the_annual_tax_payable_for_the_band_for_a_given_salary() {
    assertThat(taxBand.calculateTaxPayableFor(annualSalary), is(expectedAnnualTaxPayable));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAnnualTaxPayableOf(double amount) {
    return new Money(amount);
  }
}
