package prog.ex09.solution.editpizzascreen.gui;

import java.util.ArrayList;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import prog.ex09.exercise.editpizzascreen.pizzadelivery.Order;
import prog.ex09.exercise.editpizzascreen.pizzadelivery.Pizza;
import prog.ex09.exercise.editpizzascreen.pizzadelivery.PizzaDeliveryService;
import prog.ex09.exercise.editpizzascreen.pizzadelivery.PizzaSize;
import prog.ex09.exercise.editpizzascreen.pizzadelivery.Topping;
import prog.ex09.solution.editpizzascreen.pizzadelivery.SimplePizza;

/**
 * JavaFX component to edit a pizza configuration.
 */
public class EditPizzaScreen extends VBox {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(EditPizzaScreen.class);

  Label pizzaSizeLabel = new Label();
  SimpleObjectProperty<PizzaSize> pizzaSizeProperty = new SimpleObjectProperty<>();

  Label pizzaPriceLabel = new Label();
  SimpleIntegerProperty pizzaPriceProperty = new SimpleIntegerProperty();

  // Choice Box Elements
  ChoiceBox<Topping> toppingChoiceBox = new ChoiceBox<>();
  ObservableList<Topping> observableToppingChoiceList;
  Button addToppingButton = new Button("Add the selected Topping");

  //List View Elements
  ListView<Topping> toppingsOnPizzaListView = new ListView<>();
  ObservableList<Topping> observableCurrentToppingList;
  Button finishButton = new Button("Finish Configuration");

  public EditPizzaScreen(PizzaDeliveryService service, final int orderId, int pizzaId) {

    Order currentOrder = service.getOrder(orderId);
    Pizza currentPizza = currentOrder.getPizzaList().stream().filter(pizza -> pizza.getPizzaId() == pizzaId).findFirst().get();

    pizzaSizeProperty.set(currentPizza.getSize());
    pizzaPriceProperty.set(currentPizza.getPrice());

    observableToppingChoiceList = FXCollections.observableList(
        new ArrayList<>(service.getToppingsPriceList().keySet()));
    observableCurrentToppingList = FXCollections.observableList(new ArrayList<>());

    // build UI Bindings
    pizzaSizeLabel.textProperty().bind(pizzaSizeProperty.asString());
    pizzaPriceLabel.textProperty().bind(pizzaPriceProperty.asString());

    toppingChoiceBox.setItems(observableToppingChoiceList);
    addToppingButton.setOnAction(event -> addSelectedTopping(service, pizzaId, currentPizza));

    toppingsOnPizzaListView.setCellFactory(list -> new ToppingListCell(observableCurrentToppingList,
        service, pizzaId, currentPizza));
    toppingsOnPizzaListView.setItems(observableCurrentToppingList);
    finishButton.setOnAction(event -> Platform.exit());

    // add UI Elements
    getChildren().add(pizzaSizeLabel);
    getChildren().add(pizzaPriceLabel);
    getChildren().add(toppingChoiceBox);
    getChildren().add(addToppingButton);
    getChildren().add(toppingsOnPizzaListView);
    getChildren().add(finishButton);
  }

  private void addSelectedTopping(PizzaDeliveryService service, int pizzaId, Pizza currentPizza) {
    try {
      service.addTopping(pizzaId, toppingChoiceBox.getValue());
      observableCurrentToppingList.add(toppingChoiceBox.getValue());
      pizzaPriceProperty.set(currentPizza.getPrice());
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Too many Toppings already added!");
      alert.setHeaderText("Way too many Toppings");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    }
  }

   class ToppingListCell extends ListCell<Topping> {
    private final ObservableList<Topping> toppings;
    private PizzaDeliveryService service;
    int pizzaId;
    Pizza currentPizza;

    public ToppingListCell(final ObservableList<Topping> toppings, PizzaDeliveryService service, int pizzaId, Pizza currentPizza) {
      this.toppings = toppings;
      this.service = service;
      this.pizzaId = pizzaId;
      this.currentPizza = currentPizza;
    }

    @Override
    protected void updateItem(final Topping topping, final boolean empty) {
      super.updateItem(topping, empty);
      if (empty || topping == null) {
        textProperty().setValue(null);
        setGraphic(null);
      } else {
        VBox verticalBox = new VBox();
        Label nameLabel = new Label(topping.name());
        verticalBox.getChildren().add(nameLabel);
        Button removeButton = new Button("remove topping");
        removeButton.setId("remove-" + topping);
        removeButton.setOnAction((event -> removeSelectedTopping(pizzaId, currentPizza, topping)));
        Pane spacer = new Pane();
        spacer.setMinSize(10,1);
        HBox horizontalBox = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        horizontalBox.getChildren().addAll(verticalBox, spacer, removeButton);
        setGraphic(horizontalBox);
      }
    }

    private void removeSelectedTopping(int pizzaId, Pizza currentPizza, Topping topping) {
      try {
        service.removeTopping(pizzaId, topping);
      } catch (IllegalArgumentException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Illegal Argument");
        alert.setHeaderText("Illegal Argument Exception");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
      }

      toppings.remove(topping);
      pizzaPriceProperty.set(currentPizza.getPrice());
    }

  }

}
