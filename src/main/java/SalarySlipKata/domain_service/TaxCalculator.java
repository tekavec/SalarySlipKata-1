package SalarySlipKata.domain_service;

import static java.lang.Double.MAX_VALUE;
import static SalarySlipKata.domain.Money.zero;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.bands.StandardBand;
import SalarySlipKata.domain.bands.HigherTaxWithPersonalAllowanceReductionRuleBand;
import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.TaxDetails;
import SalarySlipKata.domain.rule.PersonalAllowanceReductionOver100K;

public class TaxCalculator {

  private PersonalAllowanceReductionOver100K personalAllowanceReductionOver100K;

  private StandardBand higherTaxBand = new StandardBand(new Money( 43_000.00), new Money(150_000.00), 0.40);
  private StandardBand additionalTax = new StandardBand(new Money(150_000.00), new Money( MAX_VALUE), 0.45);
  private StandardBand basicTax      = new StandardBand(new Money( 11_000.00), new Money( 43_000.00), 0.20);
  private StandardBand zeroTax       = new StandardBand(new Money(      0.00), new Money( 11_000.00), 0.00);

  private List<StandardBand> taxBands = new ArrayList<StandardBand>() {
    { add(additionalTax); }
    { add(basicTax); }
    { add(zeroTax); }
  };

  public TaxCalculator(PersonalAllowanceReductionOver100K personalAllowanceReductionOver100K) {
    this.personalAllowanceReductionOver100K = personalAllowanceReductionOver100K;

    StandardBand higherTax =
        new HigherTaxWithPersonalAllowanceReductionRuleBand(higherTaxBand, personalAllowanceReductionOver100K);
    taxBands.add(higherTax);
  }

  public TaxDetails calculateTaxDetailsFor(Money annualSalary) {
    return new TaxDetails(
        calculateTaxFreeAllowanceFor(annualSalary),
        calculateTaxableIncomeFor(annualSalary),
        calculateTaxPayableFor(annualSalary)
    );
  }

  private Money calculateTaxFreeAllowanceFor(Money annualSalary) {
    return personalAllowanceReductionOver100K.calculateTaxFreeAllowance(annualSalary);
  }

  private Money calculateTaxableIncomeFor(Money annualSalary) {
    return annualSalary.minus(calculateTaxFreeAllowanceFor(annualSalary));
  }

  private Money calculateTaxPayableFor(Money annualSalary) {
    Money totalTaxPayable = zero();

    for (StandardBand taxStandardBand : taxBands) {
      Money taxPayableForTheBand = taxStandardBand.calculateFrom(annualSalary);
      totalTaxPayable = totalTaxPayable.plus(taxPayableForTheBand);
    }

    return totalTaxPayable;
  }
}
