package org.somename.salaryslip;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.somename.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class NationalInsuranceCalculatorShould {
  private Money annualSalary;
  private Money monthlyContributionsBand;

  private NationalInsuranceCalculator nationalInsuranceCalculator;

  @Before
  public void setUp() throws Exception {
    nationalInsuranceCalculator = new NationalInsuranceCalculator();
  }

  @Parameterized.Parameters(name = "For an annual salary of {0}, the monthly contributions is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 8_060.00),  expectedContributionsOf( 0.00) },
            { annualSalaryOf( 9_060.00),  expectedContributionsOf(10.00) },
            { annualSalaryOf(43_000.00), expectedContributionsOf(349.40) },
            { annualSalaryOf(45_000.00), expectedContributionsOf(352.73) }
        }
    );
  }

  public NationalInsuranceCalculatorShould(Money annualSalary, Money monthlyContributionsBand) {
    this.annualSalary = annualSalary;
    this.monthlyContributionsBand = monthlyContributionsBand;
  }

  @Test
  public void
  return_the_contributions_for_the_basic_contributions_band_for_an_annual_salary() {
    assertThat(nationalInsuranceCalculator.calculateMonthlyContributionsFor(annualSalary), is(monthlyContributionsBand));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedContributionsOf(double amount) {
    return new Money(amount);
  }
}
