package com.codurance.salaryslip.tax;

import static com.codurance.salaryslip.components.Money.zero;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.lang.Double.MAX_VALUE;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import com.codurance.salaryslip.components.Money;
import com.codurance.salaryslip.personal_allowance.PersonalAllowanceCalculator;

@RunWith(Parameterized.class)
public class StandardTaxBandShould {
  private static final StandardTaxBand ZERO_TAX_BAND       = new StandardTaxBand(zero(),                new Money(11_000.00),  0.00);
  private static final StandardTaxBand BASIC_TAX_BAND      = new StandardTaxBand(new Money(11_000.00),  new Money(43_000.00),  0.20);
  private static final StandardTaxBand HIGHER_TAX_BAND     = new StandardTaxBand(new Money(43_000.00),  new Money(150_000.00), 0.40);
  private static final StandardTaxBand ADDITIONAL_TAX_BAND = new StandardTaxBand(new Money(150_000.00), new Money(MAX_VALUE),  0.40);

  private static final TaxBand
      HIGHER_TAX_WITH_PA_REDUCTION_RULE_BAND = new HigherTaxWithPersonalAllowanceReductionRuleBand(HIGHER_TAX_BAND, new PersonalAllowanceCalculator());

  private final Money annualSalary;
  private final TaxBand taxBand;
  private final Money expectedTaxPayableForTheBand;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the Tax payable is {2} for the band {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 60_000.00), ZERO_TAX_BAND,       expectedTaxPayableOf(    0.00) },
            { annualSalaryOf( 60_000.00), BASIC_TAX_BAND,      expectedTaxPayableOf( 6400.00) },
            { annualSalaryOf( 60_000.00), HIGHER_TAX_BAND,     expectedTaxPayableOf( 6800.00) },
            { annualSalaryOf( 60_000.00), ADDITIONAL_TAX_BAND, expectedTaxPayableOf(    0.00) },
            { annualSalaryOf(100_000.00), ZERO_TAX_BAND,       expectedTaxPayableOf(    0.00) },
            { annualSalaryOf(100_000.00), BASIC_TAX_BAND,      expectedTaxPayableOf( 6400.00) },
            { annualSalaryOf(100_000.00), HIGHER_TAX_BAND,     expectedTaxPayableOf(22800.00) },
            { annualSalaryOf(100_000.00), ADDITIONAL_TAX_BAND, expectedTaxPayableOf(    0.00) },
            { annualSalaryOf(110_000.00), HIGHER_TAX_WITH_PA_REDUCTION_RULE_BAND,
                                                               expectedTaxPayableOf(29200.00) },
            { annualSalaryOf(122_000.00), HIGHER_TAX_WITH_PA_REDUCTION_RULE_BAND,
                                                               expectedTaxPayableOf(36000.00) },
            { annualSalaryOf(150_000.00), HIGHER_TAX_WITH_PA_REDUCTION_RULE_BAND,
                                                               expectedTaxPayableOf(47200.00) },

        }
    );
  }

  private static Money annualSalaryOf(double amount) {return new Money(amount);}

  private static Money expectedTaxPayableOf(double amount) {
    return new Money(amount);
  }

  public StandardTaxBandShould(
      final Money annualSalary,
      final TaxBand taxBand,
      final Money expectedTaxPayableForTheBand) {
    this.annualSalary = annualSalary;
    this.taxBand = taxBand;
    this.expectedTaxPayableForTheBand = expectedTaxPayableForTheBand;
  }

  @Test
  public void
  return_the_Tax_Payable_for_a_given_tax_band_for_a_given_annual_salary() {
    final Money actualAnnualTaxPayable = taxBand.calculateFrom(annualSalary);
    assertThat(actualAnnualTaxPayable, is(expectedTaxPayableForTheBand));
  }
}
