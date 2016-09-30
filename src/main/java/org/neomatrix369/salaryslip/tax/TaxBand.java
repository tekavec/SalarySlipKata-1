package org.neomatrix369.salaryslip.tax;

import org.neomatrix369.salaryslip.components.Money;

public abstract class TaxBand {
  abstract Money calculateTaxFrom(Money annualSalary);
}
