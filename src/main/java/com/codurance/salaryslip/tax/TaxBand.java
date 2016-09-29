package com.codurance.salaryslip.tax;

import com.codurance.salaryslip.components.Money;

public interface TaxBand {
  Money calculateFrom(Money annualSalary);

  Money calculateExcessFrom(Money upperLimit, Money lowerLimit);

  Money lowerLimit();

  Money upperLimit();

  double rate();
}
