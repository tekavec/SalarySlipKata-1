package SalarySlipKata.infrastructure;

import static java.time.LocalDate.now;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Clock {

  private static final DateTimeFormatter DD_MMM_YYYYY_FORMATTER = ofPattern("dd MMM yyyy");

  public String todayAsString() {
    return currentDate().format(DD_MMM_YYYYY_FORMATTER);
  }

  protected LocalDate currentDate() {return now();}
}
