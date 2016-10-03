package org.neomatrix369.salaryslip;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.neomatrix369.salaryslip.EmployeeBuilder.aEmployee;
import static org.neomatrix369.salaryslip.SalarySlipBuilder.aSalarySlip;
import static org.neomatrix369.salaryslip.tax.TaxDetailsBuilder.aTaxDetails;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.neomatrix369.salaryslip.components.Employee;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.SalarySlip;
import org.neomatrix369.salaryslip.national_insurance.NationalInsuranceCalculator;
import org.neomatrix369.salaryslip.personal_allowance.PersonalAllowanceCalculator;
import org.neomatrix369.salaryslip.tax.TaxCalculator;

@RunWith(Parameterized.class)
public class SalarySlipGeneratorShould {

  private static EmployeeBuilder createEmployeeWith(Money annualSalary) {
    return aEmployee()
        .withId(12345)
        .withName("John J Doe")
        .withAnnualSalary(new Money(annualSalary));
  }

  private SalarySlipGenerator salarySlipGenerator;
  private Money annualSalary;

  private SalarySlip salarySlip;

  public SalarySlipGeneratorShould(Money annualSalary, SalarySlip salarySlip) {
    this.annualSalary = annualSalary;
    this.salarySlip = salarySlip;
  }

  @Before
  public void setUp() throws Exception {
    NationalInsuranceCalculator nationalInsuranceCalculator = new NationalInsuranceCalculator();
    PersonalAllowanceCalculator personalAllowanceCalculator = new PersonalAllowanceCalculator();
    TaxCalculator taxCalculator = new TaxCalculator(personalAllowanceCalculator);

    salarySlipGenerator = new SalarySlipGenerator(nationalInsuranceCalculator, taxCalculator);
  }

  @Parameterized.Parameters(name = "For an annual salary of {0}, the monthly salary slip contains {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                annualSalaryOf(24_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(24_000.00))
                    .withGrossSalary(monthlyOf(2_000.00))
                    .withTaxDetails(aTaxDetails()
                        .withTaxFreeAllowance(monthlyOf(916.67))
                        .withTaxableIncome(monthlyOf(1_083.33))
                        .withTaxPayable(monthlyOf(216.67))
                        .build()
                    )
                    .withNIContributions(monthlyOf(159.40))
                    .build()
            },
            {
                annualSalaryOf(5_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(5_000.00))
                    .withGrossSalary(monthlyOf(416.67))
                    .withTaxDetails(aTaxDetails()
                        .withTaxFreeAllowance(monthlyOf(916.67))
                      .withTaxableIncome(monthlyOf(0.00))
                      .withTaxPayable(monthlyOf(0.00))
                      .build()
                )
                    .withNIContributions(monthlyOf(0.00))
                .build()
            },
            {
                annualSalaryOf(8_060.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(8_060.00))
                    .withGrossSalary(monthlyOf(671.67))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(916.67))
                      .withTaxableIncome(monthlyOf(0.00))
                      .withTaxPayable(monthlyOf(0.00))
                      .build()
                    )
                    .withNIContributions(monthlyOf(0.00))
                    .build()
            },
            {
                annualSalaryOf(12_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(12_000.00))
                    .withGrossSalary(monthlyOf(1_000.00))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(916.67))
                      .withTaxableIncome(monthlyOf(83.33))
                      .withTaxPayable(monthlyOf(16.67))
                      .build()
                    )
                    .withNIContributions(monthlyOf(39.40))
                    .build()
            },
            {
                annualSalaryOf(40_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(40_000.00))
                    .withGrossSalary(monthlyOf(3_333.33))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(916.67))
                      .withTaxableIncome(monthlyOf(2_416.67))
                      .withTaxPayable(monthlyOf(483.33))
                      .build()
                    )
                    .withNIContributions(monthlyOf(319.40))
                    .build()
            },
            {
                annualSalaryOf(43_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(43_000.00))
                    .withGrossSalary(monthlyOf(3_583.33))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(916.67))
                      .withTaxableIncome(monthlyOf(2_666.67))
                      .withTaxPayable(monthlyOf(533.33))
                      .build()
                    )
                    .withNIContributions(monthlyOf(349.40))
                    .build()
            },
            {
                annualSalaryOf(60_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(60_000.00))
                    .withGrossSalary(monthlyOf(5_000.00))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(916.67))
                      .withTaxableIncome(monthlyOf(4_083.33))
                      .withTaxPayable(monthlyOf(1_100.00))
                      .build()
                    )
                    .withNIContributions(monthlyOf(377.73))
                    .build()
            },
            {
                annualSalaryOf(100_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(100_000.00))
                    .withGrossSalary(monthlyOf(8_333.33))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(916.67))
                      .withTaxableIncome(monthlyOf(7_416.67))
                      .withTaxPayable(monthlyOf(2_433.33))
                      .build()
                    )
                    .withNIContributions(monthlyOf(444.40))
                    .build()
            },
            {
                annualSalaryOf(105_500.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(105_500.00))
                    .withGrossSalary(monthlyOf(8_791.67))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(687.50))
                      .withTaxableIncome(monthlyOf(8_104.17))
                      .withTaxPayable(monthlyOf(2_708.33))
                      .build()
                    )
                    .withNIContributions(monthlyOf(453.57))
                    .build()
            },
            {
                annualSalaryOf(111_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(111_000.00))
                    .withGrossSalary(monthlyOf(9_250.00))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(458.33))
                      .withTaxableIncome(monthlyOf(8_791.67))
                      .withTaxPayable(monthlyOf(2_983.33))
                      .build()
                    )
                    .withNIContributions(monthlyOf(462.73))
                    .build()
            },
            {
                annualSalaryOf(122_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(122_000.00))
                    .withGrossSalary(monthlyOf(10_166.67))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(0.00))
                      .withTaxableIncome(monthlyOf(10_166.67))
                      .withTaxPayable(monthlyOf(3_533.33))
                      .build()
                    )
                    .withNIContributions(monthlyOf(481.07))
                    .build()
            },
            {
                annualSalaryOf(150_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(150_000.00))
                    .withGrossSalary(monthlyOf(12_500.00))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(0.00))
                      .withTaxableIncome(monthlyOf(12_500.00))
                      .withTaxPayable(monthlyOf(4_466.67))
                      .build()
                    )
                    .withNIContributions(monthlyOf(527.73))
                    .build()
            },
            {
                annualSalaryOf(160_000.00),
                aSalarySlip()
                    .withEmployee(employeeWithAnnualSalaryOf(160_000.00))
                    .withGrossSalary(monthlyOf(13_333.33))
                    .withTaxDetails(aTaxDetails()
                      .withTaxFreeAllowance(monthlyOf(0.00))
                      .withTaxableIncome(monthlyOf(13_333.33))
                      .withTaxPayable(monthlyOf(4_841.67))
                      .build()
                    )
                    .withNIContributions(monthlyOf(544.40))
                    .build()
            }
        }
    );
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Employee employeeWithAnnualSalaryOf(double annualSalary) {
    return createEmployeeWith(new Money(annualSalary)).build();
  }

  private static Money monthlyOf(double amount) {
    return new Money(amount);
  }

  @Test public void
  return_a_monthly_salary_slip_of_an_employee_with_a_given_annual_salary() {
    Employee employee = createEmployeeWith(annualSalary).build();
    assertThat(
        salarySlipGenerator.generateFor(employee),
        is(salarySlip)
    );
  }
}
