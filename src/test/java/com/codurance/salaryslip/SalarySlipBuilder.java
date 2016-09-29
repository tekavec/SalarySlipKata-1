package com.codurance.salaryslip;

import com.codurance.salaryslip.components.Employee;
import com.codurance.salaryslip.components.Money;
import com.codurance.salaryslip.components.SalarySlip;
import com.codurance.salaryslip.tax.TaxDetails;

public class SalarySlipBuilder {
  private Employee employee;
  private Money grossSalary;
  private TaxDetails taxDetails;
  private Money niContributions;
  private Money netPayable;

  private SalarySlipBuilder() {}

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

  public SalarySlipBuilder withTaxDetails(TaxDetails taxDetails) {
    this.taxDetails = taxDetails;
    return this;
  }

  public SalarySlipBuilder withNIContributions(Money niContributions) {
    this.niContributions = niContributions;
    return this;
  }

  public SalarySlipBuilder withNetPayable(Money netPayable) {
    this.netPayable = netPayable;
    return this;
  }

  public SalarySlip build() {
    return new SalarySlip(employee, grossSalary, taxDetails, niContributions, netPayable);
  }
}
