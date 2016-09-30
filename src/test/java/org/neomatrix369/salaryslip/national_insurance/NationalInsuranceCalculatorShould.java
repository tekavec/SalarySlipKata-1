package org.neomatrix369.salaryslip.national_insurance;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.neomatrix369.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class NationalInsuranceCalculatorShould {

  private final Money annualSalary;
  private final Money expectedMonthlyContributions;

  private NationalInsuranceCalculator nationalInsuranceCalculator;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the monthly national insurance contributions is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf(  5_000.00), expectedMonthlyNIContributionsOf(  0.00) },
            { annualSalaryOf(  8_060.00), expectedMonthlyNIContributionsOf(  0.00) },
            { annualSalaryOf( 10_000.00), expectedMonthlyNIContributionsOf( 19.40) },
            { annualSalaryOf( 40_000.00), expectedMonthlyNIContributionsOf(319.40) },
            { annualSalaryOf( 43_000.00), expectedMonthlyNIContributionsOf(349.40) },
            { annualSalaryOf( 45_000.00), expectedMonthlyNIContributionsOf(352.73) },
            { annualSalaryOf( 50_000.00), expectedMonthlyNIContributionsOf(361.07) },
            { annualSalaryOf(105_500.00), expectedMonthlyNIContributionsOf(453.57) },
        }
    );
  }

  private static Money annualSalaryOf(double amount) {return new Money(amount);}

  private static Money expectedMonthlyNIContributionsOf(double amount) {
    return new Money(amount);
  }

  public NationalInsuranceCalculatorShould(Money annualSalary, Money expectedMonthlyContributions) {
    this.annualSalary = annualSalary;
    this.expectedMonthlyContributions = expectedMonthlyContributions;
  }

  @Before
  public void setUp() throws Exception {
    nationalInsuranceCalculator = new NationalInsuranceCalculator();
  }

  @Test public void
  return_the_monthly_NI_contributions_for_a_given_annual_salary() {
    final Money actualMonthlyContributions =
        nationalInsuranceCalculator.calculateMonthlyContributionsFor(annualSalary);
    assertThat(actualMonthlyContributions, is(expectedMonthlyContributions));
  } 
}
