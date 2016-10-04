package org.neomatrix369.salaryslip.components;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.neomatrix369.salaryslip.components.Money.minimum;
import static org.neomatrix369.salaryslip.components.Money.zero;

import org.junit.Test;

public class MoneyShould {
  @Test public void
  return_a_result_of_a_division_by_a_divisor() {
    Money someMoney = new Money(12.34);
    int someDivisor = 5;

    final Money expectedResult = new Money(2.47);
    assertThat(someMoney.divisionBy(someDivisor), is(expectedResult));
  }

  @Test public void
  return_the_difference_between_two_amounts() {
    Money firstAmount = new Money(20.00);
    Money secondAmount = new Money(15.00);

    Money expectedDifference = new Money(5.00);
    assertThat(firstAmount.subtract(secondAmount), is(expectedDifference));
  }

  @Test public void
  return_zero_if_the_difference_between_two_numbers_is_less_than_zero() {
    Money firstAmount = new Money(15.00);
    Money secondAmount = new Money(20.00);

    assertThat(firstAmount.subtract(secondAmount), is(zero()));
  }

  @Test public void
  validate_if_amount_is_greater_than_zero() {
    Money someAmount = new Money(1.23);

    assertThat(someAmount.isGreaterThanZero(), is(true));
  }

  @Test public void
  return_the_lower_of_the_two_amounts() {
    Money firstAmount = new Money(100.00);
    Money secondAmount = new Money(15.00);

    assertThat(minimum(firstAmount, secondAmount), is(secondAmount));
  }
}
