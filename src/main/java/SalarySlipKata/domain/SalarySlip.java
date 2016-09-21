package SalarySlipKata.domain;

import static java.lang.String.*;

import java.util.Objects;

public class SalarySlip {
  private final Employee employee;
  private final GBP grossSalary;
  private TaxDetails taxDetails;
  private final GBP niContributions;
  private final GBP netPayable;

  public SalarySlip(Employee employee, GBP grossSalary, TaxDetails taxDetails,
      GBP niContributions, GBP netPayable) {
    this.employee = employee;
    this.grossSalary = grossSalary;
    this.taxDetails = taxDetails;
    this.niContributions = niContributions;
    this.netPayable = netPayable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SalarySlip that = (SalarySlip) o;
    return Objects.equals(employee, that.employee) &&
        Objects.equals(grossSalary, that.grossSalary) &&
        Objects.equals(taxDetails, that.taxDetails) &&
        Objects.equals(niContributions, that.niContributions) &&
        Objects.equals(netPayable, that.netPayable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employee, grossSalary, taxDetails, niContributions, netPayable);
  }

  @Override
  public String toString() {
    return format("employee=%s\n grossSalary=%s\n taxDetails=%s\n niContributions=%s\n netPayable=%s",
        employee, grossSalary, taxDetails, niContributions, netPayable);
  }
}
