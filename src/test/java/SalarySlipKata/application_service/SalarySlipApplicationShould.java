package SalarySlipKata.application_service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.GBP;
import SalarySlipKata.domain.SalarySlip;
import SalarySlipKata.domain.TaxDetails;
import SalarySlipKata.domain_service.NationalInsuranceCalculator;
import SalarySlipKata.domain_service.TaxCalculator;

@RunWith(Parameterized.class)
public class SalarySlipApplicationShould {

  private SalarySlipApplication salarySlipApplication;

  private GBP annualSalary;
  private SalarySlip salarySlip;

  public SalarySlipApplicationShould(GBP annualSalary, SalarySlip salarySlip) {
    this.annualSalary = annualSalary;
    this.salarySlip = salarySlip;
  }

  @Before
  public void setUp() throws Exception {
    NationalInsuranceCalculator nationalInsuranceCalculator = new NationalInsuranceCalculator();
    TaxCalculator taxCalculator = new TaxCalculator();
    salarySlipApplication = new SalarySlipApplication(nationalInsuranceCalculator, taxCalculator);
  }

  @Parameterized.Parameters(name = "For an annual salary of {0}, a monthly salary slip looks like {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                new GBP(24_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(24_000.00)),
                    new GBP(2000.00),
                    new TaxDetails(new GBP(916.67), new GBP(1083.33), new GBP(216.67)),
                    new GBP(159.40),
                    new GBP(1623.93)
                )
            },
            {
                new GBP(5_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(5_000.00)),
                    new GBP(416.67),
                    new TaxDetails(new GBP(916.67), new GBP(0.00), new GBP(0.00)),
                    new GBP(0.00),
                    new GBP(416.67)
                )
            },
            {
                new GBP(8_060.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(8_060.00)),
                    new GBP(671.67),
                    new TaxDetails(new GBP(916.67), new GBP(0.00), new GBP(0.00)),
                    new GBP(0.00),
                    new GBP(671.67)
                )
            },
            {
                new GBP(12_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(12_000.00)),
                    new GBP(1000.00),
                    new TaxDetails(new GBP(916.67), new GBP(83.33), new GBP(16.67)),
                    new GBP(39.40),
                    new GBP(943.93)
                )
            },
            {
                new GBP(40_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(40_000.00)),
                    new GBP(3333.33),
                    new TaxDetails(new GBP(916.67), new GBP(2416.67), new GBP(483.33)),
                    new GBP(319.40),
                    new GBP(2530.60)
                )
            },
            {
                new GBP(43_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(43_000.00)),
                    new GBP(3583.33),
                    new TaxDetails(new GBP(916.67), new GBP(2666.67), new GBP(533.33)),
                    new GBP(349.40),
                    new GBP(2700.60)
                )
            },
            {
                new GBP(60_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(60_000)),
                    new GBP(5000.00),
                    new TaxDetails(new GBP(916.67), new GBP(4083.33), new GBP(1100.00)),
                    new GBP(377.73),
                    new GBP(3522.27)
                )
            },
            {
                new GBP(100_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(100_000)),
                    new GBP(8333.33),
                    new TaxDetails(new GBP(916.67), new GBP(7416.67), new GBP(2433.33)),
                    new GBP(444.40),
                    new GBP(5455.60)
                )
            },
            {
                new GBP(111_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(111_000)),
                    new GBP(9250.00),
                    new TaxDetails(new GBP(458.33), new GBP(8791.67), new GBP(2983.33)),
                    new GBP(462.73),
                    new GBP(5803.93)
                )
            },
            {
                new GBP(122_000),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(122_000)),
                    new GBP(10166.67),
                    new TaxDetails(new GBP(0.00), new GBP(10166.67), new GBP(3533.33)),
                    new GBP(481.07),
                    new GBP(6152.27)
                )
            },
            {
                new GBP(150_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(150_000)),
                    new GBP(12500.00),
                    new TaxDetails(new GBP(0.00), new GBP(12500.00), new GBP(4466.67)),
                    new GBP(527.73),
                    new GBP(7505.60)
                )
            },
            {
                new GBP(160_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new GBP(160_000)),
                    new GBP(13333.33),
                    new TaxDetails(new GBP(0.00), new GBP(13333.33), new GBP(4841.67)),
                    new GBP(544.40),
                    new GBP(7947.27)
                )
            }
        }
    );
  }

  private static SalarySlip createSalarySlip(Employee employee, GBP grossSalary, TaxDetails taxDetails,
      GBP  niContributions, GBP netPayable) {
    return new SalarySlip(
        employee, grossSalary, taxDetails, niContributions, netPayable
    );
  }

  @Test public void
  return_generated_monthly_salary_slip_for_a_given_annual_salary() {
    Employee employee = new Employee(12345, "John J Doe", annualSalary);

    assertThat(
        salarySlipApplication.generateFor(employee).toString(),
        is(salarySlip.toString())
    );
  }
}
