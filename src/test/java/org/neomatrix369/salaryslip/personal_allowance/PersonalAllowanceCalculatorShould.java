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
  private Money expectedAdjustmentToAnnualPersonalAllowance;
  private Money expectedAnnualTaxFreeAllowance;

  private PersonalAllowanceCalculator personalAllowanceCalculator;

  @Before
  public void initialise() {
    personalAllowanceCalculator = new PersonalAllowanceCalculator();
  }

  public PersonalAllowanceCalculatorShould(
      Money annualSalary, Money expectedAdjustmentToAnnualPersonalAllowance, Money expectedAnnualTaxFreeAllowance) {
    this.annualSalary = annualSalary;
    this.expectedAdjustmentToAnnualPersonalAllowance = expectedAdjustmentToAnnualPersonalAllowance;
    this.expectedAnnualTaxFreeAllowance = expectedAnnualTaxFreeAllowance;
  }

  @Parameterized.Parameters(name = "For an annual salary of {0}, the adjustment to the annual personal allowance is {1} and annual tax-free allowance is {2}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 90_000.00), expectedAdjustmentToAnnualPersonalAllowanceOf(     0.00), expectedAnnualTaxFreeAllowanceOf(11_000.00) },
            { annualSalaryOf(100_000.00), expectedAdjustmentToAnnualPersonalAllowanceOf(     0.00), expectedAnnualTaxFreeAllowanceOf(11_000.00) },
            { annualSalaryOf(105_500.00), expectedAdjustmentToAnnualPersonalAllowanceOf( 2_750.00), expectedAnnualTaxFreeAllowanceOf( 8_250.00) },
            { annualSalaryOf(111_000.00), expectedAdjustmentToAnnualPersonalAllowanceOf( 5_500.00), expectedAnnualTaxFreeAllowanceOf( 5_500.00) },
            { annualSalaryOf(115_000.00), expectedAdjustmentToAnnualPersonalAllowanceOf( 7_500.00), expectedAnnualTaxFreeAllowanceOf( 3_500.00) },
            { annualSalaryOf(122_000.00), expectedAdjustmentToAnnualPersonalAllowanceOf(11_000.00), expectedAnnualTaxFreeAllowanceOf(     0.00) },
            { annualSalaryOf(130_000.00), expectedAdjustmentToAnnualPersonalAllowanceOf(11_000.00), expectedAnnualTaxFreeAllowanceOf(     0.00) },
            { annualSalaryOf(150_000.00), expectedAdjustmentToAnnualPersonalAllowanceOf(11_000.00), expectedAnnualTaxFreeAllowanceOf(     0.00) }
        }
    );
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAdjustmentToAnnualPersonalAllowanceOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAnnualTaxFreeAllowanceOf(double amount) {
    return new Money(amount);
  }

  @Test public void
  return_the_adjustment_to_the_personal_allowance_for_a_given_annual_salary() {
    Money actualAvailableAnnualPersonalAllowance = personalAllowanceCalculator.calculateAdjustmentForOver100K(annualSalary);
    assertThat(actualAvailableAnnualPersonalAllowance, is(expectedAdjustmentToAnnualPersonalAllowance));
  }

  @Test public void
  return_the_tax_free_allowance_for_a_given_annual_salary() {
    Money actualAnnualTaxFreeAllowance = personalAllowanceCalculator.calculateTaxFreeAllowanceFor(annualSalary);
    assertThat(actualAnnualTaxFreeAllowance, is(expectedAnnualTaxFreeAllowance));
  }
}
