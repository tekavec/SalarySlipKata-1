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

  private static final int TWELVE_MONTHS = 12;

  private final Money annualSalary;
  private final Money expectedMonthlyContributions;

  private NationalInsuranceCalculator nationalInsuranceCalculator;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the monthly national insurance contribution is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {  new Money(5_000.00),   new Money(0.00) },
            {  new Money(8_060.00),   new Money(0.00) },
            { new Money(10_000.00),  new Money(19.40) },
            { new Money(40_000.00), new Money(319.40) },
            { new Money(43_000.00), new Money(349.40) },
            { new Money(45_000.00), new Money(352.73) },
            { new Money(50_000.00), new Money(361.07) },
        }
    );
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
  return_the_monthly_contribution_for_a_given_annual_salary() {
    final Money actualAnnualContributions = nationalInsuranceCalculator.calculateContributionsFor(annualSalary);
    final Money actualMonthlyContributions = actualAnnualContributions.divideBy(TWELVE_MONTHS);
    assertThat(actualMonthlyContributions, is(expectedMonthlyContributions)
    );
  } 
}
