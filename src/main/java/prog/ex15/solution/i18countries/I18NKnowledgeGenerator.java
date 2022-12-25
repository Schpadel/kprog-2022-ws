package prog.ex15.solution.i18countries;

import java.text.MessageFormat;
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

    container.addKnowledge(Category.TRAFFIC, MessageFormat.format(currentMessageBundle.getString("traffic.maximum.speed.highways"), currentTypicalBundle.getObject(TypicalCountry.VELOCITY), currentTypicalBundle.getString(TypicalCountry.VELOCITY_UNIT)));
    container.addKnowledge(Category.FOOD, "Our most prominent food is Fish and Chips.");
    container.addKnowledge(Category.HOLIDAYS,
        "Our most important holiday is  Brexit Day (Joke) on January, the 1, 2022.");
    container.addKnowledge(Category.STATISTICS, "Our population is 66.500.000");
    return container;
  }
}
