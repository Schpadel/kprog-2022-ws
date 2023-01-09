package prog.ex15.solution.i18ncountries.multilingual.gui;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.VBox;
import prog.ex15.exercise.i18ncountries.Country;
import prog.ex15.exercise.i18ncountries.CountryKnowledgeContainer;
import prog.ex15.solution.i18ncountries.SingletonConfiguration;

/**
 * FX Presenter Class to show Country Selection + Knowledge Accordion.
 */
public class FxKnowledgePresenter extends VBox {
  private ChoiceBox<String> countrySelection;
  private FxKnowledgeAccordion accordion;

  private ObservableList<String> countryObservableList;
  private List<String> localizedCountryNameList;
  private EventHandler<ActionEvent> handler;

  /**
   * Creates a {@code VBox} layout with {@code spacing = 0} and alignment at {@code TOP_LEFT}.
   */
  public FxKnowledgePresenter(CountryKnowledgeContainer countryKnowledgeContainer) {
    this.countrySelection = new ChoiceBox<String>();
    localizedCountryNameList = new ArrayList<>();
    countryObservableList = FXCollections.observableList(localizedCountryNameList);
    updateCountryNames();
    countrySelection.setItems(countryObservableList);
    countrySelection.setValue("England"); //set default Value
    accordion = new FxKnowledgeAccordion(countryKnowledgeContainer);
    this.getChildren().add(countrySelection);
    this.getChildren().add(accordion);

    handler = event -> handleCountrySelection();
    countrySelection.setOnAction(handler);
  }

  private void handleCountrySelection() {
    setNewLocale();
    updateCountryNames();
  }
  public void updateCountryNames() {
    int index = countrySelection.getSelectionModel().getSelectedIndex();
    countrySelection.setOnAction(null);
    ResourceBundle currentMessageBundle = SingletonConfiguration.getInstance().getMessageBundle();
    countryObservableList.clear();

    for(Country value : Country.values()) {
      countryObservableList.add(currentMessageBundle.getString("country." + value.toString()));
    }
    countrySelection.getSelectionModel().select(index);
    countrySelection.setOnAction(handler);
  }

  private void setNewLocale() {
    int index = localizedCountryNameList.indexOf(countrySelection.getValue());
    SingletonConfiguration.getInstance().setLocale(SingletonConfiguration.getInstance()
        .getCountry2LocaleMap().get(Country.values()[index])); // TODO: fixed?
  }
}
