package org.somename.salaryslip;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.somename.salaryslip.components.Employee;
import org.somename.salaryslip.components.Money;
import org.somename.salaryslip.components.SalarySlip;
import org.somename.salaryslip.personal_allowance.PersonalAllowanceCalculator;
import org.somename.salaryslip.tax.TaxDetails;

@RunWith(Parameterized.class)
public class SalarySlipGeneratorShould {

  private double annualSalary;
  private SalarySlip expectedMonthlySalarySlip;

  private SalarySlipGenerator salarySlipGenerator;

  public SalarySlipGeneratorShould(double annualSalary, SalarySlip expectedMonthlySalarySlip) {
    this.annualSalary = annualSalary;
    this.expectedMonthlySalarySlip = expectedMonthlySalarySlip;
  }

  @Parameters (name = "For an annual salary of {0}, the monthly salary slip looks like {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                annualSalaryOf(5_000.00),
                    createSalarySlip(
                        createEmployeeWith(5_000.00),
                        monthlyGrossSalaryOf(416.67),
                        monthlyNIContributionsOf(0.00),
                        monthlyTaxDetailsOf(
                            monthlyTaxFreeAllowanceOf(916.67),
                            monthlyTaxableIncomeOf(0.00),
                            monthlyTaxablePayableOf(0.00)
                        )
                    )
            },
            {
                annualSalaryOf(8_060.00),
                createSalarySlip(
                    createEmployeeWith(8_060.00),
                    monthlyGrossSalaryOf(671.67),
                    monthlyNIContributionsOf(0.00),
                    monthlyTaxDetailsOf(
                        monthlyTaxFreeAllowanceOf(916.67),
                        monthlyTaxableIncomeOf(0.00),
                        monthlyTaxablePayableOf(0.00)
                    )
                )
            },
            {
                annualSalaryOf(9_060.00),
                createSalarySlip(
                    createEmployeeWith(9_060.00),
                    monthlyGrossSalaryOf(755.00),
                    monthlyNIContributionsOf(10.00),
                    monthlyTaxDetailsOf(
                        monthlyTaxFreeAllowanceOf(916.67),
                        monthlyTaxableIncomeOf(0.00),
                        monthlyTaxablePayableOf(0.00)
                    )
                )
            },
            {
                annualSalaryOf(40_000.00),
                createSalarySlip(
                    createEmployeeWith(40_000.00),
                    monthlyGrossSalaryOf(3_333.33),
                    monthlyNIContributionsOf(319.40),
                    monthlyTaxDetailsOf(
                        monthlyTaxFreeAllowanceOf(916.67),
                        monthlyTaxableIncomeOf(2_416.67),
                        monthlyTaxablePayableOf(483.33)
                    )
                )
            },
            {
                annualSalaryOf(45_000.00),
                createSalarySlip(
                    createEmployeeWith(45_000.00),
                    monthlyGrossSalaryOf(3_750.00),
                    monthlyNIContributionsOf(352.73),
                    monthlyTaxDetailsOf(
                        monthlyTaxFreeAllowanceOf(916.67),
                        monthlyTaxableIncomeOf(2_833.33),
                        monthlyTaxablePayableOf(600.00)
                    )
                )
            },
            {
                annualSalaryOf(101_000.00),
                createSalarySlip(
                    createEmployeeWith(101_000.00),
                    monthlyGrossSalaryOf(8_416.67),
                    monthlyNIContributionsOf(446.07),
                    monthlyTaxDetailsOf(
                        monthlyTaxFreeAllowanceOf(875.00),
                        monthlyTaxableIncomeOf(7_541.67),
                        monthlyTaxablePayableOf(2_483.33)
                    )
                )
            },
            {
                annualSalaryOf(105_500.00),
                createSalarySlip(
                    createEmployeeWith(105_500.00),
                    monthlyGrossSalaryOf(8_791.67),
                    monthlyNIContributionsOf(453.57),
                    monthlyTaxDetailsOf(
                        monthlyTaxFreeAllowanceOf(687.50),
                        monthlyTaxableIncomeOf(8_104.17),
                        monthlyTaxablePayableOf(2_708.33)
                    )
                )
            },
            {
                annualSalaryOf(111_000.00),
                createSalarySlip(
                    createEmployeeWith(111_000.00),
                    monthlyGrossSalaryOf(9_250.00),
                    monthlyNIContributionsOf(462.73),
                    monthlyTaxDetailsOf(
                        monthlyTaxFreeAllowanceOf(458.33),
                        monthlyTaxableIncomeOf(8_791.67),
                        monthlyTaxablePayableOf(2_983.33)
                    )
                )
            },
            {
                annualSalaryOf(122_000.00),
                createSalarySlip(
                    createEmployeeWith(122_000.00),
                    monthlyGrossSalaryOf(10_166.67),
                    monthlyNIContributionsOf(481.07),
                    monthlyTaxDetailsOf(
                        monthlyTaxFreeAllowanceOf(0.00),
                        monthlyTaxableIncomeOf(10_166.67),
                        monthlyTaxablePayableOf(3_533.33)
                    )
                )
            },
            {
                annualSalaryOf(150_000.00),
                createSalarySlip(
                    createEmployeeWith(150_000.00),
                    monthlyGrossSalaryOf(12_500.00),
                    monthlyNIContributionsOf(527.73),
                    monthlyTaxDetailsOf(
                        monthlyTaxFreeAllowanceOf(0.00),
                        monthlyTaxableIncomeOf(12_500.00),
                        monthlyTaxablePayableOf(4_466.67)
                    )
                )
            },
            {
                annualSalaryOf(160_000.00),
                createSalarySlip(
                    createEmployeeWith(160_000.00),
                    monthlyGrossSalaryOf(13_333.33),
                    monthlyNIContributionsOf(544.40),
                    monthlyTaxDetailsOf(
                        monthlyTaxFreeAllowanceOf(0.00),
                        monthlyTaxableIncomeOf(13_333.33),
                        monthlyTaxablePayableOf(4_841.67)
                    )
                )
            }
        }
    );
  }

  @Before
  public void setUp() throws Exception {
    salarySlipGenerator = new SalarySlipGenerator(new NationalInsuranceCalculator(), new TaxCalculator(new PersonalAllowanceCalculator()));
  }

  @Test public void
  generate_a_monthly_salary_slip_containing_salary_and_employee_details_from_the_given_annual_salary() {
    Employee employee = createEmployeeWith(annualSalary);

    assertThat(salarySlipGenerator.generateFor(employee), is(expectedMonthlySalarySlip));
  }

  private static double annualSalaryOf(double amount) {
    return amount;
  }

  private static SalarySlip createSalarySlip(
      Employee employee, Money grossSalary, Money niContributions, TaxDetails taxDetails) {
    return new SalarySlip(employee, grossSalary, niContributions, taxDetails);
  }

  private static Employee createEmployeeWith(double annualSalary) {
    return new Employee(12345, "John J Doe", new Money(annualSalary));
  }

  private static Money monthlyGrossSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money monthlyNIContributionsOf(double amount) {
    return new Money(amount);
  }

  private static TaxDetails monthlyTaxDetailsOf(
      Money taxFreeAllowance, Money taxableIncome, Money taxablePayable) {
    return new TaxDetails(taxFreeAllowance, taxableIncome, taxablePayable);
  }

  private static Money monthlyTaxFreeAllowanceOf(double amount) {
    return new Money(amount);
  }

  private static Money monthlyTaxableIncomeOf(double amount) {
    return new Money(amount);
  }

  private static Money monthlyTaxablePayableOf(double amount) {
    return new Money(amount);
  }

}
