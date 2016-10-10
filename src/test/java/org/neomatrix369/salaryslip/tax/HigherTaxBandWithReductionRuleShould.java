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
public class HigherTaxBandWithReductionRuleShould {
  private Money annualSalary;
  private Money expectedAnnualHigherTaxPayable;

  private HigherTaxBandWithReductionRule higherTaxBandWithReductionRule;

  @Before
  public void setUp() throws Exception {
    higherTaxBandWithReductionRule = new HigherTaxBandWithReductionRule(
        new Money(43_000.00), new Money(150_000.00), 0.40, new PersonalAllowanceReductionCalculator()
    );
  }

  @Parameters(name="For an annual salary of {0}, the annual higher tax payable is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 45_000.00), expectedAnnualTaxPayableOf(   800.00) },
            { annualSalaryOf(100_000.00), expectedAnnualTaxPayableOf(22_800.00) },
            { annualSalaryOf(105_500.00), expectedAnnualTaxPayableOf(26_100.00) },
            { annualSalaryOf(150_000.00), expectedAnnualTaxPayableOf(47_200.00) }
        }
    );
  }

  public HigherTaxBandWithReductionRuleShould(Money annualSalary, Money expectedAnnualHigherTaxPayable) {
    this.annualSalary = annualSalary;
    this.expectedAnnualHigherTaxPayable = expectedAnnualHigherTaxPayable;
  }

  @Test
  public void
  return_the_annual_tax_payable_for_the_band_for_a_given_salary() {
    assertThat(higherTaxBandWithReductionRule.calculateTaxPayableFor(annualSalary),
        is(expectedAnnualHigherTaxPayable));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAnnualTaxPayableOf(double amount) {
    return new Money(amount);
  }
}
