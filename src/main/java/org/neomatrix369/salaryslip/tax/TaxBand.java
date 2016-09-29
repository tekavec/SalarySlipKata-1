package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public interface TaxBand {
  Money calculateFrom(Money annualSalary);

  Money calculateExcessFrom(Money upperLimit, Money lowerLimit);

  Money lowerLimit();

  Money upperLimit();

  double rate();
}
