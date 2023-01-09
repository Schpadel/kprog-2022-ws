package prog.ex15.solution.i18ncountries;

import java.time.LocalDate;
import prog.ex15.exercise.i18ncountries.TypicalCountry;

public class TypicalBundle_dk_DK extends TypicalBundle {
  private Object[][] contents = {
      {TypicalCountry.VELOCITY, 130},
      {TypicalCountry.VELOCITY_UNIT, "km/h"},
      {TypicalCountry.POPULATION, 5840000 },
      {TypicalCountry.MOST_IMPORTANT_HOLIDAY_NAME, LocalDate.of(2022,6,5)},
      {TypicalCountry.MOST_IMPORTANT_HOLIDAY_NAME, "Grundlovsdag"},
      {TypicalCountry.MOST_FAMOUS_MEAL, "knækbrød"}
  };

  @Override
  public Object[][] getContents() {
    return contents;
  }
}
