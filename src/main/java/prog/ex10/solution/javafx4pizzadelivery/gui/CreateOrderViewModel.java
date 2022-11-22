package prog.ex10.solution.javafx4pizzadelivery.gui;

import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaDeliveryService;

/**
 * View model for CreateOrderScreen. Manages all observable data structures + access.
 */
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
