package org.neomatrix369.salaryslip.national_insurance;

import static org.neomatrix369.salaryslip.national_insurance.NationalInsuranceBand.BASIC_CONTRIBUTIONS_BAND;
import static org.neomatrix369.salaryslip.national_insurance.NationalInsuranceBand.HIGHER_CONTRIBUTIONS_BAND;

import org.neomatrix369.salaryslip.components.Money;

public class NationalInsuranceCalculator {
  private static final int TWELVE_MONTHS = 12;

  public Money calculateMonthlyContributionsFor(Money annualSalary) {
    final Money higherContributions = HIGHER_CONTRIBUTIONS_BAND.calculateContributionsFor(annualSalary);
    final Money basicContributions = BASIC_CONTRIBUTIONS_BAND.calculateContributionsFor(annualSalary);

    return basicContributions.add(higherContributions).divideBy(TWELVE_MONTHS);
  }
}
