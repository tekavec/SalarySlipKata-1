package SalarySlipKata;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain.EmployeeId;

public class TestConstants {
  public static final EmployeeId EMPLOYEE_ID_12345 = new EmployeeId(12345);
  public static final String EMPLOYEE_NAME = "John J Doe";
  public static final int ANNUAL_SALARY = 24000;
  public static final Employee TEST_EMPLOYEE = new Employee(EMPLOYEE_ID_12345, EMPLOYEE_NAME, ANNUAL_SALARY);
  public static final String SALARY_PERIOD = "Sep 2016";
  public static final String SALARY_SLIP_PRINT_DATE_AS_STRING = "01 Sep 2016";
}
