package org.somename.salaryslip.components;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
  public static final Money ZERO = new Money(0.00);

  private static final int DEFAULT_SCALE = 2;

  private BigDecimal denomination;

  public Money(double denomination) {
    this.denomination = updateToDefaultScaleAndRounding(valueOf(denomination));
  }

  public Money(Money amount) {
    this.denomination = amount.denomination;
    this.denomination = updateToDefaultScaleAndRounding(denomination);
  }

  public Money(BigDecimal denomination) {
    this.denomination = denomination;
  }

  public Money add(Money anotherAmount) {
    final BigDecimal sum = denomination.add(anotherAmount.denomination);
    return new Money(sum);
  }

  public Money subtract(Money anotherAmount) {
    final BigDecimal difference = denomination.subtract(anotherAmount.denomination);

    return difference.compareTo(BigDecimal.ZERO) > 0
                ? new Money(difference)
                : ZERO;
  }

  public Money times(double multiplicant) {
    BigDecimal convertedMultiplicant = valueOf(multiplicant);
    BigDecimal product = denomination.multiply(convertedMultiplicant);
    product = updateToDefaultScaleAndRounding(product);
    return new Money(product);
  }

  public Money divisionBy(int divisor) {
    BigDecimal convertedDivisor = updateToDefaultScaleAndRounding(valueOf(divisor));
    final BigDecimal result = denomination.divide(convertedDivisor, DEFAULT_SCALE, ROUND_HALF_UP);
    return new Money(result);
  }

  private BigDecimal updateToDefaultScaleAndRounding(BigDecimal value) {
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

  public static Money minimum(Money firstAmount, Money secondAmount) {
    final BigDecimal minimumOfTheTwo = firstAmount.denomination.min(secondAmount.denomination);
    return new Money(minimumOfTheTwo);
  }
}
