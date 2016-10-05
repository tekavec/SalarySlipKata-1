package org.somename.salaryslip.tax;

import static java.lang.String.*;

import java.util.Objects;

import org.somename.salaryslip.components.Money;

public class TaxDetails {
  private final Money taxFreeAllowance;
  private final Money taxableIncome;
  private final Money taxablePayable;

  public TaxDetails(Money taxFreeAllowance, Money taxableIncome, Money taxablePayable) {
    this.taxFreeAllowance = taxFreeAllowance;
    this.taxableIncome = taxableIncome;
    this.taxablePayable = taxablePayable;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TaxDetails that = (TaxDetails) o;
    return Objects.equals(taxFreeAllowance, that.taxFreeAllowance) &&
        Objects.equals(taxableIncome, that.taxableIncome) &&
        Objects.equals(taxablePayable, that.taxablePayable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taxFreeAllowance, taxableIncome, taxablePayable);
  }

  @Override
  public String toString() {
    return format("taxFreeAllowance=%s \n taxableIncome=%s \n taxablePayable=%s",
        taxFreeAllowance, taxableIncome, taxablePayable);
  }
}
