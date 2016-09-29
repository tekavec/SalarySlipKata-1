package com.codurance.salaryslip;

import com.codurance.salaryslip.components.Money;
import com.codurance.salaryslip.tax.TaxDetails;

public class TaxDetailsBuilder {
  private Money taxFreeAllowance;
  private Money taxableIncome;
  private Money taxPayable;

  public static TaxDetailsBuilder aTaxDetails() {
    return new TaxDetailsBuilder();
  }

  public TaxDetailsBuilder withTaxFreeAllowance(Money taxFreeAllowance) {
    this.taxFreeAllowance = taxFreeAllowance;
    return this;
  }

  public TaxDetailsBuilder withTaxableIncome(Money taxableIncome) {
    this.taxableIncome = taxableIncome;
    return this;
  }

  public TaxDetailsBuilder withTaxPayable(Money taxPayable) {
    this.taxPayable = taxPayable;
    return this;
  }

  public TaxDetails build() {
    return new TaxDetails(taxFreeAllowance, taxableIncome, taxPayable);
  }
}
