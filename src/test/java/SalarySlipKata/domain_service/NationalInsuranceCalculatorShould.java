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
  private final Money monthlyContributions;

  private NationalInsuranceCalculator nationalInsuranceCalculator;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the monthly contribution is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {  new Money(5_000.00),   new Money(0.00) },
            {  new Money(8_060.00),   new Money(0.00) },
            { new Money(10_000.00),  new Money(19.40) },
            { new Money(43_000.00), new Money(349.40) },
            { new Money(45_000.00), new Money(352.73) },
            { new Money(50_000.00), new Money(361.07) },
        }
    );
  }

  public NationalInsuranceCalculatorShould(Money annualSalary, Money monthlyContributions) {
    this.annualSalary = annualSalary;
    this.monthlyContributions = monthlyContributions;
  }

  @Before
  public void setUp() throws Exception {
    nationalInsuranceCalculator = new NationalInsuranceCalculator();
  }

  @Test public void
  return_a_monthly_contribution_for_a_given_annual_salary() {
    assertThat(nationalInsuranceCalculator.calculateMonthlyContributionsFor(annualSalary), is(monthlyContributions));
  } 
}
