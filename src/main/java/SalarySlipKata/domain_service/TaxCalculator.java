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

    return differenceAbove100k.isGreaterThanZero()
              ? reduce1PoundForEvery2PoundsEarnedOn(differenceAbove100k)
              : PERSONAL_ALLOWANCE;
  }

  private Money calculateTaxableIncomeFor(Money annualSalary) {
    return annualSalary.minus(calculateTaxFreeAllowanceFor(annualSalary));
  }

  private Money calculateTaxPayableFor(Money annualSalary) {
    Money remainingSalary = new Money(annualSalary);

    Money adjustmentForEarningsOver100K = zero();
    Money contributions = zero();

    for (TaxBands taxBand: TaxBands.values()) {
      Money excessIncome = calculateExcessForBandIncomeFrom(remainingSalary, taxBand.threshold);

      excessIncome = applyPersonalAllowanceReductionRuleForEarningsOver100K(
          excessIncome, adjustmentForEarningsOver100K
      );

      adjustmentForEarningsOver100K =
          calculateAdjustmentForExcessOver100KDueToPersonalAllowanceReductionRuleWith(remainingSalary);

      contributions = contributions.plus(
          calculateContribution(excessIncome, taxBand.rate)
      );

      remainingSalary = remainingSalary.minus(excessIncome);
    }

    return contributions;
  }

  private Money applyPersonalAllowanceReductionRuleForEarningsOver100K(
      Money excessIncome, Money adjustmentForEarningsOver100K) {
    return excessIncome.plus(adjustmentForEarningsOver100K);
  }

  private Money calculateExcessForBandIncomeFrom(Money remainingSalary, Money threshold) {
    return remainingSalary.minus(threshold);
  }

  private Money calculateContribution(Money excessAmount, double taxRate) {
    return excessAmount.multiplyBy(taxRate);
  }

  private Money calculateAdjustmentForExcessOver100KDueToPersonalAllowanceReductionRuleWith(Money annualSalary) {
    final Money differenceAbove100K = calculateDifferenceAbove100kOf(annualSalary);

    if (differenceAbove100K.isGreaterThanZero()) {
      final Money adjustedPersonalAllowance = reduce1PoundForEvery2PoundsEarnedOn(differenceAbove100K);
      return adjustedPersonalAllowance.isGreaterThanZero()
                ? adjustedPersonalAllowance
                : PERSONAL_ALLOWANCE;
    }

    return zero();
  }

  private Money calculateDifferenceAbove100kOf(Money annualSalary) {
    return annualSalary.minus(THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);
  }

  private Money reduce1PoundForEvery2PoundsEarnedOn(Money differenceAbove100k) {
    final Money reduced1PoundForEvery2PoundsEarned = differenceAbove100k.divideBy(2);
    return PERSONAL_ALLOWANCE.minus(reduced1PoundForEvery2PoundsEarned);
  }
}
