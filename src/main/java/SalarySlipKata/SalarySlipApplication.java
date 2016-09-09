package SalarySlipKata;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.EmployeeId;
import SalarySlipKata.infrastructure.Clock;
import SalarySlipKata.infrastructure.Console;
import SalarySlipKata.infrastructure.InMemoryEmployeeRepository;
import SalarySlipKata.infrastructure.SalarySlipPrinter;

public class SalarySlipApplication {

  private SalarySlipPrinter salarySlipPrinter;
  private InMemoryEmployeeRepository inMemoryEmployeeRepository;

  public static void main(String[] args) {
    final EmployeeId employeeId = new EmployeeId(12345);
    InMemoryEmployeeRepository inMemoryEmployeeRepository = new InMemoryEmployeeRepository();
    inMemoryEmployeeRepository.add(employeeId, new Employee(employeeId, "John J Doe", 24000));

    Clock clock = new Clock();
    Console console = new Console();
    SalarySlipPrinter salarySlipPrinter = new SalarySlipPrinter(clock, console);
    final SalarySlipApplication salarySlipApplication = new SalarySlipApplication(inMemoryEmployeeRepository, salarySlipPrinter);

    salarySlipApplication.printFor(employeeId, "Sep 2016");
  }

  public SalarySlipApplication(
      InMemoryEmployeeRepository inMemoryEmployeeRepository,
      SalarySlipPrinter salarySlipPrinter) {
    this.inMemoryEmployeeRepository = inMemoryEmployeeRepository;
    this.salarySlipPrinter = salarySlipPrinter;
  }

  public void printFor(EmployeeId id, String salaryPeriod) {
    Employee employee = inMemoryEmployeeRepository.get(id);
    salarySlipPrinter.print(employee, salaryPeriod);
  }
}
