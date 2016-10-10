package org.neomatrix369.salaryslip.national_insurance;

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
public class NationalContributionsBandShould {
  private static final NationalInsuranceContributionsBand HIGHER_CONTRIBUTIONS_BAND =
      new NationalInsuranceContributionsBand(new Money(43_000.00), new Money(150_000.00), 0.02);
  private static final NationalInsuranceContributionsBand BASIC_CONTRIBUTIONS_BAND =
      new NationalInsuranceContributionsBand(new Money(8_060.00), new Money(43_000.00), 0.12);

  private Money annualSalary;
  private Money expectedAnnualContributions;
  private NationalInsuranceContributionsBand niContributionsBand;

  @Parameters(name = "For an annual salary of {0}, the annual NI contributions is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 9_060.00), BASIC_CONTRIBUTIONS_BAND,  expectedContributionsOf(  120.00) },
            { annualSalaryOf(45_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedContributionsOf(4_192.80) },
            { annualSalaryOf(45_000.00), HIGHER_CONTRIBUTIONS_BAND, expectedContributionsOf(   40.00) }
        }
    );
  }

  public NationalContributionsBandShould(Money annualSalary, NationalInsuranceContributionsBand niContributionsBand, Money expectedAnnualContributions) {
    this.annualSalary = annualSalary;
    this.niContributionsBand = niContributionsBand;
    this.expectedAnnualContributions = expectedAnnualContributions;
  }

  @Test public void
  return_the_annual_contributions_for_a_given_band_for_a_given_annual_salary() {
    assertThat(niContributionsBand.calculateContributionsFor(annualSalary), is(expectedAnnualContributions));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedContributionsOf(double amount) {
    return new Money(amount);
  }
}
