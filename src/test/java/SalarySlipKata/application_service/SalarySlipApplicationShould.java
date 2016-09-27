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
import SalarySlipKata.domain.rule.PersonalAllowanceReductionOver100K;
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
    PersonalAllowanceReductionOver100K
        personalAllowanceReductionOver100K = new PersonalAllowanceReductionOver100K();
    TaxCalculator taxCalculator = new TaxCalculator(personalAllowanceReductionOver100K);

    salarySlipApplication = new SalarySlipApplication(nationalInsuranceCalculator, taxCalculator);
  }

  @Parameterized.Parameters(name = "For an annual salary of {0}, a monthly salary slip contains {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                annualSalaryOf(24_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(24_000.00),
                    monthlyGrossSalaryOf(2000.00),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(916.67), monthlyTaxableIncomeOf(1083.33), monthlyTaxPayableOf(216.67)),
                    monthlyNationalInsuranceContributionsOf(159.40),
                    monthlyNetPayableOf(1623.93)
                )
            },
            {
                annualSalaryOf(5_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(5_000.00),
                    monthlyGrossSalaryOf(416.67),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(916.67), monthlyTaxableIncomeOf(0.00), monthlyTaxPayableOf(0.00)),
                    monthlyNationalInsuranceContributionsOf(0.00),
                    monthlyNetPayableOf(416.67)
                )
            },
            {
                annualSalaryOf(8_060.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(8_060.00),
                    monthlyGrossSalaryOf(671.67),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(916.67), monthlyTaxableIncomeOf(0.00), monthlyTaxPayableOf(0.00)),
                    monthlyNationalInsuranceContributionsOf(0.00),
                    monthlyNetPayableOf(671.67)
                )
            },
            {
                annualSalaryOf(12_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(12_000.00),
                    monthlyGrossSalaryOf(1000.00),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(916.67), monthlyTaxableIncomeOf(83.33), monthlyTaxPayableOf(16.67)),
                    monthlyNationalInsuranceContributionsOf(39.40),
                    monthlyNetPayableOf(943.93)
                )
            },
            {
                annualSalaryOf(40_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(40_000.00),
                    monthlyGrossSalaryOf(3333.33),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(916.67), monthlyTaxableIncomeOf(2416.67), monthlyTaxPayableOf(483.33)),
                    monthlyNationalInsuranceContributionsOf(319.40),
                    monthlyNetPayableOf(2530.60)
                )
            },
            {
                annualSalaryOf(43_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(43_000.00),
                    monthlyGrossSalaryOf(3583.33),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(916.67), monthlyTaxableIncomeOf(2666.67), monthlyTaxPayableOf(533.33)),
                    monthlyNationalInsuranceContributionsOf(349.40),
                    monthlyNetPayableOf(2700.60)
                )
            },
            {
                annualSalaryOf(60_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(60_000.00),
                    monthlyGrossSalaryOf(5000.00),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(916.67), monthlyTaxableIncomeOf(4083.33), monthlyTaxPayableOf(1100.00)),
                    monthlyNationalInsuranceContributionsOf(377.73),
                    monthlyNetPayableOf(3522.27)
                )
            },
            {
                annualSalaryOf(100_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(100_000.00),
                    monthlyGrossSalaryOf(8333.33),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(916.67), monthlyTaxableIncomeOf(7416.67), monthlyTaxPayableOf(2433.33)),
                    monthlyNationalInsuranceContributionsOf(444.40),
                    monthlyNetPayableOf(5455.60)
                )
            },
            {
                annualSalaryOf(111_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(111_000.00),
                    monthlyGrossSalaryOf(9250.00),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(458.33), monthlyTaxableIncomeOf(8791.67), monthlyTaxPayableOf(2983.33)),
                    monthlyNationalInsuranceContributionsOf(462.73),
                    monthlyNetPayableOf(5803.94)
                )
            },
            {
                annualSalaryOf(122_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(122_000.00),
                    monthlyGrossSalaryOf(10166.67),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(0.00), monthlyTaxableIncomeOf(10166.67), monthlyTaxPayableOf(3533.33)),
                    monthlyNationalInsuranceContributionsOf(481.07),
                    monthlyNetPayableOf(6152.27)
                )
            },
            {
                annualSalaryOf(150_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(150_000.00),
                    monthlyGrossSalaryOf(12500.00),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(0.00), monthlyTaxableIncomeOf(12500.00), monthlyTaxPayableOf(4466.67)),
                    monthlyNationalInsuranceContributionsOf(527.73),
                    monthlyNetPayableOf(7505.60)
                )
            },
            {
                annualSalaryOf(160_000.00),
                createSalarySlipFrom(
                    employeeWithAnnualSalaryOf(160_000.00),
                    monthlyGrossSalaryOf(13333.33),
                    monthlyTaxDetailsWith(monthlyTaxFreeAllowanceOf(0.00), monthlyTaxableIncomeOf(13333.33), monthlyTaxPayableOf(4841.67)),
                    monthlyNationalInsuranceContributionsOf(544.40),
                    monthlyNetPayableOf(7947.26)
                )
            }
        }
    );
  }

  private static double annualSalaryOf(double money) {
    return money;
  }

  private static SalarySlip createSalarySlipFrom(Employee employee, Money grossSalary,
      TaxDetails taxDetails, Money niContributions, Money netPayable) {
    return new SalarySlip(
        employee, grossSalary, taxDetails, niContributions, netPayable
    );
  }

  private static Employee employeeWithAnnualSalaryOf(double annualSalary) {
    return new Employee(12345, "John J Doe", new Money(annualSalary));
  }

  private static Money monthlyGrossSalaryOf(double money) {
    return new Money(money);
  }

  private static Money monthlyNationalInsuranceContributionsOf(double money) {
    return new Money(money);
  }

  private static TaxDetails monthlyTaxDetailsWith(Money taxFreeAllowance, Money taxableIncome, Money taxPayable) {
    return new TaxDetails(taxFreeAllowance, taxableIncome, taxPayable);
  }

  private static Money monthlyTaxFreeAllowanceOf(double money) {
    return new Money(money);
  }

  private static Money monthlyTaxableIncomeOf(double money) {
    return new Money(money);
  }

  private static Money monthlyTaxPayableOf(double money) {
    return new Money(money);
  }

  private static Money monthlyNetPayableOf(double money) {
    return new Money(money);
  }

  @Test public void
  return_generated_monthly_salary_slip_for_a_given_annual_salary() {
    Employee employee = employeeWithAnnualSalaryOf(annualSalary);

    assertThat(
        salarySlipApplication.generateFor(employee),
        is(salarySlip)
    );
  }
}
