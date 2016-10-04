package org.neomatrix369.salaryslip.national_insurance;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;
import static java.lang.Double.MAX_VALUE;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.neomatrix369.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class NationalInsuranceBandShould {
  private Money annualSalary;
  private Money expectedNationalInsuranceContributions;

  private static final NationalInsuranceBand BASIC_CONTRIBUTIONS_BAND = new NationalInsuranceBand(new Money(8_060.00), new Money(43_000.00), 0.12);
  private static final NationalInsuranceBand HIGHER_CONTRIBUTIONS_BAND = new NationalInsuranceBand(new Money(43_000.00), new Money(MAX_VALUE), 0.02);

  private NationalInsuranceBand nationalInsuranceBand;

  @Parameters(name="For an annual salary of {0}, the annual NI contributions are {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 9_060.00), BASIC_CONTRIBUTIONS_BAND,  expectedAnnualNIContributionsOf( 120.00) },
            { annualSalaryOf(45_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedAnnualNIContributionsOf(4192.80) },
            { annualSalaryOf(45_000.00), HIGHER_CONTRIBUTIONS_BAND, expectedAnnualNIContributionsOf(  40.00) }
        }
    );
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAnnualNIContributionsOf(double amount) {
    return new Money(amount);
  }

  public NationalInsuranceBandShould(
      Money annualSalary,
      NationalInsuranceBand nationalInsuranceBand,
      Money expectedNationalInsuranceContributions) {
    this.annualSalary = annualSalary;
    this.nationalInsuranceBand = nationalInsuranceBand;
    this.expectedNationalInsuranceContributions = expectedNationalInsuranceContributions;
  }

  @Test public void
  return_the_contributions_for_this_band_for_a_given_annual_salary() {
    assertThat(nationalInsuranceBand.calculateContributionsFor(annualSalary),
        is(expectedNationalInsuranceContributions));
  } 
}
