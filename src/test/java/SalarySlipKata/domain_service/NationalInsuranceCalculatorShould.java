package SalarySlipKata.domain_service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import SalarySlipKata.domain.Money;

@RunWith(Parameterized.class)
public class NationalInsuranceCalculatorShould {

  private final Money annualSalary;
  private final Money expectedMonthlyContributions;

  private NationalInsuranceCalculator nationalInsuranceCalculator;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the monthly national insurance contribution is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 5_000.00), contributionsOf(   0.00) },
            { annualSalaryOf( 8_060.00), contributionsOf(   0.00) },
            { annualSalaryOf(10_000.00), contributionsOf( 232.80) },
            { annualSalaryOf(40_000.00), contributionsOf(3832.80) },
            { annualSalaryOf(43_000.00), contributionsOf(4192.80) },
            { annualSalaryOf(45_000.00), contributionsOf(4232.80) },
            { annualSalaryOf(50_000.00), contributionsOf(4332.80) },
        }
    );
  }

  private static Money contributionsOf(double amount) {
    return new Money(amount);
  }

  private static Money annualSalaryOf(double denomination) {return new Money(denomination);}

  public NationalInsuranceCalculatorShould(Money annualSalary, Money expectedMonthlyContributions) {
    this.annualSalary = annualSalary;
    this.expectedMonthlyContributions = expectedMonthlyContributions;
  }

  @Before
  public void setUp() throws Exception {
    nationalInsuranceCalculator = new NationalInsuranceCalculator();
  }

  @Test public void
  return_the_annual_contribution_for_a_given_annual_salary() {
    final Money actualAnnualContributions =
        nationalInsuranceCalculator.calculateContributionsFor(annualSalary);
    assertThat(actualAnnualContributions, is(expectedMonthlyContributions));
  } 
}
