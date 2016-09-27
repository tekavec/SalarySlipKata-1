package SalarySlipKata.domain_service;

import static java.lang.Double.MAX_VALUE;
import static SalarySlipKata.domain.Money.zero;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.bands.Band;
import SalarySlipKata.domain.bands.HigherTaxWithPersonalAllowanceReductionRuleBand;
import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.TaxDetails;

public class TaxCalculator {

  private final PersonalAllowanceReductionOver100KRule personalAllowanceReductionOver100KRule =
      new PersonalAllowanceReductionOver100KRule();
  private Band higherTaxBand = new Band(new Money( 43_000.00), new Money(150_000.00), 0.40);
  private Band ADDITIONAL_TAX = new Band(new Money(150_000.00), new Money( MAX_VALUE), 0.45);
  private Band HIGHER_TAX     =
      new HigherTaxWithPersonalAllowanceReductionRuleBand(higherTaxBand, personalAllowanceReductionOver100KRule);
  private Band BASIC_TAX      = new Band(new Money( 11_000.00), new Money( 43_000.00), 0.20);
  private Band ZERO_TAX       = new Band(new Money(      0.00), new Money( 11_000.00), 0.00);

  private List<Band> taxBands = new ArrayList<Band>() {
    { add(ADDITIONAL_TAX); }
    { add(HIGHER_TAX    ); }
    { add(BASIC_TAX     ); }
    { add(ZERO_TAX      ); }
  };

  public TaxDetails calculateTaxDetailsFor(Money annualSalary) {
    return new TaxDetails(
        calculateTaxFreeAllowanceFor(annualSalary),
        calculateTaxableIncomeFor(annualSalary),
        calculateTaxPayableFor(annualSalary)
    );
  }

  private Money calculateTaxFreeAllowanceFor(Money annualSalary) {
    return personalAllowanceReductionOver100KRule.calculateTaxFreeAllowance(annualSalary);
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
}
