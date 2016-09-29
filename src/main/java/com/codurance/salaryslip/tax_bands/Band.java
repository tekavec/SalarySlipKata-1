package com.codurance.salaryslip.tax_bands;

import com.codurance.salaryslip.components.Money;

public interface Band {
  Money lowerLimit();

  Money upperLimit();

  double rate();

  Money calculateFrom(Money annualSalary);

  Money calculateExcessFrom(Money upperLimit, Money lowerLimit);
}
