package org.neomatrix369.salaryslip.national_insurance;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.*;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.neomatrix369.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class NationalInsuranceCalculatorShould {

  private Money annualSalary;
  private Money expectedMonthlyNIContributions;

  private NationalInsuranceCalculator nationalInsuranceCalculator;

  @Parameters(name = "For an annual salary of {0}, the monthly NI contributions are {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 9_060.00), expectedNIMonthlyContributions( 10.00) },
            { annualSalaryOf(45_000.00), expectedNIMonthlyContributions(352.73) }
        }
    );
  }

  public NationalInsuranceCalculatorShould(Money annualSalary, Money expectedMonthlyNIContributions) {
    this.annualSalary = annualSalary;
    this.expectedMonthlyNIContributions = expectedMonthlyNIContributions;
  }

  @Before
  public void setUp() throws Exception {
    nationalInsuranceCalculator = new NationalInsuranceCalculator();
  }

  @Test public void
  return_the_monthly_ni_contributions_for_a_given_annual_salary_for_an_employee() {
    Money actualMonthlyNIContributions = nationalInsuranceCalculator.calculateMonthlyContributionsFor(annualSalary);

    assertThat(actualMonthlyNIContributions, is(expectedMonthlyNIContributions));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedNIMonthlyContributions(double amount) {
    return new Money(amount);
  }
}
