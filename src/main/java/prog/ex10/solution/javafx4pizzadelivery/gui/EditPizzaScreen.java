package prog.ex10.solution.javafx4pizzadelivery.gui;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import prog.ex10.exercise.javafx4pizzadelivery.gui.UnknownTransitionException;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Order;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Pizza;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaDeliveryService;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaSize;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.TooManyToppingsException;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Topping;


/**
 * Screen to edit the toppings on a pizza of the PizzaDeliveryService.
 */
public class EditPizzaScreen extends VBox implements PizzaDeliveryScreen {

  public static final String SCREEN_NAME = "EditPizzaScreen";
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(EditPizzaScreen.class);
  // Choice Box Elements
  ChoiceBox<Topping> toppingChoiceBox;
  ObservableList<Topping> observableToppingChoiceList;
  Button addToppingButton;
  //List View Elements
  ListView<Topping> toppingsOnPizzaListView;
  ObservableList<Topping> observableCurrentToppingList;
  Button finishButton;
  private Label pizzaSizeLabel;
  private SimpleObjectProperty<PizzaSize> pizzaSizeProperty;
  private Label priceLabel;
  private SimpleIntegerProperty pizzaPriceProperty;
  private PizzaDeliveryService service;
  private int orderId;
  private int pizzaId;
  private PizzaDeliveryScreenController controller;

  public EditPizzaScreen(PizzaDeliveryScreenController screenController) {
    controller = screenController;
  }

  /**
   * Updates the screen and all observable data structures from the current state of the service.
   */
  @Override
  public void updateScreen() {
    this.getChildren().clear();
    SingletonAttributeStore singletonAttributeStore = SingletonAttributeStore.getInstance();
    service = (PizzaDeliveryService) singletonAttributeStore.getAttribute("PizzaDeliveryService");
    orderId = (int) singletonAttributeStore.getAttribute("orderId");
    pizzaId = (int) singletonAttributeStore.getAttribute("pizzaId");

    pizzaSizeLabel = new Label();
    pizzaSizeProperty = new SimpleObjectProperty<>();
    priceLabel = new Label();
    pizzaPriceProperty = new SimpleIntegerProperty();
    toppingChoiceBox = new ChoiceBox<>();
    addToppingButton = new Button("Add the selected Topping");
    finishButton = new Button("Finish Configuration");
    toppingsOnPizzaListView = new ListView<>();

    Order currentOrder = service.getOrder(orderId);
    Pizza currentPizza = currentOrder.getPizzaList().stream()
        .filter(pizza -> pizza.getPizzaId() == pizzaId).findFirst().get();

    pizzaSizeProperty.set(currentPizza.getSize());
    pizzaPriceProperty.set(currentPizza.getPrice());

    observableToppingChoiceList = FXCollections.observableList(
        new ArrayList<>(service.getToppingsPriceList().keySet()));
    observableCurrentToppingList = FXCollections.observableList(new ArrayList<>());
    //load initial status of pizza toppings
    observableCurrentToppingList.addAll(currentPizza.getToppings());

    // set IDs for ASB and FXTest
    pizzaSizeLabel.setId("pizzaSizeLabel");
    priceLabel.setId("priceLabel");
    toppingChoiceBox.setId("toppingChoiceBox");
    addToppingButton.setId("addToppingButton");
    toppingsOnPizzaListView.setId("toppingsOnPizzaListView");

    // build UI Bindings
    pizzaSizeLabel.textProperty().bind(pizzaSizeProperty.asString());
    priceLabel.textProperty().bind(pizzaPriceProperty.asString());

    toppingChoiceBox.setItems(observableToppingChoiceList);
    addToppingButton.setOnAction(event -> addSelectedTopping(service, pizzaId, currentPizza));

    toppingsOnPizzaListView.setCellFactory(list -> new ToppingListCell(observableCurrentToppingList,
        service, pizzaId, currentPizza));
    toppingsOnPizzaListView.setItems(observableCurrentToppingList);
    finishButton.setOnAction(event -> {
      try {
        controller.switchTo(EditPizzaScreen.SCREEN_NAME, ShowOrderScreen.SCREEN_NAME);
      } catch (UnknownTransitionException e) {
        e.printStackTrace();
      }
    });

    observableCurrentToppingList.addListener(
        (ListChangeListener<? super Topping>) c -> pizzaPriceProperty.set(currentPizza.getPrice()));
    // add UI Elements
    getChildren().add(pizzaSizeLabel);
    getChildren().add(priceLabel);
    getChildren().add(toppingChoiceBox);
    getChildren().add(addToppingButton);
    getChildren().add(toppingsOnPizzaListView);
    getChildren().add(finishButton);
  }

  private void addSelectedTopping(PizzaDeliveryService service, int pizzaId, Pizza currentPizza) {
    try {
      // Always load from single point of truth the full list again
      service.addTopping(pizzaId, toppingChoiceBox.getValue());
      observableCurrentToppingList.clear();
      observableCurrentToppingList.addAll(currentPizza.getToppings());
      pizzaPriceProperty.set(currentPizza.getPrice());

    } catch (TooManyToppingsException e) {
      Alert alert = new Alert(AlertType.ERROR);
      alert.setTitle("Too many Toppings already added!");
      alert.setHeaderText("Way too many Toppings");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
    } catch (IllegalArgumentException e) {
      System.err.println("Can not add NULL Topping, please choose a topping!");
    }
  }

  static class ToppingListCell extends ListCell<Topping> {

    private final ObservableList<Topping> toppings;
    int pizzaId;
    Pizza currentPizza;
    private PizzaDeliveryService service;

    public ToppingListCell(final ObservableList<Topping> toppings, PizzaDeliveryService service,
        int pizzaId, Pizza currentPizza) {
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
        spacer.setMinSize(10, 1);
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

      toppings.clear();
      toppings.addAll(currentPizza.getToppings());

      // Price is now being updated by action listener on observable list
      //pizzaPriceProperty.set(currentPizza.getPrice());
    }

  }
}

