package org.neomatrix369.salaryslip;

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
import org.neomatrix369.salaryslip.tax.HigherTaxWithPersonalAllowanceReductionRuleBand;
import org.neomatrix369.salaryslip.tax.PersonalAllowanceCalculator;

@RunWith(Parameterized.class)
public class HigherTaxWithPersonalAllowanceReductionRuleBandShould {
  private HigherTaxWithPersonalAllowanceReductionRuleBand higherTaxBand;

  private Money annualSalary;
  private Money expectedAnnualHigherTaxPayable;

  @Parameters(name = "For an annual salary of {0}, the annual tax payable is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 60_000.00), expectedAnnualHigherTaxPayableOf( 6_800.00) },
            { annualSalaryOf(100_000.00), expectedAnnualHigherTaxPayableOf(22_800.00) },
            { annualSalaryOf(101_000.00), expectedAnnualHigherTaxPayableOf(23_400.00) }
        }
    );
  }

  public HigherTaxWithPersonalAllowanceReductionRuleBandShould(Money annualSalary, Money expectedAnnualHigherTaxPayable) {
    this.annualSalary = annualSalary;
    this.expectedAnnualHigherTaxPayable = expectedAnnualHigherTaxPayable;
  }

  @Before
  public void setUp() throws Exception {
    higherTaxBand =
        new HigherTaxWithPersonalAllowanceReductionRuleBand(
            new Money(43_000.00), new Money(150_000.00), 0.40, new PersonalAllowanceCalculator());
  }

  @Test public void
  return_the_annual_higher_tax_payable_for_a_given_annual_salary_above_100K() {
    assertThat(higherTaxBand.calculateTaxPayableFor(annualSalary), is(expectedAnnualHigherTaxPayable));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAnnualHigherTaxPayableOf(double amount) {
    return new Money(amount);
  }
}
