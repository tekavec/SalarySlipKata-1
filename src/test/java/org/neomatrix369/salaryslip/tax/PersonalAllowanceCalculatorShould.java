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
public class PersonalAllowanceCalculatorShould {
  private PersonalAllowanceCalculator personalAllowanceCalculator;

  private Money annualSalary;
  private Money expectedAnnualTaxFreeAllowance;
  private Money expectedAdjustmentDueToReductionRule;

  public PersonalAllowanceCalculatorShould(Money annualSalary, Money expectedAnnualTaxFreeAllowance, Money expectedAdjustmentDueToReductionRule) {
    this.annualSalary = annualSalary;
    this.expectedAnnualTaxFreeAllowance = expectedAnnualTaxFreeAllowance;
    this.expectedAdjustmentDueToReductionRule = expectedAdjustmentDueToReductionRule;
  }

  @Before
  public void setUp() throws Exception {
    personalAllowanceCalculator = new PersonalAllowanceCalculator();
  }

  @Parameters(name = "For an annual salary of {0}, then the tax-free allowance is {1}")
  public static Collection<Object[]> data() {
    return asList(
      new Object[][] {
          { annualSalaryOf( 50_000.00), expectedTaxFreeAllowanceOf(11_000.00), expectedAdjustmentDueToReductionRuleOf(  0.00) },
          { annualSalaryOf(101_000.00), expectedTaxFreeAllowanceOf(10_500.00), expectedAdjustmentDueToReductionRuleOf(500.00) }
      }
    );
  }

  @Test public void
  return_the_tax_free_allowance_for_a_given_annual_salary() {
    assertThat(personalAllowanceCalculator.calculateTaxFreeAllowance(annualSalary),
        is(expectedAnnualTaxFreeAllowance));
  }
  
  @Test public void
  return_the_expected_adjustment_due_to_reduction_rule() {
    assertThat(personalAllowanceCalculator.calculateAdjustmentDueToReductionRule(annualSalary),
        is(expectedAdjustmentDueToReductionRule));
  } 

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedTaxFreeAllowanceOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAdjustmentDueToReductionRuleOf(double amount) {
    return new Money(amount);
  }
}
