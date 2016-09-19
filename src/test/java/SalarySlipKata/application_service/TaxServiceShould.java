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
            {  10000.00,     0.00 },
            {  11000.00,     0.00 },
            {  12000.00,   200.00 },
            {  40000.00,  5800.00 },
            {  45000.00,  7200.00 },
            {  60000.00, 13200.00 },
            { 100000.00, 29200.00 },
            { 111000.00, 35800.00 },
            { 122000.00, 42400.00 },
            { 150000.00, 53600.00 },
            { 160000.00, 58100.00 },
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
