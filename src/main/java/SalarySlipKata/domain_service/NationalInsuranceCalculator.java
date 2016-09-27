package SalarySlipKata.domain_service;

import static java.lang.Double.MAX_VALUE;
import static SalarySlipKata.domain.Money.zero;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.bands.StandardBand;
import SalarySlipKata.domain.Money;

public class NationalInsuranceCalculator {

  private StandardBand higherContributions = new StandardBand(new Money(43_000.00), new Money(MAX_VALUE), 0.02);
  private StandardBand basicContributions = new StandardBand(new Money( 8_060.00), new Money(43_000.00), 0.12);
  private StandardBand noContributions = new StandardBand(new Money(     0.00), new Money(8_060.00 ), 0.00);

  private List<StandardBand> niContributionBands = new ArrayList<StandardBand>() {
    { add(higherContributions); }
    { add(basicContributions);  }
    { add(noContributions);     }
  };

  public Money calculateContributionsFor(Money annualSalary) {
    Money contributions = zero();

    for(StandardBand niContributionBand : niContributionBands) {
      final Money contributionForTheBand = niContributionBand.calculateFrom(annualSalary);
      contributions = contributions.plus(contributionForTheBand);
    }

    return contributions;
  }
}
