package org.neomatrix369.salaryslip.components;

import static java.lang.String.*;

import java.util.Objects;

public class SalarySlip {
  private final Employee employee;
  private final Money grossSalary;
  private final Money nationalInsuranceContributions;
  private final TaxDetails taxDetails;

  public SalarySlip(Employee employee, Money grossSalary, Money nationalInsuranceContributions, TaxDetails taxDetails) {
    this.employee = employee;
    this.grossSalary = grossSalary;
    this.nationalInsuranceContributions = nationalInsuranceContributions;
    this.taxDetails = taxDetails;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SalarySlip that = (SalarySlip) o;
    return Objects.equals(employee, that.employee) &&
        Objects.equals(grossSalary, that.grossSalary) &&
        Objects.equals(nationalInsuranceContributions, that.nationalInsuranceContributions) &&
        Objects.equals(taxDetails, that.taxDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employee, grossSalary, nationalInsuranceContributions, taxDetails);
  }

  @Override
  public String toString() {
    return format("employee=%s \n grossSalary=%s \n nationalInsuranceContributions=%s \n taxDetails=%s",
        employee, grossSalary, nationalInsuranceContributions, taxDetails);
  }
}
