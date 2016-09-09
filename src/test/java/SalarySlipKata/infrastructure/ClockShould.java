package SalarySlipKata.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.LocalDate;

import org.junit.Test;

public class ClockShould {
  @Test public void
  return_a_dd_MMM_yyyy_formatted_current_date() {
    Clock clock = new TestableClock();

    assertThat(clock.todayAsString(), is("08 Sep 2016"));
  }

  private class TestableClock extends Clock {
    @Override
    protected LocalDate currentDate() {
      return LocalDate.of(2016, 9, 8);
    }
  }
}
