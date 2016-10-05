package org.somename.salaryslip.personal_allowance;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.somename.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class PersonalAllowanceCalculatorShould {
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  private Money annualSalary;
  private Money expectedAnnualTaxFreeAllowance;
  private Money expectedAdjustmentForExcessOver100;

  public PersonalAllowanceCalculatorShould(
      Money annualSalary,
      Money expectedAnnualTaxFreeAllowance,
      Money expectedAdjustmentForExcessOver100) {
    this.annualSalary = annualSalary;
    this.expectedAnnualTaxFreeAllowance = expectedAnnualTaxFreeAllowance;
    this.expectedAdjustmentForExcessOver100 = expectedAdjustmentForExcessOver100;
  }

  @Before
  public void setUp() throws Exception {
    personalAllowanceCalculator = new PersonalAllowanceCalculator();
  }

  @Parameters(name = "For an annual salary of {0}, the annual tax-free allowance is {1} and the annual adjustment due to the reduction rule is {2}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 45_000.00), expectedAnnualTaxFreeAllowanceOf(11_000.00), expectedAdjustmentForExcessOver100KOf(     0.00) },
            { annualSalaryOf(101_000.00), expectedAnnualTaxFreeAllowanceOf(10_500.00), expectedAdjustmentForExcessOver100KOf(   500.00) },
            { annualSalaryOf(122_000.00), expectedAnnualTaxFreeAllowanceOf(     0.00), expectedAdjustmentForExcessOver100KOf(11_000.00) },
            { annualSalaryOf(150_000.00), expectedAnnualTaxFreeAllowanceOf(     0.00), expectedAdjustmentForExcessOver100KOf(11_000.00) }
        }
    );
  }

  @Test public void
  return_the_annual_tax_free_allowance_for_the_annual_salary() {
    assertThat(personalAllowanceCalculator.calculateTaxFreeAllowance(annualSalary),
        is(expectedAnnualTaxFreeAllowance));
  }
  
  @Test public void
  return_the_annual_adjustment_for_excess_over_100K_for_the_annual_salary() {
    assertThat(personalAllowanceCalculator.calculateAdjustmentForExcessOver100K(annualSalary),
        is(expectedAdjustmentForExcessOver100));
  } 

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAnnualTaxFreeAllowanceOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAdjustmentForExcessOver100KOf(double amount) {
    return new Money(amount);
  }
}
