package SalarySlipKata.domain_service;

import static java.lang.Double.MAX_VALUE;
import static SalarySlipKata.domain.Money.zero;

import java.util.ArrayList;
import java.util.List;

import SalarySlipKata.domain.Money;
import SalarySlipKata.domain.bands.Band;
import SalarySlipKata.domain.bands.NationalInsuranceBand;

public class NationalInsuranceCalculator {

  private Band higherContributions = new NationalInsuranceBand(new Money(43_000.00), new Money(MAX_VALUE), 0.02);
  private Band basicContributions = new NationalInsuranceBand(new Money( 8_060.00), new Money(43_000.00), 0.12);
  private Band noContributions = new NationalInsuranceBand(new Money(     0.00), new Money(8_060.00 ), 0.00);

  private List<Band> niContributionBands = new ArrayList<>();

  public NationalInsuranceCalculator() {
    populateNIContributionBands();
  }

  private void populateNIContributionBands() {
    niContributionBands.add(higherContributions);
    niContributionBands.add(basicContributions);
    niContributionBands.add(noContributions);
  }

  public Money calculateContributionsFor(Money annualSalary) {
    Money contributions = zero();

    for(Band niContributionBand : niContributionBands) {
      final Money contributionForTheBand = niContributionBand.calculateFrom(annualSalary);
      contributions = contributions.plus(contributionForTheBand);
    }

    return contributions;
  }
}
