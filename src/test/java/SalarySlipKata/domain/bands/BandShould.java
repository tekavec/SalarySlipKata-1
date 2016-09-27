package SalarySlipKata.domain.bands;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.lang.Double.MAX_VALUE;
import static java.util.Arrays.asList;
import static SalarySlipKata.domain.Money.zero;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import SalarySlipKata.domain.Money;

@RunWith(Parameterized.class)
public class BandShould {

  private static final int TWELVE_MONTHS = 12;

  private final Money annualSalary;
  private StandardBand standardBand;

  private final Money expectedNIContributionsForABand;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the NI Contribution or Tax payable for a given band is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { new Money(  5_000.00), new StandardBand(                zero(),   new Money(  8_060.00), 0.00),            zero() },
            { new Money(  8_060.00), new StandardBand(                zero(),   new Money(  8_060.00), 0.00),            zero() },
            { new Money( 10_000.00), new StandardBand(                zero(),   new Money(  8_060.00), 0.00),            zero() },
            { new Money( 10_000.00), new StandardBand(new Money(   8_060.00),   new Money( 43_000.00), 0.12),  new Money(19.40) },
            { new Money( 40_000.00), new StandardBand(new Money(   8_060.00),   new Money( 43_000.00), 0.12), new Money(319.40) },
            { new Money( 43_000.00), new StandardBand(                zero(),   new Money(  8_060.00), 0.00),            zero() },
            { new Money( 43_000.00), new StandardBand(new Money(   8_060.00),   new Money( 43_000.00), 0.12), new Money(349.40) },
            { new Money( 45_000.00), new StandardBand(new Money(   8_060.00),   new Money( 43_000.00), 0.12), new Money(349.40) },
            { new Money( 45_000.00), new StandardBand(new Money(  43_000.00),   new Money( MAX_VALUE), 0.02),   new Money(3.33) },
            { new Money( 50_000.00), new StandardBand(                zero(),   new Money(  8_060.00), 0.00),            zero() },
            { new Money( 50_000.00), new StandardBand(new Money(   8_060.00),   new Money( 43_000.00), 0.12), new Money(349.40) },
            { new Money( 50_000.00), new StandardBand(new Money(  43_000.00),   new Money( MAX_VALUE), 0.02),  new Money(11.67) },

            { new Money( 60_000.00), new StandardBand(               zero(),    new Money( 11_000.00), 0.00),            zero() },
            { new Money( 60_000.00), new StandardBand(new Money( 11_000.00),    new Money( 43_000.00), 0.20), new Money(533.33) },
            { new Money( 60_000.00), new StandardBand(new Money( 43_000.00),    new Money(150_000.00), 0.40), new Money(566.67) },
            { new Money( 60_000.00), new StandardBand(new Money(150_000.00),    new Money( MAX_VALUE), 0.40),            zero() },
            { new Money(100_000.00), new StandardBand(              zero(),     new Money( 11_000.00), 0.00),            zero() },
            { new Money(100_000.00), new StandardBand(new Money( 11_000.00),    new Money( 43_000.00), 0.20), new Money(533.33) },
            { new Money(100_000.00), new StandardBand(new Money( 43_000.00),    new Money(150_000.00), 0.40), new Money(1900.00)},
            { new Money(100_000.00), new StandardBand(new Money(150_000.00),    new Money( MAX_VALUE), 0.40),            zero() },

        }
    );
  }

  public BandShould(
      final Money annualSalary, final StandardBand standardBand, final Money expectedNIContributionsForABand) {
    this.annualSalary = annualSalary;
    this.standardBand = standardBand;
    this.expectedNIContributionsForABand = expectedNIContributionsForABand;
  }

  @Test
  public void
  return_the_NI_contributions_for_a_given_range_and_rate_for_a_given_annual_salary() {
    final Money actualAnnualNIContributions = standardBand.calculateFrom(annualSalary);
    final Money actualMonthlyNIContributions = actualAnnualNIContributions.divideBy(TWELVE_MONTHS);
    assertThat(actualMonthlyNIContributions, is(expectedNIContributionsForABand));
  }
}
