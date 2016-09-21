package SalarySlipKata.domain;

import static java.lang.String.format;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
  private static final int PLACES_AFTER_DECIMAL = 2;

  private BigDecimal denomination;

  public Money(double denomination) {
    this.denomination = valueOf(denomination).setScale(PLACES_AFTER_DECIMAL);
  }

  public Money(BigDecimal denomination) {
    this.denomination = denomination;
  }

  public Money(Money money) {
    this.denomination = money.denomination;
  }

  public static Money zero(double denomination) {return new Money(denomination);}

  public Money divideBy(int divisor) {
    BigDecimal bdDivisor = valueOf(divisor).setScale(PLACES_AFTER_DECIMAL);
    final BigDecimal quotient = denomination.divide(bdDivisor, PLACES_AFTER_DECIMAL, ROUND_HALF_UP);
    return new Money(quotient);
  }

  public Money plus(Money money) {
    return new Money(denomination.add(money.denomination));
  }

  public boolean isGreaterThanZero() {
    return denomination.compareTo(valueOf(0)) > 0;
  }

  public Money minus(Money money) {
    return new Money(denomination.subtract(money.denomination));
  }

  public Money multiplyBy(double anotherDenomination) {
    return new Money(denomination.multiply(valueOf(anotherDenomination)));
  }

  public static Money zero() {
    return new Money(0.0);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Money gbp = (Money) o;
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
