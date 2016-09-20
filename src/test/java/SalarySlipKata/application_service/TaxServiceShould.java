package SalarySlipKata.application_service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TaxServiceShould {
  private TaxService taxService;
  private double annualSalary;
  private double taxPayable;

  @Parameterized.Parameters(name="for an annual salary of {0}, the tax payable is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {  10_000.00,      0.00 },
            {  11_000.00,      0.00 },
            {  12_000.00,    200.00 },
            {  40_000.00,  5_800.00 },
            {  45_000.00,  7_200.00 },
            {  60_000.00, 13_200.00 },
            { 100_000.00, 29_200.00 },
            { 111_000.00, 35_800.00 },
            { 122_000.00, 42_400.00 },
            { 150_000.00, 53_600.00 },
            { 160_000.00, 58_100.00 },
        }
    );
  }

  public TaxServiceShould(double annualSalary, double taxPayable) {
    this.annualSalary = annualSalary;
    this.taxPayable = taxPayable;
  }

  @Before
  public void setUp() throws Exception {
    taxService = new TaxService();
  }

  @Test public void
  return_the_tax_payable_for_the_annual_salary_based_on_the_various_tax_bands() {
    assertThat(taxService.calculateFor(annualSalary), is(taxPayable));
  } 
}
