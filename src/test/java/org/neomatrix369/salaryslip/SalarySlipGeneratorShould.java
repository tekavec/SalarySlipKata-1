package org.neomatrix369.salaryslip;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.neomatrix369.salaryslip.components.SalarySlipBuilder.aSalarySlip;
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
import org.neomatrix369.salaryslip.tax.PersonalAllowanceCalculator;
import org.neomatrix369.salaryslip.tax.TaxCalculator;

@RunWith(Parameterized.class)
public class SalarySlipGeneratorShould {

  private SalarySlipGenerator salarySlipGenerator;

  private double annualSalary;
  private SalarySlip expectedSalarySlip;

  @Before
  public void initialise() {
    final NationalInsuranceCalculator nationalInsuranceCalculator =
        new NationalInsuranceCalculator();
    final PersonalAllowanceCalculator personalAllowanceCalculator =
        new PersonalAllowanceCalculator();

    final TaxCalculator taxCalculator = new TaxCalculator(personalAllowanceCalculator);

    salarySlipGenerator = new SalarySlipGenerator(nationalInsuranceCalculator, taxCalculator);
  }

  @Parameterized.Parameters(name="For an annual salary of £{0}, the employee salary slip should look like {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                    5_000.00,
                    aSalarySlip()
                        .withEmployee(createEmployee(5_000.00))
                        .withGrossSalary(monthly(416.67))
                        .withNiContributions(monthly(0.00))
                        .withTaxDetails(
                            aTaxDetails()
                            .withTaxFreeAllowance(monthly(916.67))
                            .withTaxableIncome(monthly(0.00))
                            .withTaxPayable(monthly(0.00))
                            .build()
                        )
                        .build()
            },
            {
                    8_060.00,
                    aSalarySlip()
                        .withEmployee(createEmployee(8_060.00))
                        .withGrossSalary(monthly(671.67))
                        .withNiContributions(monthly(0.00))
                        .withTaxDetails(
                            aTaxDetails()
                            .withTaxFreeAllowance(monthly(916.67))
                            .withTaxableIncome(monthly(0.00))
                            .withTaxPayable(monthly(0.00))
                          .build()
                        )
                        .build()
            },
            {
                    9_060.00,
                    aSalarySlip()
                        .withEmployee(createEmployee(9_060.00))
                        .withGrossSalary(monthly(755.00))
                        .withNiContributions(monthly(10.00))
                        .withTaxDetails(
                            aTaxDetails()
                            .withTaxFreeAllowance(monthly(916.67))
                            .withTaxableIncome(monthly(0.00))
                            .withTaxPayable(monthly(0.00))
                          .build()
                        )
                        .build()
            },
            {
                    45_000.00,
                    aSalarySlip()
                        .withEmployee(createEmployee(45_000.00))
                        .withGrossSalary(monthly(3_750.00))
                        .withNiContributions(monthly(352.73))
                        .withTaxDetails(
                            aTaxDetails()
                            .withTaxFreeAllowance(monthly(916.67))
                            .withTaxableIncome(monthly(2_833.33))
                            .withTaxPayable(monthly(600.00))
                          .build()
                        )
                        .build()
            },
            {
                    101_000.00,
                    aSalarySlip()
                        .withEmployee(createEmployee(101_000.00))
                        .withGrossSalary(monthly(8_416.67))
                        .withNiContributions(monthly(446.07))
                        .withTaxDetails(
                            aTaxDetails()
                            .withTaxFreeAllowance(monthly(875.00))
                            .withTaxableIncome(monthly(7_541.67))
                            .withTaxPayable(monthly(2_483.33))
                          .build()
                        )
                        .build()
            }
        }
    );
  }

  public SalarySlipGeneratorShould(double annualSalary, SalarySlip expectedSalarySlip) {
    this.annualSalary = annualSalary;
    this.expectedSalarySlip = expectedSalarySlip;
  }

  @Test public void
  should_generate_a_salary_slip_with_employee_and_salary_details_for_a_given_annual_salary() {
    Employee employee = createEmployee(annualSalary);
    assertThat(salarySlipGenerator.generateFor(employee), is(expectedSalarySlip));
  }

  private static Employee createEmployee(double annualSalary) {
    return new Employee(12345, "John J Doe", new Money(annualSalary));
  }

  private static Money monthly(double amount) {
    return new Money(amount);
  }
}
