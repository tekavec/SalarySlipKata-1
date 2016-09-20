package SalarySlipKata.application_service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain_service.NationalInsuranceCalculator;
import SalarySlipKata.domain_service.SalaryCalculator;
import SalarySlipKata.domain_service.TaxCalculator;

@RunWith(Parameterized.class)
public class SalarySlipApplicationShould {

  private SalarySlipApplication salarySlipApplication;

  private int annualSalary;
  private List<String> salarySlip;
  private SalaryCalculator salaryCalculator;

  public SalarySlipApplicationShould(int annualSalary, List<String> salarySlip) {
    this.annualSalary = annualSalary;
    this.salarySlip = salarySlip;
  }

  @Before
  public void setUp() throws Exception {
    NationalInsuranceCalculator nationalInsuranceCalculator = new NationalInsuranceCalculator();
    TaxCalculator taxCalculator = new TaxCalculator();
    salaryCalculator = new SalaryCalculator(nationalInsuranceCalculator, taxCalculator);
    salarySlipApplication = new SalarySlipApplication(salaryCalculator);
  }

  @Parameterized.Parameters
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { 24_000, asList("Employee ID: 12345\n",
                "Employee Name: John J Doe\n",
                "Gross Salary: £2000.00\n",
                "Tax-free allowance: £916.67\n",
                "Taxable income: £1083.33\n",
                "National Insurance contributions: £159.40\n",
                "Tax Payable: £216.67\n",
                "Net Payable: £1623.93\n")
            }
        }
    );
  }
  
  @Test public void
  return_generated_monthly_salary_slip_for_a_given_annual_salary() {
    Employee employee = new Employee(12345, "John J Doe", annualSalary);

    assertThat(
        salarySlipApplication.generateFor(employee),
        equalTo(salarySlip)
    );
  }
}
