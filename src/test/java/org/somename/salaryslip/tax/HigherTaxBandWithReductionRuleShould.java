package org.somename.salaryslip.tax;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.somename.salaryslip.components.Money;
import org.somename.salaryslip.personal_allowance.PersonalAllowanceCalculator;

@RunWith(Parameterized.class)
public class HigherTaxBandWithReductionRuleShould {

  private Money annualSalary;
  private Money expectedAnnualTaxPayable;

  private HigherTaxBandWithReductionRule higherTaxBand = new HigherTaxBandWithReductionRule(new Money(43_000.00), new Money(150_000.00), 0.40, new PersonalAllowanceCalculator());

  public HigherTaxBandWithReductionRuleShould(Money annualSalary, Money expectedAnnualTaxPayable) {
    this.annualSalary = annualSalary;
    this.expectedAnnualTaxPayable = expectedAnnualTaxPayable;
  }

  @Parameters(name = "For an annual salary of {0}, the expected tax payable is {1}")
  public static Collection<Object[]> data() {
    return asList(
      new Object[][] {
          { annualSalaryOf(45_000.00),  expectedTaxPayableOf(   800.00) },
          { annualSalaryOf(101_000.00), expectedTaxPayableOf(23_400.00) }
      }
    );
  }

  @Test public void
  return_the_annual_tax_payable_at_the_higher_tax_rate_for_the_given_annual_salary() {
      assertThat(higherTaxBand.calculateTaxPayableFor(annualSalary), is(expectedAnnualTaxPayable));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedTaxPayableOf(double amount) {
    return new Money(amount);
  }

}
