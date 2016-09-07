package SalarySlipKata;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.EmployeeId;
import SalarySlipKata.infrastructure.InMemoryEmployeeRepository;
import SalarySlipKata.infrastructure.StandardSalaryPrinter;

public class SalarySlipServiceShould {
  private static final EmployeeId EMPLOYEE_ID_12345 = new EmployeeId(12345);
  private InMemoryEmployeeRepository inMemoryEmployeeRepository;
  private StandardSalaryPrinter standardSalarySlipPrinter;
  private SalarySlipService salarySlipService;

  @Before
  public void setUp() throws Exception {
    inMemoryEmployeeRepository = mock(InMemoryEmployeeRepository.class);
    standardSalarySlipPrinter = mock(StandardSalaryPrinter.class);
    salarySlipService = new SalarySlipService(inMemoryEmployeeRepository, standardSalarySlipPrinter);
  }

  @Test public void
  print_a_salary_slip() {
    Employee employee = new Employee(EMPLOYEE_ID_12345, "John J Doe", 24000);
    when(inMemoryEmployeeRepository.get(EMPLOYEE_ID_12345)).thenReturn(employee);

    salarySlipService.printFor(EMPLOYEE_ID_12345, "Sep 2016");

    verify(standardSalarySlipPrinter).print(employee, "Sep 2016");
  }  
}
