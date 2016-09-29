package com.codurance.salaryslip.national_insurance;

import static java.lang.Double.MAX_VALUE;

import java.util.ArrayList;
import java.util.List;

import com.codurance.salaryslip.components.Money;

public class NationalInsuranceCalculator {

  private NationalInsuranceBand higherContributions = new NationalInsuranceBand(new Money(43_000.00), new Money(MAX_VALUE), 0.02);
  private NationalInsuranceBand basicContributions  = new NationalInsuranceBand(new Money( 8_060.00), new Money(43_000.00), 0.12);
  private NationalInsuranceBand noContributions     = new NationalInsuranceBand(new Money(     0.00), new Money(8_060.00 ), 0.00);

  private List<NationalInsuranceBand> niContributionBands = new ArrayList<>();

  public NationalInsuranceCalculator() {
    populateNIContributionBands();
  }

  private void populateNIContributionBands() {
    niContributionBands.add(higherContributions);
    niContributionBands.add(basicContributions);
    niContributionBands.add(noContributions);
  }

  public Money calculateContributionsFor(Money annualSalary) {
    Money contributions = Money.zero();

    for(NationalInsuranceBand niContributionBand : niContributionBands) {
      final Money contributionForTheBand = niContributionBand.calculateFrom(annualSalary);
      contributions = contributions.plus(contributionForTheBand);
    }

    return contributions;
  }
}
