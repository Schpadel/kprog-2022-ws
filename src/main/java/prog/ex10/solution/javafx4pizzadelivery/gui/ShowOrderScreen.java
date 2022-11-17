package prog.ex10.solution.javafx4pizzadelivery.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
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
 * Screen to show the list of pizzas of an order of the PizzaDeliveryService. It is also possible
 * to add, change and remove pizzas.
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


  public ShowOrderScreen(PizzaDeliveryScreenController screenController) {
    service = (PizzaDeliveryService) SingletonAttributeStore.getInstance().getAttribute("PizzaDeliveryService");
    orderPriceProperty = new SimpleIntegerProperty();
    orderIdProperty = new SimpleIntegerProperty();
    availablePizzaSizeList = FXCollections.observableList(new ArrayList<>(service.getPizzaSizePriceList().keySet()));
    observableCurrentPizzaList = FXCollections.observableList(new ArrayList<>(service.getOrder(
        (Integer) SingletonAttributeStore.getInstance().getAttribute("orderId")).getPizzaList()));

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

  public void addPizza() {
    service.addPizza((Integer) SingletonAttributeStore.getInstance().getAttribute("orderId"), availablePizzaSizes.getValue());
    updateScreen();
  }

  public void orderPizza() {

  }

  public void updateScreen() {
    Order currentOrder = service.getOrder(
        (Integer) SingletonAttributeStore.getInstance().getAttribute("orderId"));
    observableCurrentPizzaList.clear();
    observableCurrentPizzaList.addAll(currentOrder.getPizzaList());
    orderPriceProperty.set(currentOrder.getValue());
    orderIdProperty.set(currentOrder.getOrderId());
  }

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

    PizzaDeliveryService service = (PizzaDeliveryService) SingletonAttributeStore.getInstance().getAttribute("PizzaDeliveryService");
    int orderId = (int) SingletonAttributeStore.getInstance().getAttribute("orderId");
    orderPriceProperty.set(service.getOrder(orderId).getValue());

  }

  static class PizzaListCell extends ListCell<Pizza> {

    private final ObservableList<Pizza> pizzas;
    private PizzaDeliveryService service;
    private PizzaDeliveryScreenController controller;
  public PizzaListCell(final ObservableList<Pizza> pizzas, PizzaDeliveryService service, PizzaDeliveryScreenController controller) {
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
      Label nameLabel = new Label(pizza.getSize().toString() + ", " + pizza.getToppings().size() + " Toppings");
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
      // TODO: add edit listener
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
