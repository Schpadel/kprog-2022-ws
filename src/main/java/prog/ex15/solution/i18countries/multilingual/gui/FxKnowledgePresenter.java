package prog.ex15.solution.i18countries.multilingual.gui;

import java.util.Arrays;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import prog.ex15.exercise.i18ncountries.Country;
import prog.ex15.exercise.i18ncountries.CountryKnowledgeContainer;

public class FxKnowledgePresenter extends VBox {
  private ChoiceBox<Country> countrySelection;
  private FxKnowledgeAccordion accordion;

  /**
   * Creates a {@code VBox} layout with {@code spacing = 0} and alignment at {@code TOP_LEFT}.
   */
  public FxKnowledgePresenter(CountryKnowledgeContainer countryKnowledgeContainer) {
    this.countrySelection = new ChoiceBox<Country>();
    countrySelection.setItems(FXCollections.observableList(Arrays.asList(Country.values())));
    countrySelection.setValue(Country.ENGLAND); //set default Value
    accordion = new FxKnowledgeAccordion(countryKnowledgeContainer);
    this.getChildren().add(countrySelection);
    this.getChildren().add(accordion);
  }
}
