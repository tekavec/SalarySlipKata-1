package SalarySlipKata.application_service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class NationalInsuranceServiceShould {

  private final double annualSalary;
  private final double expectedNationalInsuranceContribution;

  private NationalInsuranceService nationalInsuranceService;

  public NationalInsuranceServiceShould(double annualSalary, double expectedNationalInsuranceContribution) {
    this.annualSalary = annualSalary;
    this.expectedNationalInsuranceContribution = expectedNationalInsuranceContribution;
  }

  @Before
  public void setUp() throws Exception {
    nationalInsuranceService = new NationalInsuranceService();
  }

  @Parameterized.Parameters(name = "annual salary of {0} should result in an NI Contribution of {1}")
  public static Collection<Object[]> data() {
    return Arrays.asList(
        new Object[][] {
            {  5000.00,    0.00 },
            {  8060.00,    0.00 },
            {  9060.00,  120.00 },
            { 43000.00, 4192.80 },
            { 45000.00, 4232.80 },
            { 50000.00, 4332.80 },
            { 60000.00, 4532.80 },
        }
    );
  }

  @Test public void
  return_the_NI_contributions_for_a_given_annual_salary_based_on_the_NI_bands() {
    assertThat(nationalInsuranceService.calculateFor(annualSalary), is(equalTo(expectedNationalInsuranceContribution)));
  }
}
