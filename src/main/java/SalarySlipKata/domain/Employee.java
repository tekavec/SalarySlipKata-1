package SalarySlipKata.domain;

public class Employee {
  private final EmployeeId id;
  private final String name;
  private final int annualSalary;

  public Employee(EmployeeId id, String name, int annualSalary) {
    this.id = id;
    this.name = name;
    this.annualSalary = annualSalary;
  }

  public EmployeeId id() {
    return id;
  }

  public String name() {
    return name;
  }

  public int annualSalary() {
    return annualSalary;
  }


}
