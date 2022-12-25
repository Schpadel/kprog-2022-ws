package prog.ex15.solution.i18countries.multilingual.listresourcebundle;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.ListResourceBundle;
import prog.ex15.exercise.i18ncountries.TypicalCountry;

public class CountryBundle extends ListResourceBundle implements TypicalCountry {

  private Object[][] contents = {
      {TypicalCountry.VELOCITY, 70},
      {TypicalCountry.VELOCITY_UNIT, "mph"},
      {TypicalCountry.POPULATION, 66500000},
      {TypicalCountry.MOST_IMPORTANT_HOLIDAY_NAME, LocalDate.of(2022,01,31)},
      {TypicalCountry.MOST_IMPORTANT_HOLIDAY_NAME, "Brexit Day (Joke)"},
      {TypicalCountry.MOST_FAMOUS_MEAL, "fish and chips"}
  };

  public Object[][] getContents() {
    return contents;
  }

  @Override
  public void setVelocity(int velocity, String unit) {
    Object currentVelocity = getObject(TypicalCountry.VELOCITY);
    currentVelocity = velocity;
  }

  @Override
  public void setPopulation(int population) {

  }

  @Override
  public void setMostFamousMeal(String mostFamousMeal) {

  }

  @Override
  public void setMostImportantHoliday(LocalDate date, String holidayName) {

  }
}
