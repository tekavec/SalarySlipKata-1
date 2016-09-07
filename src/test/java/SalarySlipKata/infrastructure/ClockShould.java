package SalarySlipKata.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static java.time.LocalDate.of;

import java.time.LocalDate;

import org.junit.Test;

public class ClockShould {
  @Test public void
  return_todays_date_in_the_format_dd_MMM_yyyy() {
    assertThat(new TestableClock().todayAsString(), is("06 Sep 2016"));
  }

  private class TestableClock extends Clock {
    @Override
    protected LocalDate today() {
      return of(2016, 9,6);
    }
  }
}
