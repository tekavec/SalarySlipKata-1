package SalarySlipKata.domain_service;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.TaxDetails;

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

  private static final int TWELVE_MONTHS = 12;

  public TaxDetails calculateMonthlyTaxDetailsFor(Money annualSalary) {
    return new TaxDetails(
        calculateMonthlyTaxFreeAllowanceFor(annualSalary),
        calculateMonthlyTaxableIncomeFor(annualSalary),
        calculateMonthlyTaxPayableFor(annualSalary)
    );
  }

  private Money calculateMonthlyTaxFreeAllowanceFor(Money annualSalary) {
    return calculateTaxFreeAllowanceFor(annualSalary).divideBy(TWELVE_MONTHS);
  }

  private Money calculateTaxFreeAllowanceFor(Money annualSalary) {
    final Money differenceAbove100k = calculateDifferenceAbove100k(annualSalary);

    if (differenceAbove100k.isGreaterThanZero()) {
      Money halfOfTheDifference = differenceAbove100k.divideBy(2);
      final Money actualPersonalAllowance = PERSONAL_ALLOWANCE.minus(halfOfTheDifference);
      return actualPersonalAllowance.isGreaterThanZero()
                ? actualPersonalAllowance
                : zero();
    }

    return PERSONAL_ALLOWANCE;
  }

  private Money calculateMonthlyTaxableIncomeFor(Money annualSalary) {
    return calculateTaxableIncomeFor(annualSalary).divideBy(TWELVE_MONTHS);
  }

  private Money calculateTaxableIncomeFor(Money annualSalary) {
    final Money taxableIncome = annualSalary.minus(calculateTaxFreeAllowanceFor(annualSalary));
    return taxableIncome.isGreaterThanZero()
              ? taxableIncome
              : zero();
  }

  public Money calculateMonthlyTaxPayableFor(Money annualSalary) {
    return calculateTaxPayableFor(annualSalary).divideBy(TWELVE_MONTHS);
  }

  private Money calculateTaxPayableFor(Money annualSalary) {
    final Money additionalTaxLimitsDifference = annualSalary.minus(HIGHER_TAX_UPPER_LIMIT);
    if (additionalTaxLimitsDifference.isGreaterThanZero()) {
      return
          calculateAdditionalTaxFor(additionalTaxLimitsDifference)
              .plus(calculateHigherTaxFor(HIGHER_TAX_UPPER_LIMIT.minus(BASIC_TAX_LIMITS_DIFFERENCE)))
              .plus(calculateBasicTaxFor(BASIC_TAX_LIMITS_DIFFERENCE));
    }

    final Money taxableIncome = calculateTaxableIncomeFor(annualSalary);
    if (calculateDifferenceAbove100k(annualSalary).isGreaterThanZero()) {
      return
          calculateHigherTaxFor(taxableIncome.minus(BASIC_TAX_LIMITS_DIFFERENCE))
          .plus(calculateBasicTaxFor(BASIC_TAX_LIMITS_DIFFERENCE));
    }

    final Money higherTaxLimitsDifference = annualSalary.minus(HIGHER_TAX_LOWER_LIMIT);
    if (higherTaxLimitsDifference.isGreaterThanZero()) {
      return
          calculateHigherTaxFor(higherTaxLimitsDifference)
          .plus(calculateBasicTaxFor(BASIC_TAX_LIMITS_DIFFERENCE));
    }

    return calculateBasicTaxFor(taxableIncome);
  }

  private Money calculateAdditionalTaxFor(Money amount) {return amount.multiplyBy(ADDITIONAL_TAX_RATE);}

  private Money calculateDifferenceAbove100k(Money annualSalary) {return annualSalary.minus(UPPER_LIMIT_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);}

  private Money calculateHigherTaxFor(Money amount) {return amount.multiplyBy(HIGHER_TAX_RATE);}

  private Money calculateBasicTaxFor(Money amount) {return new Money(amount.multiplyBy(BASIC_TAX_RATE));}
}
