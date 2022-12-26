package prog.ex15.solution.i18countries.multilingual.listresourcebundle;

import java.time.LocalDate;
import prog.ex15.exercise.i18ncountries.TypicalCountry;

public class CountryBundle_nl_NL extends CountryBundle {
  private Object[][] contents = {
      {TypicalCountry.VELOCITY, 120},
      {TypicalCountry.VELOCITY_UNIT, "km/h"},
      {TypicalCountry.POPULATION, 17500000 },
      {TypicalCountry.MOST_IMPORTANT_HOLIDAY_NAME, LocalDate.of(2022,4,27)},
      {TypicalCountry.MOST_IMPORTANT_HOLIDAY_NAME, "Koningsdag"},
      {TypicalCountry.MOST_FAMOUS_MEAL, "Pannekoken"}
  };

  @Override
  public Object[][] getContents() {
    return contents;
  }
}
