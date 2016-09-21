package SalarySlipKata.domain;

import static java.lang.String.format;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.Objects;

public class GBP {
  private BigDecimal denomination;

  public GBP(double denomination) {this.denomination = valueOf(denomination);}

  public GBP(BigDecimal denomination) {
    this.denomination = denomination;
  }

  public GBP(GBP amount) {
    this.denomination = amount.denomination;
  }

  public static GBP zero(double denomination) {return new GBP(denomination);}

  public GBP divideBy(int divisor) {
    BigDecimal bdDivisor = valueOf(divisor);
    final BigDecimal quotient = denomination.divide(bdDivisor, 2, ROUND_HALF_UP);
    return new GBP(quotient);
  }

  public GBP plus(GBP anotherAmount) {
    return new GBP(denomination.add(anotherAmount.denomination));
  }

  public boolean isGreaterThanZero() {
    return denomination.compareTo(valueOf(0)) > 0;
  }

  public GBP minus(GBP anotherAmount) {
    return new GBP(denomination.subtract(anotherAmount.denomination));
  }

  public GBP multiplyBy(double anotherDenomination) {
    return new GBP(denomination.multiply(valueOf(anotherDenomination)));
  }

  public static GBP zero() {
    return new GBP(0.0);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GBP gbp = (GBP) o;
    return Objects.equals(denomination, gbp.denomination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(denomination);
  }

  @Override
  public String toString() {
    return format("£%.2f", denomination);
  }
}
