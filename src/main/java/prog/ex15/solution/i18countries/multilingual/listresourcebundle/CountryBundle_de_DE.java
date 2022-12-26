package prog.ex15.solution.i18countries.multilingual.listresourcebundle;

import java.time.LocalDate;
import java.util.ListResourceBundle;
import prog.ex15.exercise.i18ncountries.TypicalCountry;

//TODO: fix inheritance and implementation of interface!
public class CountryBundle_de_DE extends ListResourceBundle implements TypicalCountry {
  private Object[][] contents = {
      {TypicalCountry.VELOCITY, 130},
      {TypicalCountry.VELOCITY_UNIT, "km/h"},
      {TypicalCountry.POPULATION, 83200000},
      {TypicalCountry.MOST_IMPORTANT_HOLIDAY_NAME, LocalDate.of(2022,10,3)},
      {TypicalCountry.MOST_IMPORTANT_HOLIDAY_NAME, "Tag der Deutschen Einheit"},
      {TypicalCountry.MOST_FAMOUS_MEAL, "Eisbein mit Sauerkraut"}
  };

  @Override
  public Object[][] getContents() {
    return contents;
  }

  @Override
  public void setVelocity(int velocity, String unit) {

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
