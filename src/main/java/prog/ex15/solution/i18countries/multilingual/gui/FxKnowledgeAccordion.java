package prog.ex15.solution.i18countries.multilingual.gui;

import java.util.List;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import prog.ex15.exercise.i18ncountries.Category;
import prog.ex15.exercise.i18ncountries.CountryKnowledgeContainer;
import prog.ex15.solution.i18countries.I18NKnowledgeGenerator;
import prog.ex15.solution.i18countries.SingletonConfiguration;

/**
 * JavaFX component presenting the content of a CountryKnowledgeContainer.
 */
public class FxKnowledgeAccordion extends Accordion {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(FxKnowledgeAccordion.class);

  CountryKnowledgeContainer countryKnowledgeContainer;

  public FxKnowledgeAccordion(final CountryKnowledgeContainer countryKnowledgeContainer) {
    this.countryKnowledgeContainer = countryKnowledgeContainer;
    fillAccordion();
    SingletonConfiguration.getInstance().addPropertyChangeListener(evt -> refreshAccordion());
  }

  private void refreshAccordion() {
    I18NKnowledgeGenerator i18NKnowledgeGenerator = new I18NKnowledgeGenerator();
    this.countryKnowledgeContainer = i18NKnowledgeGenerator.fillContainer();
    logger.info("Refreshed knowledgeContainer!");
    fillAccordion();
  }

  private void fillAccordion() {
    this.getPanes().clear();
    for (Category category : Category.values()) {
      TitledPane titledPane = new TitledPane();
      titledPane.setText(SingletonConfiguration.getInstance().getMessageBundle().getString("categories." + category.toString()));
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
