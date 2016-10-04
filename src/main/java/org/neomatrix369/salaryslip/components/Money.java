package org.neomatrix369.salaryslip.components;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
  private BigDecimal denomination;

  public Money(double denomination) {
    this.denomination = updateWithDefaultScale(valueOf(denomination));
  }

  public Money(BigDecimal denomination) {
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

  public Money divisionBy(int dividedBy) {
    BigDecimal dividedByAsBigDecimal = updateWithDefaultScale(valueOf(dividedBy));
    final BigDecimal result = denomination.divide(dividedByAsBigDecimal, 2, ROUND_HALF_UP);
    return new Money(result);
  }

  public boolean isGreaterThanZero() {
    return denomination.compareTo(valueOf(0.00)) > 0;
  }

  public static Money minimum(Money firstAmount, Money secondAmount) {
    Money difference = firstAmount.subtract(secondAmount);

    if (difference.isGreaterThanZero()) {
      return secondAmount;
    }

    return firstAmount;
  }

  private BigDecimal updateWithDefaultScale(BigDecimal value) {
    return value.setScale(2, ROUND_HALF_UP);
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
