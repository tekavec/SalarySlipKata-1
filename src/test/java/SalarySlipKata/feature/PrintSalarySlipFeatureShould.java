package SalarySlipKata.feature;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static SalarySlipKata.TestConstants.EMPLOYEE_ID_12345;
import static SalarySlipKata.TestConstants.SALARY_PERIOD;
import static SalarySlipKata.TestConstants.SALARY_SLIP_PRINT_DATE;
import static SalarySlipKata.TestConstants.TEST_EMPLOYEE;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.SalarySlipApplication;
import SalarySlipKata.infrastructure.Clock;
import SalarySlipKata.infrastructure.Console;
import SalarySlipKata.infrastructure.InMemoryEmployeeRepository;
import SalarySlipKata.infrastructure.SalarySlipPrinter;

public class PrintSalarySlipFeatureShould {

  private Console console;
  private SalarySlipApplication salarySlipApplication;
  private InMemoryEmployeeRepository inMemoryEmployeeRepository;
  private SalarySlipPrinter salarySlipPrinter;
  private Clock clock;

  @Before
  public void setUp() throws Exception {
    console = mock(Console.class);
    clock = mock(Clock.class);
    salarySlipPrinter = new SalarySlipPrinter(clock, console);
    inMemoryEmployeeRepository = new InMemoryEmployeeRepository();
    salarySlipApplication = new SalarySlipApplication(inMemoryEmployeeRepository, salarySlipPrinter);
  }

  @Test public void
  print_salary_slip_of_an_employee() {
    when(clock.todayAsString()).thenReturn(SALARY_SLIP_PRINT_DATE);
    inMemoryEmployeeRepository.add(EMPLOYEE_ID_12345, TEST_EMPLOYEE);

    salarySlipApplication.printFor(EMPLOYEE_ID_12345, SALARY_PERIOD);

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
