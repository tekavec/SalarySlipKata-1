package SalarySlipKata.domain.bands;

import SalarySlipKata.domain.Money;

public class TaxBand extends StandardBand  {
  public TaxBand(Money lowerLimit, Money upperLimit, double rate) {
    super(lowerLimit, upperLimit, rate);
  }
}
