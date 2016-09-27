package SalarySlipKata.domain_service;

import static java.lang.Double.MAX_VALUE;
import static SalarySlipKata.domain.Money.zero;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.TaxDetails;
import SalarySlipKata.domain.bands.Band;
import SalarySlipKata.domain.bands.HigherTaxWithPersonalAllowanceReductionRuleBand;
import SalarySlipKata.domain.bands.StandardBand;
import SalarySlipKata.domain.rule.PersonalAllowanceReduction;

public class TaxCalculator {

  private PersonalAllowanceReduction personalAllowanceReduction;

  private Band higherTaxBand = new StandardBand(new Money( 43_000.00), new Money(150_000.00), 0.40);
  private Band additionalTax = new StandardBand(new Money(150_000.00), new Money( MAX_VALUE), 0.45);
  private Band basicTax      = new StandardBand(new Money( 11_000.00), new Money( 43_000.00), 0.20);
  private Band zeroTax       = new StandardBand(new Money(      0.00), new Money( 11_000.00), 0.00);

  private List<Band> taxBands = new ArrayList<Band>() {
    { add(additionalTax); }
    { add(basicTax); }
    { add(zeroTax); }
  };

  public TaxCalculator(PersonalAllowanceReduction personalAllowanceReduction) {
    this.personalAllowanceReduction = personalAllowanceReduction;

    Band higherTax =
        new HigherTaxWithPersonalAllowanceReductionRuleBand(higherTaxBand, personalAllowanceReduction);
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
    return personalAllowanceReduction.calculateTaxFreeAllowance(annualSalary);
  }

  private Money calculateTaxableIncomeFor(Money annualSalary) {
    return annualSalary.minus(calculateTaxFreeAllowanceFor(annualSalary));
  }

  private Money calculateTaxPayableFor(Money annualSalary) {
    Money totalTaxPayable = zero();

    for (Band taxBand : taxBands) {
      Money taxPayableForTheBand = taxBand.calculateFrom(annualSalary);
      totalTaxPayable = totalTaxPayable.plus(taxPayableForTheBand);
    }

    return totalTaxPayable;
  }
}
