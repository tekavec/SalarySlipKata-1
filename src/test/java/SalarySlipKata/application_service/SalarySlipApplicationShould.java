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

  private double annualSalary;
  private SalarySlip salarySlip;

  public SalarySlipApplicationShould(double annualSalary, SalarySlip salarySlip) {
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
                annualSalaryOf(24_000.00),
                createSalarySlipFrom(
                    employeeWith(24_000.00),
                    grossSalaryOf(2000.00),
                    taxDetailsWith(taxFreeAllowance(916.67), taxableIncome(1083.33), taxPayable(216.67)),
                    nationalInsuranceContributionsOf(159.40),
                    netPayableOf(1623.93)
                )
            },
            {
                annualSalaryOf(5_000.00),
                createSalarySlipFrom(
                    employeeWith(5_000.00),
                    grossSalaryOf(416.67),
                    taxDetailsWith(taxFreeAllowance(916.67), taxableIncome(0.00), taxPayable(0.00)),
                    nationalInsuranceContributionsOf(0.00),
                    netPayableOf(416.67)
                )
            },
            {
                annualSalaryOf(8_060.00),
                createSalarySlipFrom(
                    employeeWith(8_060.00),
                    grossSalaryOf(671.67),
                    taxDetailsWith(taxFreeAllowance(916.67), taxableIncome(0.00), taxPayable(0.00)),
                    nationalInsuranceContributionsOf(0.00),
                    netPayableOf(671.67)
                )
            },
            {
                annualSalaryOf(12_000.00),
                createSalarySlipFrom(
                    employeeWith(12_000.00),
                    grossSalaryOf(1000.00),
                    taxDetailsWith(taxFreeAllowance(916.67), taxableIncome(83.33), taxPayable(16.67)),
                    nationalInsuranceContributionsOf(39.40),
                    netPayableOf(943.93)
                )
            },
            {
                annualSalaryOf(40_000.00),
                createSalarySlipFrom(
                    employeeWith(40_000.00),
                    grossSalaryOf(3333.33),
                    taxDetailsWith(taxFreeAllowance(916.67), taxableIncome(2416.67), taxPayable(483.33)),
                    nationalInsuranceContributionsOf(319.40),
                    netPayableOf(2530.60)
                )
            },
            {
                annualSalaryOf(43_000.00),
                createSalarySlipFrom(
                    employeeWith(43_000.00),
                    grossSalaryOf(3583.33),
                    taxDetailsWith(taxFreeAllowance(916.67), taxableIncome(2666.67), taxPayable(533.33)),
                    nationalInsuranceContributionsOf(349.40),
                    netPayableOf(2700.60)
                )
            },
            {
                annualSalaryOf(60_000.00),
                createSalarySlipFrom(
                    employeeWith(60_000.00),
                    grossSalaryOf(5000.00),
                    taxDetailsWith(taxFreeAllowance(916.67), taxableIncome(4083.33), taxPayable(1100.00)),
                    nationalInsuranceContributionsOf(377.73),
                    netPayableOf(3522.27)
                )
            },
            {
                annualSalaryOf(100_000.00),
                createSalarySlipFrom(
                    employeeWith(100_000.00),
                    grossSalaryOf(8333.33),
                    taxDetailsWith(taxFreeAllowance(916.67), taxableIncome(7416.67), taxPayable(2433.33)),
                    nationalInsuranceContributionsOf(444.40),
                    netPayableOf(5455.60)
                )
            },
            {
                annualSalaryOf(111_000.00),
                createSalarySlipFrom(
                    employeeWith(111_000.00),
                    grossSalaryOf(9250.00),
                    taxDetailsWith(taxFreeAllowance(458.33), taxableIncome(8791.67), taxPayable(2983.33)),
                    nationalInsuranceContributionsOf(462.73),
                    netPayableOf(5803.94)
                )
            },
            {
                annualSalaryOf(122_000.00),
                createSalarySlipFrom(
                    employeeWith(122_000.00),
                    grossSalaryOf(10166.67),
                    taxDetailsWith(taxFreeAllowance(0.00), taxableIncome(10166.67), taxPayable(3533.33)),
                    nationalInsuranceContributionsOf(481.07),
                    netPayableOf(6152.27)
                )
            },
            {
                annualSalaryOf(150_000.00),
                createSalarySlipFrom(
                    employeeWith(150_000.00),
                    grossSalaryOf(12500.00),
                    taxDetailsWith(taxFreeAllowance(0.00), taxableIncome(12500.00), taxPayable(4466.67)),
                    nationalInsuranceContributionsOf(527.73),
                    netPayableOf(7505.60)
                )
            },
            {
                annualSalaryOf(160_000.00),
                createSalarySlipFrom(
                    employeeWith(160_000.00),
                    grossSalaryOf(13333.33),
                    taxDetailsWith(taxFreeAllowance(0.00), taxableIncome(13333.33), taxPayable(4841.67)),
                    nationalInsuranceContributionsOf(544.40),
                    netPayableOf(7947.26)
                )
            }
        }
    );
  }

  private static double annualSalaryOf(double money) {
    return money;
  }

  private static SalarySlip createSalarySlipFrom(Employee employee, Money grossSalary, TaxDetails taxDetails,
      Money niContributions, Money netPayable) {
    return new SalarySlip(
        employee, grossSalary, taxDetails, niContributions, netPayable
    );
  }

  private static Employee employeeWith(double annualSalary) {
    return new Employee(12345, "John J Doe", new Money(annualSalary));
  }

  private static Money grossSalaryOf(double money) {
    return new Money(money);
  }

  private static Money nationalInsuranceContributionsOf(double money) {
    return new Money(money);
  }

  private static TaxDetails taxDetailsWith(Money taxFreeAllowance, Money taxableIncome, Money taxPayable) {
    return new TaxDetails(taxFreeAllowance, taxableIncome, taxPayable);
  }

  private static Money taxFreeAllowance(double money) {
    return new Money(money);
  }

  private static Money taxableIncome(double money) {
    return new Money(money);
  }

  private static Money taxPayable(double money) {
    return new Money(money);
  }

  private static Money netPayableOf(double money) {
    return new Money(money);
  }

  @Test public void
  return_generated_monthly_salary_slip_for_a_given_annual_salary() {
    Employee employee = employeeWith(annualSalary);

    assertThat(
        salarySlipApplication.generateFor(employee),
        is(salarySlip)
    );
  }
}
