package org.neomatrix369.salaryslip.personal_allowance;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.neomatrix369.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class PersonalAllowanceCalculatorShould {

  private Money annualSalary;
  private Money expectedAdjustmentToPersonalAllowance;
  private Money expectedTaxFreeAllowance;

  private PersonalAllowanceCalculator personalAllowanceCalculator;

  @Before
  public void initialise() {
    personalAllowanceCalculator = new PersonalAllowanceCalculator();
  }

  public PersonalAllowanceCalculatorShould(
      Money annualSalary, Money expectedAdjustmentToPersonalAllowance, Money expectedTaxFreeAllowance) {
    this.annualSalary = annualSalary;
    this.expectedAdjustmentToPersonalAllowance = expectedAdjustmentToPersonalAllowance;
    this.expectedTaxFreeAllowance = expectedTaxFreeAllowance;
  }

  @Parameterized.Parameters(name = "For an annual salary of {0}, the adjustment to the personal allowance is {1} and tax-free allowance is {2}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 90_000.00), expectedAdjustmentToPersonalAllowanceOf(     0.00), expectedTaxFreeAllowanceOf(11_000.00) },
            { annualSalaryOf(100_000.00), expectedAdjustmentToPersonalAllowanceOf(     0.00), expectedTaxFreeAllowanceOf(11_000.00) },
            { annualSalaryOf(105_500.00), expectedAdjustmentToPersonalAllowanceOf( 2_750.00), expectedTaxFreeAllowanceOf( 8_250.00) },
            { annualSalaryOf(111_000.00), expectedAdjustmentToPersonalAllowanceOf( 5_500.00), expectedTaxFreeAllowanceOf( 5_500.00) },
            { annualSalaryOf(115_000.00), expectedAdjustmentToPersonalAllowanceOf( 7_500.00), expectedTaxFreeAllowanceOf( 3_500.00) },
            { annualSalaryOf(122_000.00), expectedAdjustmentToPersonalAllowanceOf(11_000.00), expectedTaxFreeAllowanceOf(     0.00) },
            { annualSalaryOf(130_000.00), expectedAdjustmentToPersonalAllowanceOf(11_000.00), expectedTaxFreeAllowanceOf(     0.00) },
            { annualSalaryOf(150_000.00), expectedAdjustmentToPersonalAllowanceOf(11_000.00), expectedTaxFreeAllowanceOf(     0.00) }
        }
    );
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAdjustmentToPersonalAllowanceOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedTaxFreeAllowanceOf(double amount) {
    return new Money(amount);
  }

  @Test public void
  return_the_adjustment_to_the_personal_allowance_for_a_given_annual_salary() {
    Money actualAvailableAnnualPersonalAllowance = personalAllowanceCalculator.calculateAdjustmentForOver100K(annualSalary);
    assertThat(actualAvailableAnnualPersonalAllowance, is(expectedAdjustmentToPersonalAllowance));
  }

  @Test public void
  return_the_tax_free_allowance_for_a_given_annual_salary() {
    Money actualAnnualTaxFreeAllowance = personalAllowanceCalculator.calculateTaxFreeAllowanceFor(annualSalary);
    assertThat(actualAnnualTaxFreeAllowance, is(expectedTaxFreeAllowance));
  }
}
