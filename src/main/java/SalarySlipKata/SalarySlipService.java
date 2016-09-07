package SalarySlipKata;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.EmployeeId;
import SalarySlipKata.infrastructure.InMemoryEmployeeRepository;
import SalarySlipKata.infrastructure.StandardSalaryPrinter;

public class SalarySlipService {

  private StandardSalaryPrinter standardSalarySlipPrinter;
  private InMemoryEmployeeRepository inMemoryEmployeeRepository;

  public SalarySlipService(InMemoryEmployeeRepository inMemoryEmployeeRepository, StandardSalaryPrinter standardSalarySlipPrinter) {
    this.inMemoryEmployeeRepository = inMemoryEmployeeRepository;
    this.standardSalarySlipPrinter = standardSalarySlipPrinter;
  }

  public void printFor(EmployeeId employeeId, String salaryPeriod) {
    Employee employee = inMemoryEmployeeRepository.get(employeeId);
    standardSalarySlipPrinter.print(employee, salaryPeriod);
  }
}
