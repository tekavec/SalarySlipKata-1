package SalarySlipKata.feature;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.SalarySlipService;
import SalarySlipKata.domain.EmployeeId;
import SalarySlipKata.infrastructure.Clock;
import SalarySlipKata.infrastructure.Console;
import SalarySlipKata.infrastructure.InMemoryEmployeeRepository;
import SalarySlipKata.infrastructure.StandardSalaryPrinter;

public class PrintSalarySlipFeatureShould {

  private static final EmployeeId EMPLOYEE_ID_12345 = new EmployeeId(12345);

  private Console console;
  private SalarySlipService salarySlipService;
  private InMemoryEmployeeRepository inMemoryEmployeeRepository;
  private StandardSalaryPrinter standardSalarySlipPrinter;
  private Clock clock;

  @Before
  public void initialise() {
    console = mock(Console.class);
    clock = mock(Clock.class);

    inMemoryEmployeeRepository = new InMemoryEmployeeRepository();

    standardSalarySlipPrinter = new StandardSalaryPrinter(clock, console);
    salarySlipService = new SalarySlipService(inMemoryEmployeeRepository, standardSalarySlipPrinter);
  }

  @Test public void
    print_a_salary_slip_for_an_employee() {
      when(clock.todayAsString()).thenReturn("01 Sep 2016");
      inMemoryEmployeeRepository.add(EMPLOYEE_ID_12345, "John J Doe", 24000);

      salarySlipService.printFor(EMPLOYEE_ID_12345, "Sep 2016");

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
