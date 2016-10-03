package org.neomatrix369.salaryslip.components;

import static java.lang.String.format;
import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.ZERO;
import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;
import java.util.Objects;

public class Money {
  private static final int PLACES_AFTER_DECIMAL = 2;

  private BigDecimal denomination;

  public Money(double denomination) {
    this.denomination = valueOf(denomination);
    updateScaleToTwoDecimalPlaces();
  }

  public Money(Money amount) {
    this.denomination = amount.denomination;
    updateScaleToTwoDecimalPlaces();
  }

  private Money(BigDecimal denomination) {
    this.denomination = denomination;
    updateScaleToTwoDecimalPlaces();
  }

  public static Money zero() {return new Money(0.0);}

  private void updateScaleToTwoDecimalPlaces() {
    this.denomination = this.denomination.setScale(PLACES_AFTER_DECIMAL, ROUND_HALF_UP);
  }

  public boolean isGreaterThanZero()  {
    return denomination.compareTo(ZERO) > 0;
  }

  public boolean isGreaterThan(Money anotherAmount) {
    return denomination.compareTo(anotherAmount.denomination) > 0;
  }

  public static Money minimum(Money firstAmount, Money secondAmount) {
    final BigDecimal minimumOfTheTwo = firstAmount.denomination.min(secondAmount.denomination);
    return new Money(minimumOfTheTwo);
  }

  public Money plus(Money amount) {
    return new Money(denomination.add(amount.denomination));
  }

  public Money minus(Money amount) {
    if (denomination.compareTo(amount.denomination) > 0) {
      final BigDecimal differenceBetweenTheTwoAmounts = denomination.subtract(amount.denomination);
      return new Money(differenceBetweenTheTwoAmounts);
    }

    return zero();
  }

  public Money multiplyBy(double anotherDenomination) {
    final BigDecimal anotherDenominationAsBigDecimal = valueOf(anotherDenomination);
    return new Money(denomination.multiply(anotherDenominationAsBigDecimal));
  }

  public Money divideBy(int divisor) {
    final BigDecimal divisorAsBigDecimal = valueOf(divisor).setScale(PLACES_AFTER_DECIMAL, ROUND_HALF_UP);
    return new Money(denomination.divide(divisorAsBigDecimal, PLACES_AFTER_DECIMAL, ROUND_HALF_UP));
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
    return format("£%.2f", denomination);
  }
}
