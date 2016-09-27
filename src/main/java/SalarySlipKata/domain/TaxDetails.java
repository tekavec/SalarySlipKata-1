package SalarySlipKata.domain;

import static java.lang.String.*;

import java.util.Objects;

public class TaxDetails {
  private final Money taxFreeAllowance;
  private final Money taxableIncome;
  private final Money taxPayable;

  public TaxDetails(Money taxFreeAllowance, Money taxableIncome, Money taxPayable) {
    this.taxFreeAllowance = taxFreeAllowance;
    this.taxableIncome = taxableIncome;
    this.taxPayable = taxPayable;
  }

  public Money taxFreeAllowance() {
    return new Money(taxFreeAllowance);
  }

  public Money taxPayable() {
    return new Money(taxPayable);
  }

  public Money taxableIncome() {
    return new Money(taxableIncome);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TaxDetails that = (TaxDetails) o;
    return Objects.equals(taxFreeAllowance, that.taxFreeAllowance) &&
        Objects.equals(taxableIncome, that.taxableIncome) &&
        Objects.equals(taxPayable, that.taxPayable);
  }

  @Override
  public int hashCode() {
    return Objects.hash(taxFreeAllowance, taxableIncome, taxPayable);
  }

  @Override
  public String toString() {
    return format("taxFreeAllowance=%s, taxableIncome=%s, taxPayable=%s",
        taxFreeAllowance, taxableIncome, taxPayable);
  }
}
