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
                annualSalaryOf(new Money(24_000.00)),
                createSalarySlip(
                    employeeWith(new Money(24_000.00)),
                    grossSalaryOf(new Money(2000.00)),
                    taxDetailsWith(taxFreeAllowance(new Money(916.67)), taxableIncome(new Money(1083.33)), taxPayable(new Money(216.67))),
                    nationalInsuranceContributionsOf(new Money(159.40)),
                    netPayableOf(new Money(1623.93))
                )
            },
            {
                annualSalaryOf(new Money(5_000.00)),
                createSalarySlip(
                    employeeWith(new Money(5_000.00)),
                    grossSalaryOf(new Money(416.67)),
                    taxDetailsWith(taxFreeAllowance(new Money(916.67)), taxableIncome(new Money(0.00)), taxPayable(new Money(0.00))),
                    nationalInsuranceContributionsOf(new Money(0.00)),
                    netPayableOf(new Money(416.67))
                )
            },
            {
                annualSalaryOf(new Money(8_060.00)),
                createSalarySlip(
                    employeeWith(new Money(8_060.00)),
                    grossSalaryOf(new Money(671.67)),
                    taxDetailsWith(taxFreeAllowance(new Money(916.67)), taxableIncome(new Money(0.00)), taxPayable(new Money(0.00))),
                    nationalInsuranceContributionsOf(new Money(0.00)),
                    netPayableOf(new Money(671.67))
                )
            },
            {
                annualSalaryOf(new Money(12_000.00)),
                createSalarySlip(
                    employeeWith(new Money(12_000.00)),
                    grossSalaryOf(new Money(1000.00)),
                    taxDetailsWith(taxFreeAllowance(new Money(916.67)), taxableIncome(new Money(83.33)), taxPayable(new Money(16.67))),
                    nationalInsuranceContributionsOf(new Money(39.40)),
                    netPayableOf(new Money(943.93))
                )
            },
            {
                annualSalaryOf(new Money(40_000.00)),
                createSalarySlip(
                    employeeWith(new Money(40_000.00)),
                    grossSalaryOf(new Money(3333.33)),
                    taxDetailsWith(taxFreeAllowance(new Money(916.67)), taxableIncome(new Money(2416.67)), taxPayable(new Money(483.33))),
                    nationalInsuranceContributionsOf(new Money(319.40)),
                    netPayableOf(new Money(2530.60))
                )
            },
            {
                annualSalaryOf(new Money(43_000.00)),
                createSalarySlip(
                    employeeWith(new Money(43_000.00)),
                    grossSalaryOf(new Money(3583.33)),
                    taxDetailsWith(taxFreeAllowance(new Money(916.67)), taxableIncome(new Money(2666.67)), taxPayable(new Money(533.33))),
                    nationalInsuranceContributionsOf(new Money(349.40)),
                    netPayableOf(new Money(2700.60))
                )
            },
            {
                annualSalaryOf(new Money(60_000.00)),
                createSalarySlip(
                    employeeWith(new Money(60_000.00)),
                    grossSalaryOf(new Money(5000.00)),
                    taxDetailsWith(taxFreeAllowance(new Money(916.67)), taxableIncome(new Money(4083.33)), taxPayable(new Money(1100.00))),
                    nationalInsuranceContributionsOf(new Money(377.73)),
                    netPayableOf(new Money(3522.27))
                )
            },
            {
                annualSalaryOf(new Money(100_000.00)),
                createSalarySlip(
                    employeeWith(new Money(100_000.00)),
                    grossSalaryOf(new Money(8333.33)),
                    taxDetailsWith(taxFreeAllowance(new Money(916.67)), taxableIncome(new Money(7416.67)), taxPayable(new Money(2433.33))),
                    nationalInsuranceContributionsOf(new Money(444.40)),
                    netPayableOf(new Money(5455.60))
                )
            },
            {
                annualSalaryOf(new Money(111_000.00)),
                createSalarySlip(
                    employeeWith(new Money(111_000.00)),
                    grossSalaryOf(new Money(9250.00)),
                    taxDetailsWith(taxFreeAllowance(new Money(458.33)), taxableIncome(new Money(8791.67)), taxPayable(new Money(2983.33))),
                    nationalInsuranceContributionsOf(new Money(462.73)),
                    netPayableOf(new Money(5803.94))
                )
            },
            {
                annualSalaryOf(new Money(122_000.00)),
                createSalarySlip(
                    employeeWith(new Money(122_000.00)),
                    grossSalaryOf(new Money(10166.67)),
                    taxDetailsWith(taxFreeAllowance(new Money(0.00)), taxableIncome(new Money(10166.67)), taxPayable(new Money(3533.33))),
                    nationalInsuranceContributionsOf(new Money(481.07)),
                    netPayableOf(new Money(6152.27))
                )
            },
            {
                annualSalaryOf(new Money(150_000.00)),
                createSalarySlip(
                    employeeWith(new Money(150_000.00)),
                    grossSalaryOf(new Money(12500.00)),
                    taxDetailsWith(taxFreeAllowance(new Money(0.00)), taxableIncome(new Money(12500.00)), taxPayable(new Money(4466.67))),
                    nationalInsuranceContributionsOf(new Money(527.73)),
                    netPayableOf(new Money(7505.60))
                )
            },
            {
                annualSalaryOf(new Money(160_000.00)),
                createSalarySlip(
                    employeeWith(new Money(160_000.00)),
                    grossSalaryOf(new Money(13333.33)),
                    taxDetailsWith(taxFreeAllowance(new Money(0.00)), taxableIncome(new Money(13333.33)), taxPayable(new Money(4841.67))),
                    nationalInsuranceContributionsOf(new Money(544.40)),
                    netPayableOf(new Money(7947.26))
                )
            }
        }
    );
  }

  private static Money annualSalaryOf(Money money) {
    return money;
  }

  private static SalarySlip createSalarySlip(Employee employee, Money grossSalary, TaxDetails taxDetails,
      Money niContributions, Money netPayable) {
    return new SalarySlip(
        employee, grossSalary, taxDetails, niContributions, netPayable
    );
  }

  private static Employee employeeWith(Money annualSalary) {
    return new Employee(12345, "John J Doe", annualSalary);
  }

  private static Money grossSalaryOf(Money money) {
    return money;
  }

  private static Money nationalInsuranceContributionsOf(Money money) {
    return money;
  }

  private static TaxDetails taxDetailsWith(Money taxFreeAllowance, Money taxableIncome, Money taxPayable) {
    return new TaxDetails(taxFreeAllowance, taxableIncome, taxPayable);
  }

  private static Money taxFreeAllowance(Money money) {
    return money;
  }

  private static Money taxableIncome(Money money) {
    return money;
  }

  private static Money taxPayable(Money money) {
    return money;
  }

  private static Money netPayableOf(Money money) {
    return money;
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
