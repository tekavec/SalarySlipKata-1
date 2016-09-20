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

  public final double getTaxFreeAllowance() {return PERSONAL_ALLOWANCE;}

  public final double getTaxableIncomeFor(double annualSalary) {
    final double taxableIncome = annualSalary - getTaxFreeAllowance();
    return taxableIncome > 0
              ? taxableIncome
              : 0.0;
  }

  public final double getTaxPayableFor(double annualSalary) {
    final double higherTaxLimitsDifference = annualSalary - HIGHER_TAX_LOWER_LIMIT;

    if (higherTaxLimitsDifference > 0) {
      return calculateHigherTaxFrom(higherTaxLimitsDifference) +
          calculateBasicTaxFrom(BASIC_TAX_LIMITS_DIFFERENCE);
    }

    return calculateBasicTaxFrom(getTaxableIncomeFor(annualSalary));
  }

  private double calculateHigherTaxFrom(double amount) {return amount * HIGHER_TAX_RATE;}

  private double calculateBasicTaxFrom(double amount) {return amount * BASIC_TAX_RATE;}
}
