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
public class NationalInsuranceBandShould {

  private static final Band NO_CONTRIBUTIONS_BAND     = new NationalInsuranceBand(zero(),               new Money(8_060.00),  0.00);
  private static final Band BASIC_CONTRIBUTIONS_BAND  = new NationalInsuranceBand(new Money(8_060.00),  new Money(43_000.00), 0.12);
  private static final Band HIGHER_CONTRIBUTIONS_BAND = new NationalInsuranceBand(new Money(43_000.00), new Money(MAX_VALUE), 0.02);

  private final Money annualSalary;
  private final NationalInsuranceBand nationalInsuranceBand;

  private final Money expectedNIContributionsForABand;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the NI Contribution is {2} for the band {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 5_000.00), NO_CONTRIBUTIONS_BAND,     expectedNIContributions(            zero()) },
            { annualSalaryOf( 8_060.00), NO_CONTRIBUTIONS_BAND,     expectedNIContributions(            zero()) },
            { annualSalaryOf(10_000.00), NO_CONTRIBUTIONS_BAND,     expectedNIContributions(            zero()) },
            { annualSalaryOf(10_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedNIContributions( new Money(232.80)) },
            { annualSalaryOf(40_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedNIContributions(new Money(3832.80)) },
            { annualSalaryOf(43_000.00), NO_CONTRIBUTIONS_BAND,     expectedNIContributions(            zero()) },
            { annualSalaryOf(43_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedNIContributions(new Money(4192.80)) },
            { annualSalaryOf(45_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedNIContributions(new Money(4192.80)) },
            { annualSalaryOf(45_000.00), HIGHER_CONTRIBUTIONS_BAND, expectedNIContributions(  new Money(40.00)) },
            { annualSalaryOf(50_000.00), NO_CONTRIBUTIONS_BAND,     expectedNIContributions(            zero()) },
            { annualSalaryOf(50_000.00), BASIC_CONTRIBUTIONS_BAND,  expectedNIContributions(new Money(4192.80)) },
            { annualSalaryOf(50_000.00), HIGHER_CONTRIBUTIONS_BAND, expectedNIContributions( new Money(140.00)) },
        }
    );
  }

  private static Money annualSalaryOf(double denomination) {return new Money(denomination);}

  private static Money expectedNIContributions(Money amount) {
    return amount;
  }

  public NationalInsuranceBandShould(
      final Money annualSalary, final NationalInsuranceBand nationalInsuranceBand, final Money expectedNIContributionsForABand) {
    this.annualSalary = annualSalary;
    this.nationalInsuranceBand = nationalInsuranceBand;
    this.expectedNIContributionsForABand = expectedNIContributionsForABand;
  }

  @Test
  public void
  return_the_NI_contributions_for_a_given_range_and_rate_for_a_given_annual_salary() {
    final Money actualAnnualNIContributions = nationalInsuranceBand.calculateFrom(annualSalary);
    assertThat(actualAnnualNIContributions, is(expectedNIContributionsForABand));
  }
}
