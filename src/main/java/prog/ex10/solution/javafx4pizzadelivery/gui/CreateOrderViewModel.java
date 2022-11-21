package prog.ex10.solution.javafx4pizzadelivery.gui;

import javafx.scene.control.Button;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaDeliveryService;

public class CreateOrderViewModel {
  private SingletonAttributeStore singletonAttributeStore;

  public CreateOrderViewModel() {
    this.singletonAttributeStore = SingletonAttributeStore.getInstance();
  }

  public PizzaDeliveryService getService() {
    return (PizzaDeliveryService) singletonAttributeStore.getAttribute("PizzaDeliveryService");
  }

  public void setOrderId(int orderId) {
    singletonAttributeStore.setAttribute("orderId", orderId);
  }
}
