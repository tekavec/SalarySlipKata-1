package SalarySlipKata.domain_service;

import static java.lang.Double.MAX_VALUE;
import static SalarySlipKata.domain.Money.zero;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.bands.Band;
import SalarySlipKata.domain.bands.HigherTaxWith100KRuleBand;
import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.TaxDetails;

public class TaxCalculator {

  private Band ADDITIONAL_TAX = new Band(new Money(150_000.00), new Money( MAX_VALUE), 0.45);
  private Band HIGHER_TAX     = new HigherTaxWith100KRuleBand(new Money( 43_000.00), new Money(150_000.00), 0.40);
  private Band BASIC_TAX      = new Band(new Money( 11_000.00), new Money( 43_000.00), 0.20);
  private Band ZERO_TAX       = new Band(new Money(      0.00), new Money( 11_000.00), 0.00);

  private List<Band> taxBands = new ArrayList<Band>() {
    { add(ADDITIONAL_TAX); }
    { add(HIGHER_TAX    ); }
    { add(BASIC_TAX     ); }
    { add(ZERO_TAX      ); }
  };

  private static final Money PERSONAL_ALLOWANCE = new Money(11_000.00);
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
    Money totalTaxPayable = zero();

    for (Band taxBand: taxBands) {
      Money taxPayable = taxBand.calculateFrom(annualSalary);
      totalTaxPayable = totalTaxPayable.plus(taxPayable);
    }

    return totalTaxPayable;
  }

  private Money calculateDifferenceAbove100kOf(Money annualSalary) {
    return annualSalary.minus(THRESHOLD_FOR_PERSONAL_ALLOWANCE_REDUCTION_RULE);
  }

  private Money reduce1PoundForEvery2PoundsEarnedOn(Money differenceAbove100k) {
    final Money reduced1PoundForEvery2PoundsEarned = differenceAbove100k.divideBy(2);
    return PERSONAL_ALLOWANCE.minus(reduced1PoundForEvery2PoundsEarned);
  }
}
