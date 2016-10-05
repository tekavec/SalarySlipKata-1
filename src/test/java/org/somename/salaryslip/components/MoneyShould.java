package org.somename.salaryslip.components;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.somename.salaryslip.components.Money.ZERO;
import static org.somename.salaryslip.components.Money.minimum;

import org.junit.Test;

public class MoneyShould {
  @Test public void
  return_the_quotient_of_a_division_of_an_amount_by_a_divisor() {
    Money someAmount = new Money(12.345);
    int someDivisor = 12;

    assertThat(someAmount.divisionBy(someDivisor), is(new Money(1.03)));
  } 
  
  @Test public void
  return_the_difference_between_two_amounts() {
    Money firstAmount = new Money(12.345);
    Money secondAmount = new Money(4.567);

    assertThat(firstAmount.subtract(secondAmount), is(new Money(7.78)));
  } 
  
  @Test public void
  return_zero_if_the_difference_between_two_amounts_is_negative() {
    Money firstAmount = new Money(15.00);
    Money secondAmount = new Money(20.00);

    assertThat(firstAmount.subtract(secondAmount), is(ZERO));
  } 
  
  @Test public void
  return_the_product_of_an_amount_and_a_multiplicant() {
    Money someAmount = new Money(5.00);
    assertThat(someAmount.times(1.23), is(new Money(6.15)));
  } 
  
  @Test public void
  return_the_minimum_of_two_amount() {
    Money firstAmount = new Money(200.00);
    Money secondAmount = new Money(100.00);

    assertThat(minimum(firstAmount, secondAmount), is(secondAmount));
  }

  @Test public void
  return_the_sum_of_two_amounts() {
    Money firstAmount = new Money(10.00);
    Money secondAmount = new Money(15.00);

    assertThat(firstAmount.add(secondAmount), is(new Money(25.00)));
  }
}
