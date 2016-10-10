package org.neomatrix369.salaryslip.tax;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.neomatrix369.salaryslip.SalarySlipGenerator;
import org.neomatrix369.salaryslip.components.Employee;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.SalarySlip;
import org.neomatrix369.salaryslip.components.TaxDetails;
import org.neomatrix369.salaryslip.national_insurance.NationalInsuranceCalculator;

@RunWith(Parameterized.class)
public class SalarySlipGeneratorShould {

  private SalarySlipGenerator salarySlipGenerator;

  private double annualSalary;
  private SalarySlip expectedMonthlySalarySlip;

  private NationalInsuranceCalculator nationalInsuranceCalculator;
  private TaxCalculator taxCalculator;

  public SalarySlipGeneratorShould(double annualSalary, SalarySlip expectedMonthlySalarySlip) {
    this.annualSalary = annualSalary;
    this.expectedMonthlySalarySlip = expectedMonthlySalarySlip;
  }

  @Parameters(name = "For an annual salary of {0}, the monthly salary slip looks like {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                annualSalaryOf(5_000.0),
                expectedSalarySlip(
                  createEmployeeWith(5_000.00),
                  monthlyGrossSalary(416.67),
                  monthlyNationalInsuranceContributionsOf(0.00),
                  monthlyTaxDetailsOf(
                      taxFreeAllowanceOf(916.67),
                      taxableIncomeOf(0.00),
                      taxPayableOf(0.00)
                  )
                )
            },
            {
                annualSalaryOf(9_060.0),
                expectedSalarySlip(
                    createEmployeeWith(9_060.00),
                    monthlyGrossSalary(755.00),
                    monthlyNationalInsuranceContributionsOf(10.00),
                    monthlyTaxDetailsOf(
                        taxFreeAllowanceOf(916.67),
                        taxableIncomeOf(0.00),
                        taxPayableOf(0.00)
                    )
                )
            },
            {
                annualSalaryOf(40_000.00),
                expectedSalarySlip(
                    createEmployeeWith(40_000.00),
                    monthlyGrossSalary(3_333.33),
                    monthlyNationalInsuranceContributionsOf(319.40),
                    monthlyTaxDetailsOf(
                        taxFreeAllowanceOf(916.67),
                        taxableIncomeOf(2_416.67),
                        taxPayableOf(483.33)
                    )
                )
            },
            {
                annualSalaryOf(45_000.00),
                expectedSalarySlip(
                    createEmployeeWith(45_000.00),
                    monthlyGrossSalary(3_750.00),
                    monthlyNationalInsuranceContributionsOf(352.73),
                    monthlyTaxDetailsOf(
                        taxFreeAllowanceOf(916.67),
                        taxableIncomeOf(2_833.33),
                        taxPayableOf(600.00)
                    )
                )
            },
            {
                annualSalaryOf(105_500.00),
                expectedSalarySlip(
                    createEmployeeWith(105_500.00),
                    monthlyGrossSalary(8_791.67),
                    monthlyNationalInsuranceContributionsOf(453.57),
                    monthlyTaxDetailsOf(
                        taxFreeAllowanceOf(687.50),
                        taxableIncomeOf(8_104.17),
                        taxPayableOf(2_708.33)
                    )
                )
            },
            {
                annualSalaryOf(111_000.00),
                expectedSalarySlip(
                    createEmployeeWith(111_000.00),
                    monthlyGrossSalary(9_250.00),
                    monthlyNationalInsuranceContributionsOf(462.73),
                    monthlyTaxDetailsOf(
                        taxFreeAllowanceOf(458.33),
                        taxableIncomeOf(8_791.67),
                        taxPayableOf(2_983.33)
                    )
                )
            },
            {
                annualSalaryOf(122_000.00),
                expectedSalarySlip(
                    createEmployeeWith(122_000.00),
                    monthlyGrossSalary(10_166.67),
                    monthlyNationalInsuranceContributionsOf(481.07),
                    monthlyTaxDetailsOf(
                        taxFreeAllowanceOf(0.00),
                        taxableIncomeOf(10_166.67),
                        taxPayableOf(3_533.33)
                    )
                )
            },
            {
                annualSalaryOf(150_000.00),
                expectedSalarySlip(
                    createEmployeeWith(150_000.00),
                    monthlyGrossSalary(12_500.00),
                    monthlyNationalInsuranceContributionsOf(527.73),
                    monthlyTaxDetailsOf(
                        taxFreeAllowanceOf(0.00),
                        taxableIncomeOf(12_500.00),
                        taxPayableOf(4_466.67)
                    )
                )
            },
            {
                annualSalaryOf(160_000.00),
                expectedSalarySlip(
                    createEmployeeWith(160_000.00),
                    monthlyGrossSalary(13_333.33),
                    monthlyNationalInsuranceContributionsOf(544.40),
                    monthlyTaxDetailsOf(
                        taxFreeAllowanceOf(0.00),
                        taxableIncomeOf(13_333.33),
                        taxPayableOf(4_841.67)
                    )
                )
            }
        }
    );
  }

  private static SalarySlip expectedSalarySlip(
      Employee employee, Money grossSalary, Money nationalInsuranceContributions, TaxDetails taxDetails) {
    return new SalarySlip(
        employee,
        grossSalary,
        nationalInsuranceContributions,
        taxDetails
    );
  }

  @Before
  public void setUp() throws Exception {
    final PersonalAllowanceReductionCalculator personalAllowanceReductionCalculator =
        new PersonalAllowanceReductionCalculator();
    nationalInsuranceCalculator = new NationalInsuranceCalculator();
    taxCalculator = new TaxCalculator(personalAllowanceReductionCalculator);

    salarySlipGenerator = new SalarySlipGenerator(
        nationalInsuranceCalculator,
        taxCalculator
    );
  }

  @Test public void
      return_a_monthly_salary_slip_containing_salary_details_for_a_given_annual_salary() {
    Employee employee = createEmployeeWith(annualSalary);
    assertThat(salarySlipGenerator.generateFor(employee), is(expectedMonthlySalarySlip));
  }

  private static double annualSalaryOf(double amount) {
    return amount;
  }

  private static Employee createEmployeeWith(double annualSalary) {
    return new Employee(12345, "John J Doe", new Money(annualSalary));
  }

  private static Money monthlyGrossSalary(double amount) {
    return new Money(amount);
  }

  private static Money monthlyNationalInsuranceContributionsOf(double amount) {
    return new Money(amount);
  }

  private static TaxDetails monthlyTaxDetailsOf(Money taxFreeAllowance, Money taxableIncome, Money taxPayable) {
    return new TaxDetails(taxFreeAllowance, taxableIncome, taxPayable);
  }

  private static Money taxFreeAllowanceOf(double amount) {
    return new Money(amount);
  }

  private static Money taxableIncomeOf(double amount) {
    return new Money(amount);
  }

  private static Money taxPayableOf(double amount) {
    return new Money(amount);
  }
}
