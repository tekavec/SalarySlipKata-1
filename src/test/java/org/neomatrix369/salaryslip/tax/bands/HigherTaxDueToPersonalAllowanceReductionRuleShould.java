package org.neomatrix369.salaryslip.tax.bands;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.neomatrix369.salaryslip.tax.bands.TaxBand.*;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.tax.PersonalAllowanceReductionCalculator;
import org.neomatrix369.salaryslip.tax.HigherTaxDueToPersonalAllowanceReductionRule;

@RunWith(Parameterized.class)
public class HigherTaxDueToPersonalAllowanceReductionRuleShould {
  private HigherTaxDueToPersonalAllowanceReductionRule higherTaxDueToPersonalAllowanceReductionRule;

  private Money annualSalary;
  private Money expectedHigherTaxPayableDueToRule;

  @Parameters(name = "For an annual salary of {0}, the annual tax payable due to rule is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 60_000.00), expectedAnnualTaxPayableDueToRuleOf(   0.00) },
            { annualSalaryOf(100_000.00), expectedAnnualTaxPayableDueToRuleOf(   0.00) },
            { annualSalaryOf(101_000.00), expectedAnnualTaxPayableDueToRuleOf( 200.00) },
            { annualSalaryOf(105_500.00), expectedAnnualTaxPayableDueToRuleOf(1100.00) },
            { annualSalaryOf(111_000.00), expectedAnnualTaxPayableDueToRuleOf(2200.00) },
            { annualSalaryOf(116_500.00), expectedAnnualTaxPayableDueToRuleOf(3300.00) },
            { annualSalaryOf(122_000.00), expectedAnnualTaxPayableDueToRuleOf(4400.00) },
            { annualSalaryOf(130_000.00), expectedAnnualTaxPayableDueToRuleOf(4400.00) }
        }
    );
  }

  public HigherTaxDueToPersonalAllowanceReductionRuleShould(Money annualSalary, Money expectedHigherTaxPayableDueToRule) {
    this.annualSalary = annualSalary;
    this.expectedHigherTaxPayableDueToRule = expectedHigherTaxPayableDueToRule;
  }

  @Before
  public void setUp() throws Exception {
    higherTaxDueToPersonalAllowanceReductionRule =
        new HigherTaxDueToPersonalAllowanceReductionRule(HIGHER_RATE_TAX_BAND.rate(), new PersonalAllowanceReductionCalculator());
  }

  @Test public void
  return_the_annual_higher_tax_payable_for_a_given_annual_salary_above_100K() {
    assertThat(higherTaxDueToPersonalAllowanceReductionRule.calculateTaxPayableFor(annualSalary), is(expectedHigherTaxPayableDueToRule));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAnnualTaxPayableDueToRuleOf(double amount) {
    return new Money(amount);
  }
}
