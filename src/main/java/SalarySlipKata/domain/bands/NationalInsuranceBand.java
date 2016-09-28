package SalarySlipKata.domain.bands;

import SalarySlipKata.domain.Money;

public class NationalInsuranceBand extends StandardBand {

  public NationalInsuranceBand(Money lowerLimit, Money upperLimit, double rate) {
    super(lowerLimit, upperLimit, rate);
  }
}
