package SalarySlipKata.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static SalarySlipKata.TestConstants.EMPLOYEE_ID_12345;
import static SalarySlipKata.TestConstants.TEST_EMPLOYEE;

import org.junit.Test;

import SalarySlipKata.domain.Employee;

public class InMemoryEmployeeRepositoryShould {
  private InMemoryEmployeeRepository inMemoryEmployeeRepository = new InMemoryEmployeeRepository();

  @Test public void
  store_an_employee_when_added() {
    inMemoryEmployeeRepository.add(EMPLOYEE_ID_12345, TEST_EMPLOYEE);

    Employee actualEmployee = inMemoryEmployeeRepository.get(EMPLOYEE_ID_12345);
    assertThat(actualEmployee, is(TEST_EMPLOYEE));
  } 
}
