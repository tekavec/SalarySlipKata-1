package org.neomatrix369.salaryslip.components;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.neomatrix369.salaryslip.components.Money.minimum;

import org.junit.Test;

public class MoneyShould {
  @Test public void
  return_the_result_of_an_amount_divided_by_a_divisor() {
    Money someAmount = new Money(1.23);
    int someDivisor = 5;

    assertThat(someAmount.divisionBy(someDivisor), is(new Money(0.25)));
  }
  
  @Test public void
  return_the_difference_from_another_amount() {
    Money firstAmount = new Money(20.00);
    Money secondAmount = new Money(10.00);

    assertThat(firstAmount.subtract(secondAmount), is(new Money(10.00)));
  }
  
  @Test public void
  return_zero_if_the_difference_with_another_amount_is_negative() {
    Money firstAmount = new Money(12.00);
    Money secondAmount = new Money(14.00);

    assertThat(firstAmount.subtract(secondAmount), is(new Money(0.00)));
  } 
  
  @Test public void
  return_the_product_of_an_amount_with_a_multiplicant() {
    Money someAmount = new Money(10.00);
    double someMultiplicant = 1.23;

    assertThat(someAmount.times(someMultiplicant), is(new Money(12.30)));
  } 
  
  @Test public void
  return_the_minimum_of_the_two_amounts() {
    Money firstAmount = new Money(20.00);
    Money secondAmount = new Money(30.00);

    assertThat(minimum(firstAmount, secondAmount), is(firstAmount));
  }

  @Test public void
  return_the_sum_of_two_amounts() {
    Money firstAmount = new Money(10.00);
    Money secondAmount = new Money(15.00);

    assertThat(firstAmount.add(secondAmount), is(new Money(25.00)));
  }
  
  @Test public void
  validate_one_amount_is_greater_than_the_other() {
    Money firstAmount = new Money(20.00);
    Money secondAmount = new Money(10.00);

    assertThat(firstAmount.isGreaterThan(secondAmount), is(true));
  } 
}
