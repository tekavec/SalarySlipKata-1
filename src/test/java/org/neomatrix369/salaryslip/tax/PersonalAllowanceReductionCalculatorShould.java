package org.neomatrix369.salaryslip.tax;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.neomatrix369.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class PersonalAllowanceReductionCalculatorShould {
  private PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator = new PersonalAllowanceReductionCalculator();

  private Money annualSalary;
  private Money expectedAnnualTaxableReduction;

  public PersonalAllowanceReductionCalculatorShould(Money annualSalary, Money expectedAnnualTaxableReduction) {
    this.annualSalary = annualSalary;
    this.expectedAnnualTaxableReduction = expectedAnnualTaxableReduction;
  }

  @Parameters(name = "For an annual salary of {0}, the personal allowance reduction for the annual salary is {1}")
  public static Collection<Object[]> data() {
    return asList(
      new Object[][] {
          { annualSalaryOf( 50_000.00), expectedAnnualTaxableReductionOf(     0.00) },
          { annualSalaryOf(101_000.00), expectedAnnualTaxableReductionOf(   500.00) },
          { annualSalaryOf(111_000.00), expectedAnnualTaxableReductionOf( 5_500.00) },
          { annualSalaryOf(122_000.00), expectedAnnualTaxableReductionOf(11_000.00) },
          { annualSalaryOf(150_000.00), expectedAnnualTaxableReductionOf(11_000.00) }
      }
    );
  }

  @Test public void
  return_the_expected_annual_taxable_reduction_for_a_given_annual_salary() {
    assertThat(personalAllowanceReductionCalculator.reductionFor(annualSalary),
        is(expectedAnnualTaxableReduction));
  } 

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAnnualTaxableReductionOf(double amount) {
    return new Money(amount);
  }
}
