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

  private static final Band NO_CONTRIBUTIONS_BAND = new NationalInsuranceBand(zero(), new Money(8_060.00), 0.00);
  private static final Band BASIC_CONTRIBUTIONS_BAND = new NationalInsuranceBand(new Money(8_060.00), new Money(43_000.00), 0.12);
  private static final Band HIGHER_CONTRIBUTIONS_BAND = new NationalInsuranceBand(new Money(43_000.00), new Money(MAX_VALUE), 0.02);

  private static final Band ZERO_TAX_BAND = new TaxBand(zero(), new Money(11_000.00), 0.00);
  private static final Band BASIC_TAX_BAND = new TaxBand(new Money(11_000.00), new Money(43_000.00), 0.20);
  private static final Band HIGHER_TAX_BAND = new TaxBand(new Money(43_000.00), new Money(150_000.00), 0.40);
  private static final Band ADDITIONAL_TAX_BAND = new TaxBand(new Money(150_000.00), new Money(MAX_VALUE), 0.40);

  private final Money annualSalary;
  private final Band band;

  private final Money expectedNIContributionsForABand;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the NI Contribution or Tax payable for a band {1} is {2}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { new Money(  5_000.00), NO_CONTRIBUTIONS_BAND,                zero() },
            { new Money(  8_060.00), NO_CONTRIBUTIONS_BAND,                zero() },
            { new Money( 10_000.00), NO_CONTRIBUTIONS_BAND,                zero() },
            { new Money( 10_000.00), BASIC_CONTRIBUTIONS_BAND,  new Money(232.80) },
            { new Money( 40_000.00), BASIC_CONTRIBUTIONS_BAND, new Money(3832.80) },
            { new Money( 43_000.00), NO_CONTRIBUTIONS_BAND,                zero() },
            { new Money( 43_000.00), BASIC_CONTRIBUTIONS_BAND, new Money(4192.80) },
            { new Money( 45_000.00), BASIC_CONTRIBUTIONS_BAND, new Money(4192.80) },
            { new Money( 45_000.00), HIGHER_CONTRIBUTIONS_BAND,  new Money(40.00) },
            { new Money( 50_000.00), NO_CONTRIBUTIONS_BAND,                zero() },
            { new Money( 50_000.00), BASIC_CONTRIBUTIONS_BAND, new Money(4192.80) },
            { new Money( 50_000.00), HIGHER_CONTRIBUTIONS_BAND, new Money(140.00) },

            { new Money( 60_000.00), ZERO_TAX_BAND,                  zero() },
            { new Money( 60_000.00), BASIC_TAX_BAND,     new Money(6400.00) },
            { new Money( 60_000.00), HIGHER_TAX_BAND,    new Money(6800.00) },
            { new Money( 60_000.00), ADDITIONAL_TAX_BAND,            zero() },
            { new Money(100_000.00), ZERO_TAX_BAND,                  zero() },
            { new Money(100_000.00), BASIC_TAX_BAND,     new Money(6400.00) },
            { new Money(100_000.00), HIGHER_TAX_BAND,   new Money(22800.00) },
            { new Money(100_000.00), ADDITIONAL_TAX_BAND,            zero() },

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
    assertThat(actualAnnualNIContributions, is(expectedNIContributionsForABand));
  }
}
