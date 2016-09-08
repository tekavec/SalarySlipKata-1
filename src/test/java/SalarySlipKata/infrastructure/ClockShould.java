package SalarySlipKata.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static java.time.LocalDate.of;
import static SalarySlipKata.TestConstants.SALARY_SLIP_PRINT_DATE_AS_STRING;

import java.time.LocalDate;

import org.junit.Test;

public class ClockShould {
  @Test public void
  return_todays_date_as_a_dd_MMM_yyyy_formatted_string() {
    Clock clock = new TestableClock();

    assertThat(clock.todayAsString(), is(SALARY_SLIP_PRINT_DATE_AS_STRING));
  }

  private class TestableClock extends Clock {
    @Override
    public LocalDate today() {
      return of(2016, 9, 1);
    }
  }
}
