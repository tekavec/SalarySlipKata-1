package SalarySlipKata.domain_service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.lang.Double.MAX_VALUE;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import SalarySlipKata.domain.Band;
import SalarySlipKata.domain.Money;

@RunWith(Parameterized.class)
public class BandShould {

  private static final int TWELVE_MONTHS = 12;

  private final Money annualSalary;
  private Band band;

  private final Money expectedNIContributionsForABand;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the NI Contributions for a given band is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { new Money(5_000.00),  new Band(new Money(0.00), new Money(8_060.00), 0.00), new Money(0.00) },
            { new Money(8_060.00),  new Band(new Money(0.00), new Money(8_060.00), 0.00), new Money(0.00) },
            { new Money(10_000.00), new Band(new Money(0.00), new Money(8_060.00), 0.00), new Money(0.00) },
            { new Money(10_000.00), new Band(new Money(8_060.00), new Money(43_000.00), 0.12), new Money(19.40)  },
            { new Money(40_000.00), new Band(new Money(8_060.00), new Money(43_000.00), 0.12), new Money(319.40) },
            { new Money(43_000.00), new Band(new Money(0.00), new Money(8_060.00), 0.00), new Money(0.00) },
            { new Money(43_000.00), new Band(new Money(8_060.00), new Money(43_000.00), 0.12), new Money(349.40) },
            { new Money(45_000.00), new Band(new Money(0.00), new Money(8_060.00), 0.00), new Money(0.00) },
            { new Money(45_000.00), new Band(new Money(8_060.00), new Money(43_000.00), 0.12), new Money(349.40) },
            { new Money(45_000.00), new Band(new Money(43_000.00), new Money(MAX_VALUE), 0.02), new Money(3.33) },
            { new Money(50_000.00), new Band(new Money(0.00), new Money(8_060.00), 0.00), new Money(0.00) },
            { new Money(50_000.00), new Band(new Money(8_060.00), new Money(43_000.00), 0.12), new Money(349.40) },
            { new Money(50_000.00), new Band(new Money(43_000.00), new Money(MAX_VALUE), 0.02), new Money(11.67) },
        }
    );
  }

  public BandShould(
      final Money annualSalary, final Band band, final Money expectedNIContributionsForABand) {
    this.annualSalary = annualSalary;
    this.band = band;
    this.expectedNIContributionsForABand = expectedNIContributionsForABand;
  }

  @Test
  public void
  return_the_NI_contributions_for_a_given_range_and_rate_for_a_given_annual_salary() {
    final Money actualAnnualNIContributions = band.calculateFrom(annualSalary);
    final Money actualMonthlyNIContributions = actualAnnualNIContributions.divideBy(TWELVE_MONTHS);
    assertThat(actualMonthlyNIContributions, is(expectedNIContributionsForABand));
  }
}
