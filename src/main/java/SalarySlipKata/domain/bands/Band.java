package SalarySlipKata.domain.bands;

import SalarySlipKata.domain.Money;

public interface Band {
  Money lowerLimit();

  Money upperLimit();

  double rate();

  Money calculateFrom(Money annualSalary);
}
