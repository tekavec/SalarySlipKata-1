package org.neomatrix369.salaryslip.components;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.neomatrix369.salaryslip.components.Money.minimumOf;
import static org.neomatrix369.salaryslip.components.Money.zero;

import org.junit.Test;

public class MoneyShould {
  @Test public void
  validate_that_the_given_amount_is_greater_than_zero() {
    final Money amount = new Money(10.00);

    assertThat(amount.isGreaterThanZero(), is(true));
  }

  @Test public void
  validate_that_the_given_amount_is_greater_than_another_amount() {
    Money firstAmount = new Money(30.00);
    Money secondAmount = new Money(20.00);

    assertThat(firstAmount.isGreaterThan(secondAmount), is(true));
  } 

  @Test public void
  return_the_smaller_of_any_two_given_amounts() {
    Money firstAmount = new Money(40.00);
    Money secondAmount = new Money(25.00);

    assertThat(minimumOf(firstAmount, secondAmount), is(secondAmount));
  }

  @Test public void
  be_able_to_sum_up_two_given_amounts() {
    Money firstAmount = new Money(50.00);
    Money secondAmount = new Money(25.00);

    Money sumOfTheTwoAmounts = new Money(75.00);
    assertThat(firstAmount.add(secondAmount), is(sumOfTheTwoAmounts));
  }
  
  @Test public void
  be_able_to_subtract_two_given_amounts() {
    Money firstAmount = new Money(35.00);
    Money secondAmount = new Money(15.00);

    Money differenceOfTheTwoAmounts = new Money(20.00);
    assertThat(firstAmount.subtract(secondAmount), is(differenceOfTheTwoAmounts));
  }

  @Test public void
  return_zero_if_the_difference_of_two_given_amounts_is_less_than_zero() {
    Money firstAmount = new Money(25.00);
    Money secondAmount = new Money(40.00);

    assertThat(firstAmount.subtract(secondAmount), is(zero()));
  }
  
  @Test public void
  return_the_product_of_an_amount_and_a_multiplicant() {
    Money someAmount = new Money(20.50);
    double someMultiplicant = 0.45;

    Money productOfTheOperation = new Money(09.23);
    assertThat(someAmount.times(someMultiplicant), is(productOfTheOperation));
  } 
  
  @Test public void
  return_the_result_of_a_division_operation_between_an_amount_and_a_divisor() {
    Money someAmount = new Money(25.35);
    int someDivisor = 5;

    Money resultOfOperation = new Money(5.07);
    assertThat(someAmount.divisionBy(someDivisor), is(resultOfOperation));
  } 
}
