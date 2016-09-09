package SalarySlipKata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static SalarySlipKata.TestConstants.EMPLOYEE_ID_12345;
import static SalarySlipKata.TestConstants.SALARY_PERIOD;
import static SalarySlipKata.TestConstants.TEST_EMPLOYEE;

import org.junit.Test;

import SalarySlipKata.infrastructure.InMemoryEmployeeRepository;
import SalarySlipKata.infrastructure.SalarySlipPrinter;

public class SalarySlipApplicationShould {
  private SalarySlipPrinter salarySlipPrinter = mock(SalarySlipPrinter.class);
  private InMemoryEmployeeRepository inMemoryEmployeeRepository = mock(InMemoryEmployeeRepository.class);
  private SalarySlipApplication salarySlipApplication = new SalarySlipApplication(inMemoryEmployeeRepository, salarySlipPrinter);

  @Test public void
  print_a_salary_slip() {
    when(inMemoryEmployeeRepository.get(EMPLOYEE_ID_12345)).thenReturn(TEST_EMPLOYEE);

    salarySlipApplication.printFor(EMPLOYEE_ID_12345, SALARY_PERIOD);

    verify(salarySlipPrinter).print(TEST_EMPLOYEE, SALARY_PERIOD);
  } 
}
