package org.neomatrix369.salaryslip.components;

import static java.lang.String.*;

import java.util.Objects;

import org.neomatrix369.salaryslip.tax.TaxDetails;

public class SalarySlip {
  private final Employee employee;
  private final Money grossSalary;
  private final Money niContributions;
  private final TaxDetails taxDetails;
  private final Money netPayable;

  public SalarySlip(
      Employee employee,
      Money grossSalary,
      Money niContributions,
      TaxDetails taxDetails) {
    this.employee = employee;
    this.grossSalary = grossSalary;
    this.niContributions = niContributions;
    this.taxDetails = taxDetails;
    this.netPayable = grossSalary
                        .subtract(niContributions)
                        .subtract(taxDetails.taxPayable());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SalarySlip that = (SalarySlip) o;
    return Objects.equals(employee, that.employee) &&
        Objects.equals(grossSalary, that.grossSalary) &&
        Objects.equals(niContributions, that.niContributions) &&
        Objects.equals(taxDetails, that.taxDetails) &&
        Objects.equals(netPayable, that.netPayable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employee, grossSalary, niContributions, taxDetails, netPayable);
  }

  @Override
  public String toString() {
    return format("employee=%s\n grossSalary=%s\n niContributions=%s\n taxDetails=%s\n netPayable=%s}",
        employee, grossSalary, niContributions, taxDetails, netPayable);
  }
}
