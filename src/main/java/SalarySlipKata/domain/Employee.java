package SalarySlipKata.domain;

import static java.lang.String.format;

import java.util.Objects;

public class Employee {
  private int id;
  private String name;
  private Money annualSalary;

  public Employee(int id, String name, Money annualSalary) {
    this.id = id;
    this.name = name;
    this.annualSalary = annualSalary;
  }

  public Money annualSalary() {
    return new Money(annualSalary);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Employee employee = (Employee) o;
    return id == employee.id &&
        Objects.equals(name, employee.name) &&
        Objects.equals(annualSalary, employee.annualSalary);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, annualSalary);
  }

  @Override
  public String toString() {
    return format("id=%d, name='%s', annualSalary=%s", id, name, annualSalary);
  }
}
