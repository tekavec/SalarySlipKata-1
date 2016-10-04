package org.neomatrix369.salaryslip.components;

import org.neomatrix369.salaryslip.tax.TaxDetails;

public class SalarySlipBuilder {
  private Employee employee;
  private Money grossSalary;
  private Money niContributions;
  private TaxDetails taxDetails;

  public static SalarySlipBuilder aSalarySlip() {
    return new SalarySlipBuilder();
  }

  public SalarySlipBuilder withEmployee(Employee employee) {
    this.employee = employee;
    return this;
  }

  public SalarySlipBuilder withGrossSalary(Money grossSalary) {
    this.grossSalary = grossSalary;
    return this;
  }

  public SalarySlipBuilder withNiContributions(Money niContributions) {
    this.niContributions = niContributions;
    return this;
  }

  public SalarySlipBuilder withTaxDetails(TaxDetails taxDetails) {
    this.taxDetails = taxDetails;
    return this;
  }

  public SalarySlip build() {
    return new SalarySlip(employee, grossSalary, niContributions, taxDetails);
  }
}
