package prog.ex15.solution.i18countries.multilingual.gui;

import java.util.Arrays;
import java.util.Locale;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import prog.ex15.exercise.i18ncountries.Country;
import prog.ex15.exercise.i18ncountries.CountryKnowledgeContainer;
import prog.ex15.solution.i18countries.SingletonConfiguration;

public class FxKnowledgePresenter extends VBox {
  private ChoiceBox<Country> countrySelection;
  private FxKnowledgeAccordion accordion;

  /**
   * Creates a {@code VBox} layout with {@code spacing = 0} and alignment at {@code TOP_LEFT}.
   */
  public FxKnowledgePresenter(CountryKnowledgeContainer countryKnowledgeContainer) {
    this.countrySelection = new ChoiceBox<Country>();
    ObservableList<Country> countryObservableList = FXCollections.observableList(Arrays.asList(Country.values()));
    countrySelection.setItems(countryObservableList);
    countrySelection.setValue(Country.ENGLAND); //set default Value
    accordion = new FxKnowledgeAccordion(countryKnowledgeContainer);
    this.getChildren().add(countrySelection);
    this.getChildren().add(accordion);

    countrySelection.setOnAction(event -> setNewLocale());

  }

  private void setNewLocale() {
    SingletonConfiguration.getInstance().setLocale(SingletonConfiguration.getInstance().getCountry2LocaleMap().get(countrySelection.getValue()));
  }
}
