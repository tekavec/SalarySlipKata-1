package SalarySlipKata.domain_service;

public class TaxCalculator {
  private static final double PERSONAL_ALLOWANCE = 11_000.00;

  private static final double BASIC_TAX_UPPER_LIMIT = 43_000.00;
  private static final double BASIC_TAX_LOWER_LIMIT = 11_000.00;
  private static final double BASIC_TAX_LIMITS_DIFFERENCE =
      BASIC_TAX_UPPER_LIMIT - BASIC_TAX_LOWER_LIMIT;
  private static final double BASIC_TAX_RATE = 0.20;

  private static final double HIGHER_TAX_LOWER_LIMIT = 43_000.00;
  private static final double HIGHER_TAX_RATE = 0.40;

  public final double getTaxFreeAllowance(double annualSalary) {
    final double differenceAbove100k = getDifferenceAbove100k(annualSalary);

    if (differenceAbove100k > 0) {
      double halfOfTheDifference = differenceAbove100k / 2;
      final double actualPersonalAllowance = PERSONAL_ALLOWANCE - halfOfTheDifference;
      return actualPersonalAllowance > 0
                ? actualPersonalAllowance
                : 0;
    }

    return PERSONAL_ALLOWANCE;
  }

  public final double getTaxableIncomeFor(double annualSalary) {
    final double taxableIncome = annualSalary - getTaxFreeAllowance(annualSalary);
    return taxableIncome > 0
              ? taxableIncome
              : 0.0;
  }

  public final double getTaxPayableFor(double annualSalary) {
    if (getDifferenceAbove100k(annualSalary) > 0) {
      return calculateHigherTaxFrom(getTaxableIncomeFor(annualSalary) - BASIC_TAX_LIMITS_DIFFERENCE) +
          calculateBasicTaxFrom(BASIC_TAX_LIMITS_DIFFERENCE);
    }

    final double higherTaxLimitsDifference = annualSalary - HIGHER_TAX_LOWER_LIMIT;
    if (higherTaxLimitsDifference > 0) {
      return calculateHigherTaxFrom(higherTaxLimitsDifference) +
          calculateBasicTaxFrom(BASIC_TAX_LIMITS_DIFFERENCE);
    }

    return calculateBasicTaxFrom(getTaxableIncomeFor(annualSalary));
  }

  private double getDifferenceAbove100k(double annualSalary) {return annualSalary - 100_000.00;}

  private double calculateHigherTaxFrom(double amount) {return amount * HIGHER_TAX_RATE;}

  private double calculateBasicTaxFrom(double amount) {return amount * BASIC_TAX_RATE;}
}
