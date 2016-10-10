package org.neomatrix369.salaryslip.components;

import static java.math.BigDecimal.ROUND_HALF_DOWN;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
  public static final Money ZERO = new Money(0.00);
  private BigDecimal denomination;

  public Money(double denomination) {
    this.denomination = updateScaleAndRounding(valueOf(denomination));
  }

  public Money(BigDecimal denomination) {
    this.denomination = updateScaleAndRounding(denomination);
  }

  public Money(Money amount) {
    this.denomination = updateScaleAndRounding(amount.denomination);
  }

  public Money add(Money anotherAmount) {
    final BigDecimal sum = denomination.add(anotherAmount.denomination);
    return new Money(sum);
  }

  public Money subtract(Money anotherAmount) {
    final BigDecimal difference = denomination.subtract(anotherAmount.denomination);
    if (difference.compareTo(BigDecimal.ZERO) < 0) {
      return ZERO;
    }
    return new Money(difference);
  }

  public Money divisionBy(int divisor) {
    BigDecimal convertedDivisor = updateScaleAndRounding(valueOf(divisor));
    BigDecimal result = denomination.divide(convertedDivisor, 2, ROUND_HALF_DOWN);
    return new Money(result);
  }

  public Money times(double rate) {
    BigDecimal convertedRate = updateScaleAndRounding(valueOf(rate));
    final BigDecimal result = updateScaleAndRounding(denomination.multiply(convertedRate));
    return new Money(result);
  }

  public static Money minimum(Money oneAmount, Money anotherAmount) {
    final BigDecimal minimum = oneAmount.denomination.min(anotherAmount.denomination);
    return new Money(minimum);
  }

  public boolean isGreaterThan(Money amount) {
    return denomination.compareTo(amount.denomination) > 0;
  }

  private BigDecimal updateScaleAndRounding(BigDecimal denomination) {
    return denomination.setScale(2, ROUND_HALF_DOWN);
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
