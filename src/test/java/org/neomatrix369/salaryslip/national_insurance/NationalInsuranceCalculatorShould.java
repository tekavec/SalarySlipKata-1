package org.neomatrix369.salaryslip.national_insurance;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.*;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.national_insurance.NationalInsuranceCalculator;

@RunWith(Parameterized.class)
public class NationalInsuranceCalculatorShould {

  private Money annualSalary;
  private Money expectedAnnualContributions;

  private NationalInsuranceCalculator nationalInsuranceCalculator = new NationalInsuranceCalculator();

  @Parameters(name = "For an annual salary of {0}, the annual NI contributions is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 9_060.00), expectedContributionsOf( 10.00) },
            { annualSalaryOf(43_000.00), expectedContributionsOf(349.40) },
            { annualSalaryOf(45_000.00), expectedContributionsOf(352.73) }
        }
    );
  }

  public NationalInsuranceCalculatorShould(Money annualSalary, Money expectedAnnualContributions) {
    this.annualSalary = annualSalary;
    this.expectedAnnualContributions = expectedAnnualContributions;
  }

  @Test
  public void
  return_the_annual_contributions_for_a_given_band_for_a_given_annual_salary() {
    assertThat(
        nationalInsuranceCalculator.monthlyNationalInsuranceContributionsFor(annualSalary),
        is(expectedAnnualContributions)
    );
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedContributionsOf(double amount) {
    return new Money(amount);
  }
}
