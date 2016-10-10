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
public class PersonalAllowanceReductionCalculatorShould {

  private Money annualSalary;
  private Money expectedAnnualReduction;

  private PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator;

  @Parameters(name="For an annual salary of {0}, the annual reductions is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 90_000.00), expectedReductionOf(    0.00) },
            { annualSalaryOf(105_500.00), expectedReductionOf(2_750.00) },
            { annualSalaryOf(122_000.00), expectedReductionOf(11_000.00) },
            { annualSalaryOf(150_000.00), expectedReductionOf(11_000.00) }
        }
    );
  }

  public PersonalAllowanceReductionCalculatorShould(Money annualSalary, Money expectedAnnualReduction) {
    this.annualSalary = annualSalary;
    this.expectedAnnualReduction = expectedAnnualReduction;
  }

  @Before
  public void setUp() throws Exception {
    personalAllowanceReductionCalculator = new PersonalAllowanceReductionCalculator();
  }

  @Test public void
  return_the_annual_reduction_to_apply_on_the_personal_allowance_for_a_given_annual_salary() {
    assertThat(personalAllowanceReductionCalculator.reductionFor(annualSalary), is(expectedAnnualReduction));
  }

  private static Money expectedReductionOf(double amount) {
    return new Money(amount);
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }
}
