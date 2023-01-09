package prog.ex15.solution.i18ncountries;

import java.time.LocalDate;
import java.util.ResourceBundle;
import prog.ex15.exercise.i18ncountries.TypicalCountry;

public class TypicalBundleManager implements TypicalCountry {

  private ResourceBundle bundleCurrentlyBeingEdited;

  public TypicalBundleManager(ResourceBundle bundleCurrentlyBeingEdited) {
    this.bundleCurrentlyBeingEdited = bundleCurrentlyBeingEdited;
  }

  public TypicalBundleManager() {
    this.bundleCurrentlyBeingEdited = ResourceBundle.getBundle("prog.ex15.solution.i18ncountries.TypicalBundle");
  }

  /**
   * Setter for the maximum velocity on streets.
   *
   * @param velocity maximum allowed speed. If there is no maximum, the recommended velocity should
   *                 be used.
   * @param unit     unit for the velocity, e.g. "km/h" in Europe, "mph" in USA
   */
  @Override
  public void setVelocity(int velocity, String unit) {
    Object currentVelocity = bundleCurrentlyBeingEdited.getObject(TypicalCountry.VELOCITY);
    currentVelocity = velocity;

    String currentVelocityUnit = bundleCurrentlyBeingEdited.getString(TypicalCountry.VELOCITY_UNIT);
    currentVelocityUnit = unit;

  }

  /**
   * Number of people living in this country.
   *
   * @param population number of people
   */
  @Override
  public void setPopulation(int population) {
    Object currentPopulation = bundleCurrentlyBeingEdited.getObject(TypicalCountry.POPULATION);

    currentPopulation = population;
  }

  /**
   * Most famous meal the country is known for.
   *
   * @param mostFamousMeal Name of the most famous meal
   */
  @Override
  public void setMostFamousMeal(String mostFamousMeal) {
    String currentMostFamousMeal = bundleCurrentlyBeingEdited.getString(TypicalCountry.MOST_FAMOUS_MEAL);

    currentMostFamousMeal = mostFamousMeal;
  }

  /**
   * Most important holiday in this country.
   *
   * @param date        date of the current year the holiday takes place
   * @param holidayName Name of the holiday
   */
  @Override
  public void setMostImportantHoliday(LocalDate date, String holidayName) {
    Object currentMostImportantHoliday = bundleCurrentlyBeingEdited.getObject(TypicalCountry.MOST_IMPORTANT_HOLIDAY_DATE);

    currentMostImportantHoliday = date;

    String currentName = bundleCurrentlyBeingEdited.getString(TypicalCountry.MOST_IMPORTANT_HOLIDAY_NAME);
    currentName = holidayName;
  }
}
