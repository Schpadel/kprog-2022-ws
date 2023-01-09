package prog.ex15.solution.i18ncountries.multilingual.gui;

import java.util.List;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import prog.ex15.exercise.i18ncountries.Category;
import prog.ex15.exercise.i18ncountries.CountryKnowledgeContainer;
import prog.ex15.solution.i18ncountries.I18nKnowledgeGenerator;
import prog.ex15.solution.i18ncountries.SingletonConfiguration;

/**
 * JavaFX component presenting the content of a CountryKnowledgeContainer.
 */
public class FxKnowledgeAccordion extends Accordion {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(FxKnowledgeAccordion.class);

  CountryKnowledgeContainer countryKnowledgeContainer;

  /**
   * Construct new FxKnowledgeAccordion from container.
   *
   * @param countryKnowledgeContainer to be used for construction
   */

  public FxKnowledgeAccordion(final CountryKnowledgeContainer countryKnowledgeContainer) {
    this.countryKnowledgeContainer = countryKnowledgeContainer;
    fillAccordion();
    SingletonConfiguration.getInstance().addPropertyChangeListener(evt -> refreshAccordion());
  }

  private void refreshAccordion() {
    I18nKnowledgeGenerator i18nKnowledgeGenerator = new I18nKnowledgeGenerator();
    this.countryKnowledgeContainer = i18nKnowledgeGenerator.fillContainer();
    logger.info("Refreshed knowledgeContainer!");
    fillAccordion();
  }

  private void fillAccordion() {
    this.getPanes().clear();
    for (Category category : Category.values()) {
      TitledPane titledPane = new TitledPane();
      titledPane.setText(SingletonConfiguration.getInstance()
          .getMessageBundle().getString("categories." + category.toString()));
      List<String> knowledgeList = countryKnowledgeContainer.getKnowledge(category);
      VBox box = new VBox();
      for (String string : knowledgeList) {
        box.getChildren().add(new Label(string));
        logger.info("Adding label " + string);
      }
      titledPane.setContent(box);
      this.getPanes().add(titledPane);
    }
  }
}
