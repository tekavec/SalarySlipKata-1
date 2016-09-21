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
import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.SalarySlip;
import SalarySlipKata.domain.TaxDetails;
import SalarySlipKata.domain_service.NationalInsuranceCalculator;
import SalarySlipKata.domain_service.TaxCalculator;

@RunWith(Parameterized.class)
public class SalarySlipApplicationShould {

  private SalarySlipApplication salarySlipApplication;

  private Money annualSalary;
  private SalarySlip salarySlip;

  public SalarySlipApplicationShould(Money annualSalary, SalarySlip salarySlip) {
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
                new Money(24_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(24_000.00)),
                    new Money(2000.00),
                    new TaxDetails(new Money(916.67), new Money(1083.33), new Money(216.67)),
                    new Money(159.40),
                    new Money(1623.93)
                )
            },
            {
                new Money(5_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(5_000.00)),
                    new Money(416.67),
                    new TaxDetails(new Money(916.67), new Money(0.00), new Money(0.00)),
                    new Money(0.00),
                    new Money(416.67)
                )
            },
            {
                new Money(8_060.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(8_060.00)),
                    new Money(671.67),
                    new TaxDetails(new Money(916.67), new Money(0.00), new Money(0.00)),
                    new Money(0.00),
                    new Money(671.67)
                )
            },
            {
                new Money(12_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(12_000.00)),
                    new Money(1000.00),
                    new TaxDetails(new Money(916.67), new Money(83.33), new Money(16.67)),
                    new Money(39.40),
                    new Money(943.93)
                )
            },
            {
                new Money(40_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(40_000.00)),
                    new Money(3333.33),
                    new TaxDetails(new Money(916.67), new Money(2416.67), new Money(483.33)),
                    new Money(319.40),
                    new Money(2530.60)
                )
            },
            {
                new Money(43_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(43_000.00)),
                    new Money(3583.33),
                    new TaxDetails(new Money(916.67), new Money(2666.67), new Money(533.33)),
                    new Money(349.40),
                    new Money(2700.60)
                )
            },
            {
                new Money(60_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(60_000)),
                    new Money(5000.00),
                    new TaxDetails(new Money(916.67), new Money(4083.33), new Money(1100.00)),
                    new Money(377.73),
                    new Money(3522.27)
                )
            },
            {
                new Money(100_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(100_000)),
                    new Money(8333.33),
                    new TaxDetails(new Money(916.67), new Money(7416.67), new Money(2433.33)),
                    new Money(444.40),
                    new Money(5455.60)
                )
            },
            {
                new Money(111_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(111_000)),
                    new Money(9250.00),
                    new TaxDetails(new Money(458.33), new Money(8791.67), new Money(2983.33)),
                    new Money(462.73),
                    new Money(5803.94)
                )
            },
            {
                new Money(122_000),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(122_000)),
                    new Money(10166.67),
                    new TaxDetails(new Money(0.00), new Money(10166.67), new Money(3533.33)),
                    new Money(481.07),
                    new Money(6152.27)
                )
            },
            {
                new Money(150_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(150_000)),
                    new Money(12500.00),
                    new TaxDetails(new Money(0.00), new Money(12500.00), new Money(4466.67)),
                    new Money(527.73),
                    new Money(7505.60)
                )
            },
            {
                new Money(160_000.00),
                createSalarySlip(
                    new Employee(12345, "John J Doe", new Money(160_000)),
                    new Money(13333.33),
                    new TaxDetails(new Money(0.00), new Money(13333.33), new Money(4841.67)),
                    new Money(544.40),
                    new Money(7947.26)
                )
            }
        }
    );
  }

  private static SalarySlip createSalarySlip(Employee employee, Money grossSalary, TaxDetails taxDetails,
      Money niContributions, Money netPayable) {
    return new SalarySlip(
        employee, grossSalary, taxDetails, niContributions, netPayable
    );
  }

  @Test public void
  return_generated_monthly_salary_slip_for_a_given_annual_salary() {
    Employee employee = new Employee(12345, "John J Doe", annualSalary);

    assertThat(
        salarySlipApplication.generateFor(employee),
        is(salarySlip)
    );
  }
}
