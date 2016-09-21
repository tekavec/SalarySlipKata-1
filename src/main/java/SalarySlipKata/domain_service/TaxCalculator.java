package SalarySlipKata.domain_service;

import SalarySlipKata.domain.Money;

public class TaxCalculator {
  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);

  private static final Money BASIC_TAX_UPPER_LIMIT = new Money(43_000.00);
  private static final Money BASIC_TAX_LOWER_LIMIT = new Money(11_000.00);
  private static final Money BASIC_TAX_LIMITS_DIFFERENCE =
      BASIC_TAX_UPPER_LIMIT.minus(BASIC_TAX_LOWER_LIMIT);
  private static final double BASIC_TAX_RATE = 0.20;

  private static final Money HIGHER_TAX_LOWER_LIMIT = new Money(43_000.00);
  private static final double HIGHER_TAX_RATE = 0.40;

  private static final double ADDITIONAL_TAX_RATE = 0.45;
  private static final Money HIGHER_TAX_UPPER_LIMIT = new Money(150_000.00);

  private static final Money UPPER_LIMIT_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE = new Money(100_000.00);

  public final Money getTaxFreeAllowance(Money annualSalary) {
    final Money differenceAbove100k = getDifferenceAbove100k(annualSalary);

    if (differenceAbove100k.isGreaterThanZero()) {
      Money halfOfTheDifference = differenceAbove100k.divideBy(2);
      final Money actualPersonalAllowance = PERSONAL_ALLOWANCE.minus(halfOfTheDifference);
      return actualPersonalAllowance.isGreaterThanZero()
                ? actualPersonalAllowance
                : Money.zero(0);
    }

    return PERSONAL_ALLOWANCE;
  }

  public final Money getTaxableIncomeFor(Money annualSalary) {
    final Money taxableIncome = annualSalary.minus(getTaxFreeAllowance(annualSalary));
    return taxableIncome.isGreaterThanZero()
              ? taxableIncome
              : Money.zero();
  }

  public final Money getTaxPayableFor(Money annualSalary) {
    final Money additionalTaxLimitsDifference = annualSalary.minus(HIGHER_TAX_UPPER_LIMIT);
    if (additionalTaxLimitsDifference.isGreaterThanZero()) {
      return
          calculateAdditionalTaxFrom(additionalTaxLimitsDifference)
              .plus(calculateHigherTaxFrom(HIGHER_TAX_UPPER_LIMIT.minus(BASIC_TAX_LIMITS_DIFFERENCE)))
              .plus(calculateBasicTaxFrom(BASIC_TAX_LIMITS_DIFFERENCE));
    }

    final Money taxableIncome = getTaxableIncomeFor(annualSalary);
    if (getDifferenceAbove100k(annualSalary).isGreaterThanZero()) {
      return
          calculateHigherTaxFrom(taxableIncome.minus(BASIC_TAX_LIMITS_DIFFERENCE))
          .plus(calculateBasicTaxFrom(BASIC_TAX_LIMITS_DIFFERENCE));
    }

    final Money higherTaxLimitsDifference = annualSalary.minus(HIGHER_TAX_LOWER_LIMIT);
    if (higherTaxLimitsDifference.isGreaterThanZero()) {
      return
          calculateHigherTaxFrom(higherTaxLimitsDifference)
          .plus(calculateBasicTaxFrom(BASIC_TAX_LIMITS_DIFFERENCE));
    }

    return calculateBasicTaxFrom(taxableIncome);
  }

  private Money calculateAdditionalTaxFrom(Money amount) {return amount.multiplyBy(ADDITIONAL_TAX_RATE);}

  private Money getDifferenceAbove100k(Money annualSalary) {return annualSalary.minus(UPPER_LIMIT_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);}

  private Money calculateHigherTaxFrom(Money amount) {return amount.multiplyBy(HIGHER_TAX_RATE);}

  private Money calculateBasicTaxFrom(Money amount) {return new Money(amount.multiplyBy(BASIC_TAX_RATE));}
}
