package SalarySlipKata;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import SalarySlipKata.domain.Employee;
import SalarySlipKata.domain_service.NationalInsuranceService;
import SalarySlipKata.domain_service.SalaryService;
import SalarySlipKata.domain_service.TaxService;

public class SalarySlipApplicationShould {

  private List<String> salarySlip;
  private SalarySlipApplication salarySlipApplication;
  private SalaryService salaryService;
  private NationalInsuranceService nationalInsuranceService;
  private TaxService taxService;

  @Before
  public void setUp() throws Exception {
    salarySlip = new ArrayList<>();

    nationalInsuranceService = new NationalInsuranceService();
    taxService = new TaxService();
    salaryService = new SalaryService(nationalInsuranceService, taxService);

    salarySlipApplication = new SalarySlipApplication(salaryService);
  }

  @Test public void
  print_salary_slip_of_an_employee_with_salary_details_in_it() {
    salarySlip = salarySlipApplication.generateFor(new Employee(12345, "John J Doe", 24000));

    assertThat(salarySlip, equalTo(
        new ArrayList<>(
            asList("Employee ID: 12345\n",
                "Employee Name: John J Doe\n",
                "Gross Salary: £2000.00\n",
                "Tax-free allowance: £916.67\n",
                "Taxable income: £1083.33\n",
                "National Insurance contributions: £159.40\n",
                "Tax Payable: £216.67\n",
                "Net Payable: £1623.93\n"
            )
        )
      )
    );
  } 
}
