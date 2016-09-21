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
import SalarySlipKata.domain_service.TaxCalculator;

@RunWith(Parameterized.class)
public class SalarySlipApplicationShould {

  private SalarySlipApplication salarySlipApplication;

  private int annualSalary;
  private List<String> salarySlip;

  public SalarySlipApplicationShould(int annualSalary, List<String> salarySlip) {
    this.annualSalary = annualSalary;
    this.salarySlip = salarySlip;
  }

  @Before
  public void setUp() throws Exception {
    NationalInsuranceCalculator nationalInsuranceCalculator = new NationalInsuranceCalculator();
    TaxCalculator taxCalculator = new TaxCalculator();
    salarySlipApplication = new SalarySlipApplication(nationalInsuranceCalculator, taxCalculator);
  }

  @Parameterized.Parameters(name = "For an annual salary of {0}, a salary slip looks like {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                24_000, asList(
                    "Employee ID: 12345\n",
                    "Employee Name: John J Doe\n",
                    "Gross Salary: £2000.00\n",
                    "Tax-free allowance: £916.67\n",
                    "Taxable income: £1083.33\n",
                    "National Insurance contributions: £159.40\n",
                    "Tax Payable: £216.67\n",
                    "Net Payable: £1623.93\n"
                )
            },
            {
                5_000, asList(
                    "Employee ID: 12345\n",
                    "Employee Name: John J Doe\n",
                    "Gross Salary: £416.67\n",
                    "Tax-free allowance: £916.67\n",
                    "Taxable income: £0.00\n",
                    "National Insurance contributions: £0.00\n",
                    "Tax Payable: £0.00\n",
                    "Net Payable: £416.67\n"
                )
            },
            {
                8_060, asList(
                  "Employee ID: 12345\n",
                  "Employee Name: John J Doe\n",
                  "Gross Salary: £671.67\n",
                  "Tax-free allowance: £916.67\n",
                  "Taxable income: £0.00\n",
                  "National Insurance contributions: £0.00\n",
                  "Tax Payable: £0.00\n",
                  "Net Payable: £671.67\n"
                )
            },
            {
                12_000, asList(
                  "Employee ID: 12345\n",
                  "Employee Name: John J Doe\n",
                  "Gross Salary: £1000.00\n",
                  "Tax-free allowance: £916.67\n",
                  "Taxable income: £83.33\n",
                  "National Insurance contributions: £39.40\n",
                  "Tax Payable: £16.67\n",
                  "Net Payable: £943.93\n"
                )
            },
            {
                40_000, asList(
                  "Employee ID: 12345\n",
                  "Employee Name: John J Doe\n",
                  "Gross Salary: £3333.33\n",
                  "Tax-free allowance: £916.67\n",
                  "Taxable income: £2416.67\n",
                  "National Insurance contributions: £319.40\n",
                  "Tax Payable: £483.33\n",
                  "Net Payable: £2530.60\n"
                )
            },
            {
                43_000, asList(
                  "Employee ID: 12345\n",
                  "Employee Name: John J Doe\n",
                  "Gross Salary: £3583.33\n",
                  "Tax-free allowance: £916.67\n",
                  "Taxable income: £2666.67\n",
                  "National Insurance contributions: £349.40\n",
                  "Tax Payable: £533.33\n",
                  "Net Payable: £2700.60\n"
                )
            },
            {
                60_000, asList(
                  "Employee ID: 12345\n",
                  "Employee Name: John J Doe\n",
                  "Gross Salary: £5000.00\n",
                  "Tax-free allowance: £916.67\n",
                  "Taxable income: £4083.33\n",
                  "National Insurance contributions: £377.73\n",
                  "Tax Payable: £1100.00\n",
                  "Net Payable: £3522.27\n"
                )
            },
            {
                100_000, asList(
                  "Employee ID: 12345\n",
                  "Employee Name: John J Doe\n",
                  "Gross Salary: £8333.33\n",
                  "Tax-free allowance: £916.67\n",
                  "Taxable income: £7416.67\n",
                  "National Insurance contributions: £444.40\n",
                  "Tax Payable: £2433.33\n",
                  "Net Payable: £5455.60\n"
                )
            },
            {
                111_000, asList(
                  "Employee ID: 12345\n",
                  "Employee Name: John J Doe\n",
                  "Gross Salary: £9250.00\n",
                  "Tax-free allowance: £458.33\n",
                  "Taxable income: £8791.67\n",
                  "National Insurance contributions: £462.73\n",
                  "Tax Payable: £2983.33\n",
                  "Net Payable: £5803.93\n"
                )
            },
            {
                122_000, asList(
                "Employee ID: 12345\n",
                "Employee Name: John J Doe\n",
                "Gross Salary: £10166.67\n",
                "Tax-free allowance: £0.00\n",
                "Taxable income: £10166.67\n",
                "National Insurance contributions: £481.07\n",
                "Tax Payable: £3533.33\n",
                "Net Payable: £6152.27\n"
                )
            },
            {
                150_000, asList(
                "Employee ID: 12345\n",
                "Employee Name: John J Doe\n",
                "Gross Salary: £12500.00\n",
                "Tax-free allowance: £0.00\n",
                "Taxable income: £12500.00\n",
                "National Insurance contributions: £527.73\n",
                "Tax Payable: £4466.67\n",
                "Net Payable: £7505.60\n"
                )
            },
            {
                160_000, asList(
                "Employee ID: 12345\n",
                "Employee Name: John J Doe\n",
                "Gross Salary: £13333.33\n",
                "Tax-free allowance: £0.00\n",
                "Taxable income: £13333.33\n",
                "National Insurance contributions: £544.40\n",
                "Tax Payable: £4841.67\n",
                "Net Payable: £7947.27\n"
                )
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
