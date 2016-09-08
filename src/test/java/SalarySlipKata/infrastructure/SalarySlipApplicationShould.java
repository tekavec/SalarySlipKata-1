package SalarySlipKata.infrastructure;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.application_service.SalarySlipApplication;
import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.EmployeeId;

public class SalarySlipApplicationShould {
  private static final EmployeeId EMPLOYEE_ID_12345 = new EmployeeId(12345);

  private SalarySlipPrinter salarySlipPrinter;
  private SalarySlipApplication salarySlipApplication;
  private InMemoryEmployeeRepository inMemoryEmployeeRepository;

  @Before
  public void initialise() {
    inMemoryEmployeeRepository = mock(InMemoryEmployeeRepository.class);
    salarySlipPrinter = mock(SalarySlipPrinter.class);

    salarySlipApplication = new SalarySlipApplication(inMemoryEmployeeRepository, salarySlipPrinter);
  }

  @Test public void
  print_salary_slip() {
    Employee employee = new Employee(EMPLOYEE_ID_12345, "John J Doe", 24000);
    when(inMemoryEmployeeRepository.get(EMPLOYEE_ID_12345)).thenReturn(employee);

    salarySlipApplication.printFor(EMPLOYEE_ID_12345, "Sep 2016");

    verify(salarySlipPrinter).print(employee, "Sep 2016");
  } 

}
