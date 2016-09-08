package SalarySlipKata.feature;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static SalarySlipKata.TestConstants.ANNUAL_SALARY;
import static SalarySlipKata.TestConstants.EMPLOYEE_ID_12345;
import static SalarySlipKata.TestConstants.EMPLOYEE_NAME;
import static SalarySlipKata.TestConstants.SALARY_PERIOD;
import static SalarySlipKata.TestConstants.SALARY_SLIP_PRINT_DATE_AS_STRING;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.application_service.SalarySlipApplication;
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

    inMemoryEmployeeRepository = new InMemoryEmployeeRepository();
    salarySlipPrinter = new SalarySlipPrinter(clock, console);

    salarySlipApplication = new SalarySlipApplication(inMemoryEmployeeRepository, salarySlipPrinter);
  }

  @Test public void
  print_an_employee_salary_slip() {
    when(clock.todayAsString()).thenReturn(SALARY_SLIP_PRINT_DATE_AS_STRING);
    inMemoryEmployeeRepository.add(EMPLOYEE_ID_12345, EMPLOYEE_NAME, ANNUAL_SALARY);

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
