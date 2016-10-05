package org.somename.salaryslip.national_insurance;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static java.lang.Double.MAX_VALUE;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.somename.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class NationalInsuranceBandShould {

  private static final NationalInsuranceBand HIGHER_CONTRIBUTIONS_BAND = new NationalInsuranceBand(new Money(43_000.00), new Money(MAX_VALUE), 0.02);
  private static final NationalInsuranceBand BASIC_CONTRIBUTIONS_BAND  = new NationalInsuranceBand(new Money(8_060.00), new Money(43_000.00), 0.12);

  private Money annualSalary;
  private NationalInsuranceBand nationalInsuranceBand;
  private Money expectedAnnualContributions;

  public NationalInsuranceBandShould(
      Money annualSalary, NationalInsuranceBand nationalInsuranceBand, Money expectedAnnualContributions) {
    this.annualSalary = annualSalary;
    this.nationalInsuranceBand = nationalInsuranceBand;
    this.expectedAnnualContributions = expectedAnnualContributions;
  }

  @Parameters(name = "For an annual salary of {0}, the annual contributions is {2} for the band {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf(9_060.00),  BASIC_CONTRIBUTIONS_BAND,  expectedContributionsOf(  120.00) },
            { annualSalaryOf(45_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedContributionsOf(4_192.80) },
            { annualSalaryOf(45_000.00), HIGHER_CONTRIBUTIONS_BAND, expectedContributionsOf(   40.00) }
        }
    );
  }

  @Test public void
  return_the_contributions_for_the_basic_contributions_band_for_an_annual_salary() {
    assertThat(nationalInsuranceBand.calculateContributionsFor(annualSalary), is(expectedAnnualContributions));
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedContributionsOf(double amount) {
    return new Money(amount);
  }
}
