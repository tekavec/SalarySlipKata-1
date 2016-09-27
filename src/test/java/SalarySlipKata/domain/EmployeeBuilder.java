package SalarySlipKata.domain;

public class EmployeeBuilder {
  private int id;
  private String name;
  private Money annualSalary;

  public EmployeeBuilder withId(int id) {
    this.id = id;
    return this;
  }

  public EmployeeBuilder withName(String name) {
    this.name = name;
    return this;
  }

  public EmployeeBuilder withAnnualSalary(Money annualSalary) {
    this.annualSalary = annualSalary;
    return this;
  }

  public static EmployeeBuilder aEmployee() {
    return new EmployeeBuilder();
  }

  public Employee build() {
    return new Employee(id, name, annualSalary);
  }
}
