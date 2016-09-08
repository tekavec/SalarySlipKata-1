package SalarySlipKata.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static SalarySlipKata.TestConstants.ANNUAL_SALARY;
import static SalarySlipKata.TestConstants.TEST_EMPLOYEE;
import static SalarySlipKata.TestConstants.EMPLOYEE_ID_12345;
import static SalarySlipKata.TestConstants.EMPLOYEE_NAME;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.domain.Employee;

public class InMemoryEmployeeRepositoryShould {
  private InMemoryEmployeeRepository inMemoryEmployeeRepository;

  @Before
  public void setUp() throws Exception {
    inMemoryEmployeeRepository = new InMemoryEmployeeRepository();
  }

  @Test public void
  store_an_employee() {
    inMemoryEmployeeRepository.add(EMPLOYEE_ID_12345, EMPLOYEE_NAME, ANNUAL_SALARY);

    Employee actualEmployee = inMemoryEmployeeRepository.get(EMPLOYEE_ID_12345);
    assertThat(actualEmployee, is(TEST_EMPLOYEE));
  } 

}
