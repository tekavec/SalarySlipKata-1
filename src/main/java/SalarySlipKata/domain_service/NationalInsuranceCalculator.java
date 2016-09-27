package SalarySlipKata.domain_service;

import static java.lang.Double.MAX_VALUE;
import static SalarySlipKata.domain.Money.zero;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.bands.Band;
import SalarySlipKata.domain.Money;

public class NationalInsuranceCalculator {

  private static Band HIGHER_CONTRIBUTIONS = new Band(new Money(43_000.00), new Money(MAX_VALUE), 0.02);
  private static Band BASIC_CONTRIBUTIONS  = new Band(new Money( 8_060.00), new Money(43_000.00), 0.12);
  private static Band NO_CONTRIBUTIONS     = new Band(new Money(     0.00), new Money(8_060.00 ), 0.00);

  private List<Band> contributionBands = new ArrayList<Band>() {
    { add(HIGHER_CONTRIBUTIONS); }
    { add(BASIC_CONTRIBUTIONS);  }
    { add(NO_CONTRIBUTIONS);     }
  };

  public Money calculateContributionsFor(Money annualSalary) {
    Money contributions = zero();

    for(Band contributionBand: contributionBands) {
      contributions = contributions.plus(
          contributionBand.calculateFrom(annualSalary)
      );
    }

    return contributions;
  }
}
