package SalarySlipKata.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.EmployeeId;

public class InMemoryEmployeeRepositoryShould {
  private static final EmployeeId EMPLOYEE_ID_12345 = new EmployeeId(12345);
  private static final String EMPLOYEE_NAME = "XXXX";
  private static final int ANNUAL_SALARY = 12345;

  private InMemoryEmployeeRepository inMemoryEmployeeRepository;

  @Before
  public void initialise() {
    inMemoryEmployeeRepository = new InMemoryEmployeeRepository();
  }

  @Test public void
  add_employee() {
    Employee expectedEmployee = new Employee(EMPLOYEE_ID_12345, EMPLOYEE_NAME, ANNUAL_SALARY);

    inMemoryEmployeeRepository.add(EMPLOYEE_ID_12345, EMPLOYEE_NAME, ANNUAL_SALARY);

    assertThat(inMemoryEmployeeRepository.get(EMPLOYEE_ID_12345), is(expectedEmployee));
  } 
}
