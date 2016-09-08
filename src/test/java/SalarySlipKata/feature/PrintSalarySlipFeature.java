package SalarySlipKata.feature;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.application_service.SalarySlipApplication;
import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.EmployeeId;
import SalarySlipKata.infrastructure.Clock;
import SalarySlipKata.infrastructure.Console;
import SalarySlipKata.infrastructure.InMemoryEmployeeRepository;
import SalarySlipKata.infrastructure.SalarySlipPrinter;

public class PrintSalarySlipFeature {
  private static final EmployeeId EMPLOYEE_ID_12345 = new EmployeeId(12345);

  private Console console;
  private SalarySlipApplication salarySlipApplication;
  private InMemoryEmployeeRepository inMemoryEmployeeRepository;
  private SalarySlipPrinter salarySlipPrinter;
  private Clock clock;

  @Before
  public void initialise() {
    inMemoryEmployeeRepository = new InMemoryEmployeeRepository();
    console = mock(Console.class);
    clock = mock(Clock.class);
    salarySlipPrinter = new SalarySlipPrinter(clock, console);

    salarySlipApplication = new SalarySlipApplication(inMemoryEmployeeRepository, salarySlipPrinter);
  }

  @Test public void
    print_a_salary_slip_with_employee_details_on_it() {
      when(clock.todayAsString()).thenReturn("01 Sep 2016");
      inMemoryEmployeeRepository.add(EMPLOYEE_ID_12345, new Employee(EMPLOYEE_ID_12345, "John J Doe", 24000));

      salarySlipApplication.printFor(EMPLOYEE_ID_12345, "Sep 2016");

      verify(console).print(
          "Date: 01 Sep 2016             Salary for period: Sep 2016\n" +
          "                                                         \n" +
          "Employee ID: 12345            Employee Name: John J Doe  \n" +
          "                                                         \n" +
          "EARNINGS                                                 \n" +
          "Basic            £2000.00                                \n"
      );
    } 
}
