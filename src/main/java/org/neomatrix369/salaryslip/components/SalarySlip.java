package org.neomatrix369.salaryslip.components;

import static java.lang.String.*;

import java.util.Objects;

import org.neomatrix369.salaryslip.tax.TaxDetails;

public class SalarySlip {
  private final Employee employee;
  private final Money grossSalary;
  private final Money niContributions;
  private final TaxDetails taxDetails;

  public SalarySlip(
      Employee employee,
      Money grossSalary,
      Money niContributions,
      TaxDetails taxDetails) {
    this.employee = employee;
    this.grossSalary = grossSalary;
    this.niContributions = niContributions;
    this.taxDetails = taxDetails;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SalarySlip that = (SalarySlip) o;
    return Objects.equals(employee, that.employee) &&
        Objects.equals(grossSalary, that.grossSalary) &&
        Objects.equals(niContributions, that.niContributions) &&
        Objects.equals(taxDetails, that.taxDetails);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employee, grossSalary, niContributions, taxDetails);
  }

  @Override
  public String toString() {
    return format("employee=%s\n grossSalary=%s\n niContributions=%s\n %s",
        employee, grossSalary, niContributions, taxDetails);
  }
}
