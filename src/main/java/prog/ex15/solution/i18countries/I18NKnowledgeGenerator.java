package prog.ex15.solution.i18countries;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.ResourceBundle;
import prog.ex15.exercise.i18ncountries.Category;
import prog.ex15.exercise.i18ncountries.CountryKnowledgeContainer;
import prog.ex15.exercise.i18ncountries.KnowledgeGenerator;
import prog.ex15.exercise.i18ncountries.TypicalCountry;

/**
 * Simple, straight-forward implementation of the KnowledgeGenerator interface for multiple
 * countries.
 */
public class I18NKnowledgeGenerator implements KnowledgeGenerator {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(I18NKnowledgeGenerator.class);

  @Override
  public CountryKnowledgeContainer fillContainer() {
    CountryKnowledgeContainer container = new CountryKnowledgeContainer();
    SingletonConfiguration instance = SingletonConfiguration.getInstance();
    ResourceBundle currentTypicalBundle = instance.getTypicalBundle();
    ResourceBundle currentMessageBundle = instance.getMessageBundle();

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(instance.getLocale());
    NumberFormat numberFormat = NumberFormat.getNumberInstance(instance.getLocale());

    container.addKnowledge(Category.TRAFFIC, MessageFormat.format(currentMessageBundle.getString("traffic.maximum.speed.highways"), currentTypicalBundle.getObject(TypicalCountry.VELOCITY), currentTypicalBundle.getString(TypicalCountry.VELOCITY_UNIT)));
    container.addKnowledge(Category.FOOD, MessageFormat.format(currentMessageBundle.getString("food.most.prominent.food"), currentTypicalBundle.getString(TypicalCountry.MOST_FAMOUS_MEAL)));
    LocalDate holiday_date = (LocalDate) currentTypicalBundle.getObject(TypicalCountry.MOST_IMPORTANT_HOLIDAY_DATE);
    container.addKnowledge(Category.HOLIDAYS,
        MessageFormat.format(currentMessageBundle.getString("holiday.most.important.holiday"), currentTypicalBundle.getString(TypicalCountry.MOST_IMPORTANT_HOLIDAY_NAME), holiday_date.format(dateTimeFormatter)));

    int population = (int) currentTypicalBundle.getObject(TypicalCountry.POPULATION);
    container.addKnowledge(Category.STATISTICS, MessageFormat.format(currentMessageBundle.getString("statistics.population"),
        numberFormat.format(population)));
    return container;
  }
}
