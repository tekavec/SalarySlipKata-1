package SalarySlipKata.domain_service;

import SalarySlipKata.domain.GBP;

public class TaxCalculator {
  private static final GBP PERSONAL_ALLOWANCE = new GBP(11_000.00);

  private static final GBP BASIC_TAX_UPPER_LIMIT = new GBP(43_000.00);
  private static final GBP BASIC_TAX_LOWER_LIMIT = new GBP(11_000.00);
  private static final GBP BASIC_TAX_LIMITS_DIFFERENCE =
      BASIC_TAX_UPPER_LIMIT.minus(BASIC_TAX_LOWER_LIMIT);
  private static final double BASIC_TAX_RATE = 0.20;

  private static final GBP HIGHER_TAX_LOWER_LIMIT = new GBP(43_000.00);
  private static final double HIGHER_TAX_RATE = 0.40;

  private static final double ADDITIONAL_TAX_RATE = 0.45;
  private static final GBP HIGHER_TAX_UPPER_LIMIT = new GBP(150_000.00);

  private static final GBP UPPER_LIMIT_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE = new GBP(100_000.00);

  public final GBP getTaxFreeAllowance(GBP annualSalary) {
    final GBP differenceAbove100k = getDifferenceAbove100k(annualSalary);

    if (differenceAbove100k.isGreaterThanZero()) {
      GBP halfOfTheDifference = differenceAbove100k.divideBy(2);
      final GBP actualPersonalAllowance = PERSONAL_ALLOWANCE.minus(halfOfTheDifference);
      return actualPersonalAllowance.isGreaterThanZero()
                ? actualPersonalAllowance
                : GBP.zero(0);
    }

    return PERSONAL_ALLOWANCE;
  }

  public final GBP getTaxableIncomeFor(GBP annualSalary) {
    final GBP taxableIncome = annualSalary.minus(getTaxFreeAllowance(annualSalary));
    return taxableIncome.isGreaterThanZero()
              ? taxableIncome
              : GBP.zero();
  }

  public final GBP getTaxPayableFor(GBP annualSalary) {
    final GBP additionalTaxLimitsDifference = annualSalary.minus(HIGHER_TAX_UPPER_LIMIT);
    if (additionalTaxLimitsDifference.isGreaterThanZero()) {
      return
          calculateAdditionalTaxFrom(additionalTaxLimitsDifference)
              .plus(calculateHigherTaxFrom(HIGHER_TAX_UPPER_LIMIT.minus(BASIC_TAX_LIMITS_DIFFERENCE)))
              .plus(calculateBasicTaxFrom(BASIC_TAX_LIMITS_DIFFERENCE));
    }

    final GBP taxableIncome = getTaxableIncomeFor(annualSalary);
    if (getDifferenceAbove100k(annualSalary).isGreaterThanZero()) {
      return
          calculateHigherTaxFrom(taxableIncome.minus(BASIC_TAX_LIMITS_DIFFERENCE))
          .plus(calculateBasicTaxFrom(BASIC_TAX_LIMITS_DIFFERENCE));
    }

    final GBP higherTaxLimitsDifference = annualSalary.minus(HIGHER_TAX_LOWER_LIMIT);
    if (higherTaxLimitsDifference.isGreaterThanZero()) {
      return
          calculateHigherTaxFrom(higherTaxLimitsDifference)
          .plus(calculateBasicTaxFrom(BASIC_TAX_LIMITS_DIFFERENCE));
    }

    return calculateBasicTaxFrom(taxableIncome);
  }

  private GBP calculateAdditionalTaxFrom(GBP amount) {return amount.multiplyBy(ADDITIONAL_TAX_RATE);}

  private GBP getDifferenceAbove100k(GBP annualSalary) {return annualSalary.minus(UPPER_LIMIT_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);}

  private GBP calculateHigherTaxFrom(GBP amount) {return amount.multiplyBy(HIGHER_TAX_RATE);}

  private GBP calculateBasicTaxFrom(GBP amount) {return new GBP(amount.multiplyBy(BASIC_TAX_RATE));}
}
