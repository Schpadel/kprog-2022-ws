package prog.ex10.solution.javafx4pizzadelivery.gui;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Order;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Pizza;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaDeliveryService;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaSize;

public class ShowOrderScreenViewModel {
  PizzaDeliveryService service;
  SimpleIntegerProperty orderPriceProperty;
  SimpleIntegerProperty orderIdProperty;

  ObservableList<PizzaSize> availablePizzaSizeList;
  ObservableList<Pizza> observableCurrentPizzaList;

  public ShowOrderScreenViewModel() {
    service = (PizzaDeliveryService) SingletonAttributeStore.getInstance().getAttribute("PizzaDeliveryService");
    orderPriceProperty = new SimpleIntegerProperty();
    orderIdProperty = new SimpleIntegerProperty();
    availablePizzaSizeList = FXCollections.observableList(
        new ArrayList<>(service.getPizzaSizePriceList().keySet()));
    observableCurrentPizzaList = FXCollections.observableList(new ArrayList<>());
  }

  public void refreshDataFromService() {
    Order currentOrder = service.getOrder(
        (Integer) SingletonAttributeStore.getInstance().getAttribute("orderId"));
    observableCurrentPizzaList.clear();
    observableCurrentPizzaList.addAll(currentOrder.getPizzaList());
    orderPriceProperty.set(currentOrder.getValue());
    orderIdProperty.set(currentOrder.getOrderId());

    observableCurrentPizzaList.addListener(
        (ListChangeListener<? super Pizza>) c -> orderPriceProperty.set(service.getOrder(
            (Integer) SingletonAttributeStore.getInstance().getAttribute("orderId")).getValue()));
  }

  public SimpleIntegerProperty getOrderPriceProperty() {
    return orderPriceProperty;
  }

  public SimpleIntegerProperty getOrderIdProperty() {
    return orderIdProperty;
  }

  public ObservableList<PizzaSize> getAvailablePizzaSizeList() {
    return availablePizzaSizeList;
  }

  public ObservableList<Pizza> getObservableCurrentPizzaList() {
    return observableCurrentPizzaList;
  }

  /**
   * Handler method when the button to add a new pizza is pressed. Adds a new pizza to the order of
   * the service and calls updateScreen.
   */
  public void addPizza(PizzaSize size) {
    service.addPizza((Integer) SingletonAttributeStore.getInstance().getAttribute("orderId"),
        size);
    refreshDataFromService();
  }
}
