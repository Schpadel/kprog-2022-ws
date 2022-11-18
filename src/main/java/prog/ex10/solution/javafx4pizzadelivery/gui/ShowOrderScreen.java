package prog.ex10.solution.javafx4pizzadelivery.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
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

/**
 * Screen to show the list of pizzas of an order of the PizzaDeliveryService. It is also possible to
 * add, change and remove pizzas.
 */
public class ShowOrderScreen extends VBox implements Initializable {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(ShowOrderScreen.class);

  public static final String SCREEN_NAME = "ShowOrderScreen";

  private PizzaDeliveryService service;

  @FXML
  Label priceValue;
  @FXML
  Label orderId;
  @FXML
  ChoiceBox<PizzaSize> availablePizzaSizes;
  @FXML
  ListView<Pizza> orderedPizzas;
  @FXML
  Button orderButton;
  @FXML
  Button addPizza;
  @FXML
  Button cancelButton;

  // init Properties and observable data structures

  SimpleIntegerProperty orderPriceProperty;
  SimpleIntegerProperty orderIdProperty;

  ObservableList<PizzaSize> availablePizzaSizeList;
  ObservableList<Pizza> observableCurrentPizzaList;
  PizzaDeliveryScreenController controller;

  /**
   * Constructs a new ShowOrderScreen.
   *
   * @param screenController for this screen
   */
  public ShowOrderScreen(PizzaDeliveryScreenController screenController) {
    service = (PizzaDeliveryService) SingletonAttributeStore.getInstance()
        .getAttribute("PizzaDeliveryService");
    orderPriceProperty = new SimpleIntegerProperty();
    orderIdProperty = new SimpleIntegerProperty();
    availablePizzaSizeList = FXCollections.observableList(
        new ArrayList<>(service.getPizzaSizePriceList().keySet()));
    observableCurrentPizzaList = FXCollections.observableList(new ArrayList<>());

    controller = screenController;
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ShowOrderScreen.fxml"));
    //fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);
    try {
      Parent root = fxmlLoader.load();
      this.getChildren().clear();
      this.getChildren().addAll(root);
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Handler method when the button to add a new pizza is pressed. Adds a new pizza to the order of
   * the service and calls updateScreen.
   */
  public void addPizza() {
    service.addPizza((Integer) SingletonAttributeStore.getInstance().getAttribute("orderId"),
        availablePizzaSizes.getValue());
    updateScreen();
  }

  /**
   * Handler method when the order button is pressed. Orders the pizzas (not implemented in the
   * service) and returns to the CreateOrderScreen.
   */
  public void orderPizza() {
    try {
      controller.switchTo(this.SCREEN_NAME, CreateOrderScreen.SCREEN_NAME);
    } catch (UnknownTransitionException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates the entire screen and fills it with the new values provided by the service.
   */
  public void updateScreen() {
    Order currentOrder = service.getOrder(
        (Integer) SingletonAttributeStore.getInstance().getAttribute("orderId"));
    observableCurrentPizzaList.clear();
    observableCurrentPizzaList.addAll(currentOrder.getPizzaList());
    orderPriceProperty.set(currentOrder.getValue());
    orderIdProperty.set(currentOrder.getOrderId());

    //Maybe rework / find better solution?
    observableCurrentPizzaList.addListener(
        (ListChangeListener<? super Pizza>) c -> orderPriceProperty.set(service.getOrder(
            (Integer) SingletonAttributeStore.getInstance().getAttribute("orderId")).getValue()));
  }

  /**
   * Cancels the current order and returns to the CreateOrderScreen.
   */
  public void cancelOrder() {
    try {
      controller.switchTo(ShowOrderScreen.SCREEN_NAME, CreateOrderScreen.SCREEN_NAME);
    } catch (UnknownTransitionException e) {
      e.printStackTrace();
    }
  }


  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location  The location used to resolve relative paths for the root object, or
   *                  {@code null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if the root
   *                  object was not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    priceValue.textProperty().bind(orderPriceProperty.asString());
    orderId.textProperty().bind(orderIdProperty.asString());
    availablePizzaSizes.setItems(availablePizzaSizeList);
    orderedPizzas.setItems(observableCurrentPizzaList);
    orderedPizzas.setCellFactory(list -> new PizzaListCell(observableCurrentPizzaList,
        service, controller));
  }

  static class PizzaListCell extends ListCell<Pizza> {

    private final ObservableList<Pizza> pizzas;
    private PizzaDeliveryService service;
    private PizzaDeliveryScreenController controller;

    public PizzaListCell(final ObservableList<Pizza> pizzas, PizzaDeliveryService service,
        PizzaDeliveryScreenController controller) {
      this.pizzas = pizzas;
      this.service = service;
      this.controller = controller;
    }

    @Override
    protected void updateItem(final Pizza pizza, final boolean empty) {
      super.updateItem(pizza, empty);
      if (empty || pizza == null) {
        textProperty().setValue(null);
        setGraphic(null);
      } else {
        VBox verticalBox = new VBox();
        Label nameLabel = new Label(
            pizza.getSize().toString() + ", " + pizza.getToppings().size() + " Toppings");
        verticalBox.getChildren().add(nameLabel);
        Button changeButton = new Button("change");
        changeButton.setOnAction(event -> {
          try {
            SingletonAttributeStore.getInstance().setAttribute("pizzaId", pizza.getPizzaId());
            controller.switchTo(ShowOrderScreen.SCREEN_NAME, EditPizzaScreen.SCREEN_NAME);
          } catch (UnknownTransitionException e) {
            e.printStackTrace();
          }
        });

        Button removeButton = new Button("remove");
        removeButton.setId("remove-" + pizza);
        removeButton.setOnAction((event -> removeSelectedPizza(pizza.getPizzaId())));
        Pane spacer = new Pane();
        spacer.setMinSize(10, 1);
        HBox horizontalBox = new HBox();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        horizontalBox.getChildren().addAll(verticalBox, spacer, changeButton, removeButton);
        setGraphic(horizontalBox);
      }
    }

    private void removeSelectedPizza(int pizzaId) {
      int orderId = (Integer) SingletonAttributeStore.getInstance().getAttribute("orderId");
      try {
        service.removePizza(orderId, pizzaId);
      } catch (IllegalArgumentException e) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Illegal Argument");
        alert.setHeaderText("Illegal Argument Exception");
        alert.setContentText(e.getMessage());
        alert.showAndWait();
      }

      pizzas.clear();
      pizzas.addAll(service.getOrder(orderId).getPizzaList());

    }

  }
}
