package SalarySlipKata.infrastructure;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import static SalarySlipKata.TestConstants.SALARY_SLIP_PRINT_DATE;

import java.time.LocalDate;

import org.junit.Test;

public class ClockShould {
  @Test public void
  return_todays_date_formatted_as_dd_MMM_yyyy() {
    Clock clock = new TestableClock();

    assertThat(clock.todayAsString(), is(SALARY_SLIP_PRINT_DATE));
  }

  private class TestableClock extends Clock {
    @Override
    protected LocalDate today() {
      return LocalDate.of(2016, 9, 1);
    }
  }
}
