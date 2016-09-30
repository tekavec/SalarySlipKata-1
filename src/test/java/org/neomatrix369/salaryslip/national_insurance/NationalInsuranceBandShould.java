package org.neomatrix369.salaryslip.national_insurance;

import static org.neomatrix369.salaryslip.components.Money.zero;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.lang.Double.MAX_VALUE;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.neomatrix369.salaryslip.components.Money;

@RunWith(Parameterized.class)
public class NationalInsuranceBandShould {

  private static final NationalInsuranceBand NO_CONTRIBUTIONS_BAND     = new NationalInsuranceBand(zero(),               new Money(8_060.00),  0.00);
  private static final NationalInsuranceBand BASIC_CONTRIBUTIONS_BAND  = new NationalInsuranceBand(new Money(8_060.00),  new Money(43_000.00), 0.12);
  private static final NationalInsuranceBand HIGHER_CONTRIBUTIONS_BAND = new NationalInsuranceBand(new Money(43_000.00), new Money(MAX_VALUE), 0.02);

  private final Money annualSalary;
  private final NationalInsuranceBand nationalInsuranceBand;
  private final Money expectedAnnualNIContributionsForTheBand;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the NI Contribution is {2} for the band {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 5_000.00), NO_CONTRIBUTIONS_BAND,     expectedNIContributionsOf(    0.00) },
            { annualSalaryOf( 8_060.00), NO_CONTRIBUTIONS_BAND,     expectedNIContributionsOf(    0.00) },
            { annualSalaryOf(10_000.00), NO_CONTRIBUTIONS_BAND,     expectedNIContributionsOf(    0.00) },
            { annualSalaryOf(10_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedNIContributionsOf(  232.80) },
            { annualSalaryOf(40_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedNIContributionsOf(3_832.80) },
            { annualSalaryOf(43_000.00), NO_CONTRIBUTIONS_BAND,     expectedNIContributionsOf(    0.00) },
            { annualSalaryOf(43_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedNIContributionsOf(4_192.80) },
            { annualSalaryOf(45_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedNIContributionsOf(4_192.80) },
            { annualSalaryOf(45_000.00), HIGHER_CONTRIBUTIONS_BAND, expectedNIContributionsOf(   40.00) },
            { annualSalaryOf(50_000.00), NO_CONTRIBUTIONS_BAND,     expectedNIContributionsOf(    0.00) },
            { annualSalaryOf(50_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedNIContributionsOf(4_192.80) },
            { annualSalaryOf(50_000.00), HIGHER_CONTRIBUTIONS_BAND, expectedNIContributionsOf(  140.00) },
        }
    );
  }

  private static Money annualSalaryOf(double amount) {return new Money(amount);}

  private static Money expectedNIContributionsOf(double amount) {
    return new Money(amount);
  }

  public NationalInsuranceBandShould(
      final Money annualSalary,
      final NationalInsuranceBand nationalInsuranceBand,
      final Money expectedAnnualNIContributionsForTheBand) {
    this.annualSalary = annualSalary;
    this.nationalInsuranceBand = nationalInsuranceBand;
    this.expectedAnnualNIContributionsForTheBand = expectedAnnualNIContributionsForTheBand;
  }

  @Test
  public void
  return_the_NI_contributions_for_a_given_NI_contribution_band_for_a_given_annual_salary() {
    final Money actualAnnualNIContributions = nationalInsuranceBand.calculateContributionsFrom(annualSalary);
    assertThat(actualAnnualNIContributions, is(expectedAnnualNIContributionsForTheBand));
  }
}
