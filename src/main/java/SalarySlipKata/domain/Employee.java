package SalarySlipKata.domain;

public class Employee {
  private int id;
  private String name;
  private int annualSalary;

  public Employee(int id, String name, int annualSalary) {
    this.id = id;
    this.name = name;
    this.annualSalary = annualSalary;
  }

  public int id() {
    return id;
  }

  public String name() {
    return name;
  }

  public int annualSalary() {
    return annualSalary;
  }
}
