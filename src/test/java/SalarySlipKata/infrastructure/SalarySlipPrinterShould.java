package SalarySlipKata.infrastructure;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static SalarySlipKata.TestConstants.EMPLOYEE_ID_12345;
import static SalarySlipKata.TestConstants.SALARY_PERIOD;
import static SalarySlipKata.TestConstants.SALARY_SLIP_PRINT_DATE_AS_STRING;
import static SalarySlipKata.TestConstants.TEST_EMPLOYEE;

import org.junit.Before;
import org.junit.Test;

public class SalarySlipPrinterShould {
  private Console console;
  private SalarySlipPrinter salarySlipPrinter;
  private InMemoryEmployeeRepository inMemoryEmployeeRepository;
  private Clock clock;

  @Before
  public void setUp() throws Exception {
    console = mock(Console.class);
    clock = mock(Clock.class);
    inMemoryEmployeeRepository = mock(InMemoryEmployeeRepository.class);

    salarySlipPrinter = new SalarySlipPrinter(clock, console);
  }

  @Test public void
  print_a_salary_slip_to_the_console() {
    when(clock.todayAsString()).thenReturn(SALARY_SLIP_PRINT_DATE_AS_STRING);
    when(inMemoryEmployeeRepository.get(EMPLOYEE_ID_12345)).thenReturn(TEST_EMPLOYEE);

    salarySlipPrinter.print(TEST_EMPLOYEE, SALARY_PERIOD);

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
