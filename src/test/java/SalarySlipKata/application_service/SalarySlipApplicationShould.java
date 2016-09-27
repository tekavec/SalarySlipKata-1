package SalarySlipKata.application_service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;
import static SalarySlipKata.domain.EmployeeBuilder.aEmployee;
import static SalarySlipKata.domain.SalarySlipBuilder.aSalarySlip;
import static SalarySlipKata.domain.TaxDetailsBuilder.aTaxDetails;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.SalarySlip;
import SalarySlipKata.domain_service.NationalInsuranceCalculator;
import SalarySlipKata.domain_service.PersonalAllowanceCalculator;
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
    PersonalAllowanceCalculator personalAllowanceCalculator = new PersonalAllowanceCalculator();
    TaxCalculator taxCalculator = new TaxCalculator(personalAllowanceCalculator);

    salarySlipApplication = new SalarySlipApplication(nationalInsuranceCalculator, taxCalculator);
  }

  @Parameterized.Parameters(name = "For an annual salary of {0}, a monthly salary slip contains {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                annualSalaryOf(24_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(24_000.00))
                    .withGrossSalary(monthly(2000.00))
                    .withTaxDetails(aTaxDetails()
                        .withTaxFreeAllowance(monthly(916.67))
                        .withTaxableIncome(monthly(1083.33))
                        .withTaxPayable(monthly(216.67))
                        .build()
                    )
                    .withNIContributions(monthly(159.40))
                    .withNetPayable(monthly(1623.93))
                    .build()
            },
            {
                annualSalaryOf(5_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(5_000.00))
                    .withGrossSalary(monthly(416.67))
                    .withTaxDetails(aTaxDetails()
                        .withTaxFreeAllowance(monthly(916.67))
                      .withTaxableIncome(monthly(0.00))
                      .withTaxPayable(monthly(0.00))
                      .build()
                )
                    .withNIContributions(monthly(0.00))
                    .withNetPayable(monthly(416.67))
                .build()
            },
            {
                annualSalaryOf(8_060.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(8_060.00))
                    .withGrossSalary(monthly(671.67))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthly(916.67))
                      .withTaxableIncome(monthly(0.00))
                      .withTaxPayable(monthly(0.00))
                      .build()
                    )
                    .withNIContributions(monthly(0.00))
                    .withNetPayable(monthly(671.67))
                    .build()
            },
            {
                annualSalaryOf(12_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(12_000.00))
                    .withGrossSalary(monthly(1000.00))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthly(916.67))
                      .withTaxableIncome(monthly(83.33))
                      .withTaxPayable(monthly(16.67))
                      .build()
                    )
                    .withNIContributions(monthly(39.40))
                    .withNetPayable(monthly(943.93))
                    .build()
            },
            {
                annualSalaryOf(40_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(40_000.00))
                    .withGrossSalary(monthly(3333.33))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthly(916.67))
                      .withTaxableIncome(monthly(2416.67))
                      .withTaxPayable(monthly(483.33))
                      .build()
                    )
                    .withNIContributions(monthly(319.40))
                    .withNetPayable(monthly(2530.60))
                    .build()
            },
            {
                annualSalaryOf(43_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(43_000.00))
                    .withGrossSalary(monthly(3583.33))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthly(916.67))
                      .withTaxableIncome(monthly(2666.67))
                      .withTaxPayable(monthly(533.33))
                      .build()
                    )
                    .withNIContributions(monthly(349.40))
                    .withNetPayable(monthly(2700.60))
                    .build()
            },
            {
                annualSalaryOf(60_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(60_000.00))
                    .withGrossSalary(monthly(5000.00))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthly(916.67))
                      .withTaxableIncome(monthly(4083.33))
                      .withTaxPayable(monthly(1100.00))
                      .build()
                    )
                    .withNIContributions(monthly(377.73))
                    .withNetPayable(monthly(3522.27))
                    .build()
            },
            {
                annualSalaryOf(100_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(100_000.00))
                    .withGrossSalary(monthly(8333.33))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthly(916.67))
                      .withTaxableIncome(monthly(7416.67))
                      .withTaxPayable(monthly(2433.33))
                      .build()
                    )
                    .withNIContributions(monthly(444.40))
                    .withNetPayable(monthly(5455.60))
                    .build()
            },
            {
                annualSalaryOf(111_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(111_000.00))
                    .withGrossSalary(monthly(9250.00))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthly(458.33))
                      .withTaxableIncome(monthly(8791.67))
                      .withTaxPayable(monthly(2983.33))
                      .build()
                    )
                    .withNIContributions(monthly(462.73))
                    .withNetPayable(monthly(5803.94))
                    .build()
            },
            {
                annualSalaryOf(122_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(122_000.00))
                    .withGrossSalary(monthly(10166.67))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthly(0.00))
                      .withTaxableIncome(monthly(10166.67))
                      .withTaxPayable(monthly(3533.33))
                      .build()
                    )
                    .withNIContributions(monthly(481.07))
                    .withNetPayable(monthly(6152.27))
                    .build()
            },
            {
                annualSalaryOf(150_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(150_000.00))
                    .withGrossSalary(monthly(12500.00))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthly(0.00))
                      .withTaxableIncome(monthly(12500.00))
                      .withTaxPayable(monthly(4466.67))
                      .build()
                    )
                    .withNIContributions(monthly(527.73))
                    .withNetPayable(monthly(7505.60))
                    .build()
            },
            {
                annualSalaryOf(160_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(160_000.00))
                    .withGrossSalary(monthly(13333.33))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthly(0.00))
                      .withTaxableIncome(monthly(13333.33))
                      .withTaxPayable(monthly(4841.67))
                      .build()
                    )
                    .withNIContributions(monthly(544.40))
                    .withNetPayable(monthly(7947.26))
                    .build()
            }
        }
    );
  }

  private static Money monthly(double amount) {
    return new Money(amount);
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Employee employeeWithAnnualSalaryOf(double annualSalary) {
    return aEmployee()
                .withId(12345)
                .withName("John J Doe")
                .withAnnualSalary(new Money(annualSalary))
                .build();
  }

  @Test public void
  return_generated_monthly_salary_slip_for_a_given_annual_salary() {
    Employee employee = aEmployee()
                          .withId(12345)
                          .withName("John J Doe")
                          .withAnnualSalary(new Money(annualSalary))
                          .build();
    assertThat(
        salarySlipApplication.generateFor(employee),
        is(salarySlip)
    );
  }
}
