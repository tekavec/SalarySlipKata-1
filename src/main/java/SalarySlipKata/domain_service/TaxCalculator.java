package SalarySlipKata.domain_service;

import static SalarySlipKata.domain.Money.zero;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.TaxDetails;

public class TaxCalculator {

  private enum TaxBands {
    ADDITIONAL_TAX(new Money(150_000.00), 0.45),
    HIGHER_TAX    (new Money(43_000.00),  0.40),
    BASIC_TAX     (new Money(11_000.00),  0.20),
    ZERO_TAX      (new Money(0.00),       0.00);


    private final Money threshold;
    private final double rate;

    TaxBands(Money threshold, double rate) {
      this.threshold = threshold;
      this.rate = rate;
    }
  }

  private static final Money PERSONAL_ALLOWANCE = TaxBands.BASIC_TAX.threshold;
  private static final Money
      THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE = new Money(100_000.00);

  public TaxDetails calculateTaxDetailsFor(Money annualSalary) {
    return new TaxDetails(
        calculateTaxFreeAllowanceFor(annualSalary),
        calculateTaxableIncomeFor(annualSalary),
        calculateTaxPayableFor(annualSalary)
    );
  }

  private Money calculateTaxFreeAllowanceFor(Money annualSalary) {
    final Money differenceAbove100k = calculateDifferenceAbove100kOf(annualSalary);

    return (differenceAbove100k.isGreaterThanZero()
        ? reduce1PoundForEvery2PoundsEarnedOn(differenceAbove100k)
        : PERSONAL_ALLOWANCE);
  }

  private Money calculateTaxableIncomeFor(Money annualSalary) {
    return annualSalary.minus(calculateTaxFreeAllowanceFor(annualSalary));
  }

  private Money calculateTaxPayableFor(Money originalAnnualSalary) {
    Money remainingSalary = new Money(originalAnnualSalary);

    Money adjustmentDueToPersonalAllowanceReductionRule = zero();
    Money contributions = zero();

    for (TaxBands taxBand: TaxBands.values()) {
      Money excessIncome = calculateExcessForBandIncomeWith(
          remainingSalary, adjustmentDueToPersonalAllowanceReductionRule, taxBand.threshold);
      contributions = contributions.plus(
          calculateContribution(excessIncome, taxBand.rate)
      );
      adjustmentDueToPersonalAllowanceReductionRule =
          calculateAdjustmentDueTo100KPersonalAllowanceReductionRuleWith(remainingSalary);

      remainingSalary = remainingSalary.minus(excessIncome);
    }

    return contributions;
  }

  private Money calculateExcessForBandIncomeWith(Money annualSalary, Money adjustmentDueToPersonalAllowanceReductionRule, Money threshold) {
    return annualSalary
              .minus(threshold)
              .plus(adjustmentDueToPersonalAllowanceReductionRule);
  }

  private Money calculateContribution(Money amount, double taxRate) {return amount.multiplyBy(taxRate);}

  private Money calculateAdjustmentDueTo100KPersonalAllowanceReductionRuleWith(Money annualSalary) {
    final Money differenceAbove100K = calculateDifferenceAbove100kOf(annualSalary);

    if (differenceAbove100K.isGreaterThanZero()) {
      final Money actualPersonalAllowance = reduce1PoundForEvery2PoundsEarnedOn(differenceAbove100K);
      return actualPersonalAllowance.isGreaterThanZero()
          ? actualPersonalAllowance
          : PERSONAL_ALLOWANCE;
    }

    return zero();
  }

  private Money calculateDifferenceAbove100kOf(Money annualSalary) {
    return annualSalary.minus(THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);
  }

  private Money reduce1PoundForEvery2PoundsEarnedOn(Money differenceAbove100k) {
    Money halfOfTheDifference = differenceAbove100k.divideBy(2);
    return PERSONAL_ALLOWANCE.minus(halfOfTheDifference);
  }
}
