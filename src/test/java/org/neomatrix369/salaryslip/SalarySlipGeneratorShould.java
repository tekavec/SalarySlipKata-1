package org.neomatrix369.salaryslip;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.neomatrix369.salaryslip.components.Employee;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.components.SalarySlip;
import org.neomatrix369.salaryslip.tax.TaxDetails;

@RunWith(Parameterized.class)
public class SalarySlipGeneratorShould {

  private SalarySlipGenerator salarySlipGenerator;

  private double annualSalary;
  private SalarySlip expectedSalarySlip;

  @Before
  public void initialise() {
    salarySlipGenerator = new SalarySlipGenerator();
  }

  @Parameterized.Parameters(name="For an annual salary of £{0}, the employee salary slip should look like {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            {
                    5_000.00,
                    createSalarySlip(
                        createEmployee(5_000.00),
                        withMonthlyGrossSalaryOf(416.67),
                        withMonthlyNIContributions(0.00),
                        createTaxDetailsWith(
                          withMonthlyTaxFreeAllowance(916.67),
                          withMonthlyTaxableIncome(0.00),
                          withMonthlyTaxPayable(0.00)
                        )
                    )
            },
            {
                    8_060.00,
                    createSalarySlip(
                        createEmployee(8_060.00),
                        withMonthlyGrossSalaryOf(671.67),
                        withMonthlyNIContributions(0.00),
                        createTaxDetailsWith(
                          withMonthlyTaxFreeAllowance(916.67),
                          withMonthlyTaxableIncome(0.00),
                          withMonthlyTaxPayable(0.00)
                        )
                    )
            },
            {
                    9_060.00,
                    createSalarySlip(
                        createEmployee(9_060.00),
                        withMonthlyGrossSalaryOf(755.00),
                        withMonthlyNIContributions(10.00),
                        createTaxDetailsWith(
                          withMonthlyTaxFreeAllowance(916.67),
                          withMonthlyTaxableIncome(0.00),
                          withMonthlyTaxPayable(0.00)
                        )
                    )
            },
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

  private static SalarySlip createSalarySlip(
      Employee employee,
      Money grossSalary,
      Money niContributions,
      TaxDetails taxDetails) {
      return new SalarySlip(
          employee,
          grossSalary,
          niContributions,
          taxDetails
      );
  }

  private static TaxDetails createTaxDetailsWith(
      Money taxFreeAllowance,
      Money taxableIncome,
      Money taxPayable) {
    return new TaxDetails(
        taxFreeAllowance,
        taxableIncome,
        taxPayable
    );
  }

  private static Employee createEmployee(double annualSalary) {
    return new Employee(12345, "John J Doe", new Money(annualSalary));
  }

  private static Money withMonthlyGrossSalaryOf(double salary) {
    return new Money(salary);
  }

  private static Money withMonthlyNIContributions(double amount) {
    return new Money(amount);
  }

  private static Money withMonthlyTaxFreeAllowance(double amount) {
    return new Money(amount);
  }

  private static Money withMonthlyTaxableIncome(double amount) {
    return new Money(amount);
  }

  private static Money withMonthlyTaxPayable(double amount) {
    return new Money(amount);
  }
}
