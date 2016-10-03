package org.neomatrix369.salaryslip.tax;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import static java.util.Arrays.asList;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.neomatrix369.salaryslip.components.Money;
import org.neomatrix369.salaryslip.personal_allowance.PersonalAllowanceCalculator;

@RunWith(Parameterized.class)
public class HigherTaxWithPersonalAllowanceReductionRuleBandShould {
  private Money annualSalary;
  private Money expectedAnnualHigherTaxPayable;

  private PersonalAllowanceCalculator personalAllowanceCalculator = new PersonalAllowanceCalculator();
  private HigherTaxWithPersonalAllowanceReductionRuleBand higherTaxWithPersonalAllowanceReductionRuleBand;

  @Parameterized.Parameters(name = "For an annual salary of {0}, the annual higher tax payable is {1}")
  public static Collection<Object[]> data() {
    return asList(
        new Object[][] {
            { annualSalaryOf( 40_000.00), expectedAnnualHigherTaxPayableOf(     0.00) },
            { annualSalaryOf( 43_000.00), expectedAnnualHigherTaxPayableOf(     0.00) },
            { annualSalaryOf( 50_000.00), expectedAnnualHigherTaxPayableOf( 2_800.00) },
            { annualSalaryOf(100_000.00), expectedAnnualHigherTaxPayableOf(22_800.00) },
            { annualSalaryOf(111_000.00), expectedAnnualHigherTaxPayableOf(29_400.00) },
            { annualSalaryOf(122_000.00), expectedAnnualHigherTaxPayableOf(36_000.00) },
            { annualSalaryOf(150_000.00), expectedAnnualHigherTaxPayableOf(47_200.00) },
            { annualSalaryOf(160_000.00), expectedAnnualHigherTaxPayableOf(47_200.00) }
        }
    );
  }

  @Before
  public void initialise() {
    higherTaxWithPersonalAllowanceReductionRuleBand =
          new HigherTaxWithPersonalAllowanceReductionRuleBand(
              new Money(43_000.00), new Money(150_000.00), 0.40, personalAllowanceCalculator);
  }

  private static Money annualSalaryOf(double amount) {
    return new Money(amount);
  }

  private static Money expectedAnnualHigherTaxPayableOf(double amount) {
    return new Money(amount);
  }

  public HigherTaxWithPersonalAllowanceReductionRuleBandShould(
      Money annualSalary,
      Money expectedAnnualHigherTaxPayable) {
    this.annualSalary = annualSalary;
    this.expectedAnnualHigherTaxPayable = expectedAnnualHigherTaxPayable;
  }

  @Test public void
  return_the_annual_higher_tax_payable_for_an_annual_salary_applicable_for_higher_tax() {
    Money actualAnnualHigherTaxPayable =
        higherTaxWithPersonalAllowanceReductionRuleBand.calculateTaxFrom(annualSalary);
    assertThat(actualAnnualHigherTaxPayable, is(expectedAnnualHigherTaxPayable));
  } 
}
