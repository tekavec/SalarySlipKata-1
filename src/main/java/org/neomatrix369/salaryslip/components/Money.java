package org.neomatrix369.salaryslip.components;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
  private static final int DEFAULT_SCALE = 2;

  private BigDecimal denomination;

  public Money(double denomination) {
    this.denomination = updateWithDefaultScale(valueOf(denomination));
  }

  private Money(BigDecimal denomination) {
    this.denomination = updateWithDefaultScale(denomination);
  }

  public Money(Money amount) {
    this.denomination = updateWithDefaultScale(amount.denomination);
  }

  public static Money zero() {return new Money(0.00);}

  public Money add(Money anotherAmount) {
    return new Money(denomination.add(anotherAmount.denomination));
  }

  public Money subtract(Money anotherAmount) {
    final BigDecimal difference = denomination.subtract(anotherAmount.denomination);
    if (difference.compareTo(ZERO) > 0) {
      return new Money(difference);
    }

    return zero();
  }

  public Money times(double rate) {
    BigDecimal rateAsBigDecimal = updateWithDefaultScale(valueOf(rate));
    BigDecimal result = denomination.multiply(rateAsBigDecimal);
    result = updateWithDefaultScale(result);
    return new Money(result);
  }

  public Money divideBy(int dividedBy) {
    BigDecimal dividedByAsBigDecimal = updateWithDefaultScale(valueOf(dividedBy));
    final BigDecimal result = denomination.divide(dividedByAsBigDecimal, DEFAULT_SCALE, ROUND_HALF_UP);
    return new Money(result);
  }

  public boolean isGreaterThanZero() {
    return isGreaterThan(zero());
  }

  public boolean isGreaterThan(Money amount) {
    return denomination.compareTo(amount.denomination) > 0;
  }

  public static Money smallerOf(Money firstAmount, Money secondAmount) {
    final BigDecimal minimumOfTheTwo = firstAmount.denomination.min(secondAmount.denomination);
    return new Money(minimumOfTheTwo);
  }

  private BigDecimal updateWithDefaultScale(BigDecimal value) {
    return value.setScale(DEFAULT_SCALE, ROUND_HALF_UP);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Money money = (Money) o;
    return Objects.equals(denomination, money.denomination);
  }

  @Override
  public int hashCode() {
    return Objects.hash(denomination);
  }

  @Override
  public String toString() {
    return "£" + denomination;
  }
}
