package prog.ex06.solution.pizzadelivery;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import prog.ex06.exercise.pizzadelivery.Order;
import prog.ex06.exercise.pizzadelivery.Pizza;
import prog.ex06.exercise.pizzadelivery.PizzaDeliveryService;
import prog.ex06.exercise.pizzadelivery.PizzaSize;
import prog.ex06.exercise.pizzadelivery.TooManyToppingsException;
import prog.ex06.exercise.pizzadelivery.Topping;

/**
 * Simple and straight-forward implementation of the PizzaDeliveryService interface.
 */
public class SimplePizzaDeliveryService implements PizzaDeliveryService {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimplePizzaDeliveryService.class);

  private Map<Integer, SimpleOrder> orderMap;
  private Map<PizzaSize, Integer> pizzaSizePriceMap;
  private Map<Topping, Integer> pizzaToppingPriceMap;

  /**
   * Construct a new SimplePizzaDeliveryService.
   */
  public SimplePizzaDeliveryService() {
    orderMap = new HashMap<>();

    pizzaSizePriceMap = new HashMap<>();
    pizzaSizePriceMap.put(PizzaSize.SMALL, 500);
    pizzaSizePriceMap.put(PizzaSize.MEDIUM, 700);
    pizzaSizePriceMap.put(PizzaSize.LARGE, 900);
    pizzaSizePriceMap.put(PizzaSize.EXTRA_LARGE, 1100);

    pizzaToppingPriceMap = new HashMap<>();
    pizzaToppingPriceMap.put(Topping.TOMATO, 30);
    pizzaToppingPriceMap.put(Topping.CHEESE, 60);
    pizzaToppingPriceMap.put(Topping.SALAMI, 50);
    pizzaToppingPriceMap.put(Topping.HAM, 70);
    pizzaToppingPriceMap.put(Topping.PINEAPPLE, 90);
    pizzaToppingPriceMap.put(Topping.VEGETABLES, 20);
    pizzaToppingPriceMap.put(Topping.SEAFOOD, 150);
  }

  @Override
  public int createOrder() {
    SimpleOrder newOrder = new SimpleOrder(this);
    orderMap.put(newOrder.getOrderId(), newOrder);
    return newOrder.getOrderId();
  }

  @Override
  public int addPizza(final int orderId, final PizzaSize size) throws IllegalArgumentException {
    if (!orderMap.containsKey(orderId)) {
      throw new IllegalArgumentException("Order ID is unknown to the system!");
    }

    SimpleOrder relevantOrder = orderMap.get(orderId);
    Pizza newPizza = new SimplePizza(size, pizzaSizePriceMap.get(size));
    relevantOrder.addPizza(newPizza);
    return newPizza.getPizzaId();

  }

  @Override
  public void removePizza(final int orderId, final int pizzaId) throws IllegalArgumentException {
    if (!orderMap.containsKey(orderId)) {
      throw new IllegalArgumentException("Order ID is unknown to the system!");
    }

    SimpleOrder relevantOrder = orderMap.get(orderId);
    relevantOrder.removePizzaWithId(pizzaId);
  }

  @Override
  public void addTopping(final int pizzaId, final Topping topping)
      throws IllegalArgumentException, TooManyToppingsException {

    SimplePizza pizza = (SimplePizza) findPizzaWithId(pizzaId);
    int priceBeforeNewTopping = pizza.getPrice();
    if (pizza.getToppings().size() >= MAX_TOPPINGS_PER_PIZZA) {
      throw new TooManyToppingsException(
          topping + " Topping could not be added, because the maximum toppings were reached");
    }
    pizza.getToppings().add(topping);
    pizza.setPrice(priceBeforeNewTopping + pizzaToppingPriceMap.get(topping));
  }

  private Pizza findPizzaWithId(int pizzaId) throws IllegalArgumentException {
    for (SimpleOrder current : orderMap.values()) {
      if (current.getPizzaMap().containsKey(pizzaId)) {
        return current.getPizzaMap().get(pizzaId);
      }
    }
    throw new IllegalArgumentException(
        "Pizza with id: " + pizzaId + " was not found in the system.");
  }

  @Override
  public void removeTopping(final int pizzaId, final Topping topping)
      throws IllegalArgumentException {

    SimplePizza pizzaFromId = (SimplePizza) findPizzaWithId(pizzaId);
    int priceWithTopping = pizzaFromId.getPrice();
    Iterator<Topping> it = pizzaFromId.getToppings().iterator();

    while (it.hasNext()) {
      Topping current = it.next();
      if (current == topping) {
        it.remove();
        pizzaFromId.setPrice(priceWithTopping - pizzaToppingPriceMap.get(topping));
        return;
      }
    }

    throw new IllegalArgumentException(
        "Topping: " + topping + " was not found on pizza with id " + pizzaId);
  }

  @Override
  public Order getOrder(final int orderId) {
    if (!this.orderMap.containsKey(orderId)) {
      throw new IllegalArgumentException("Order " + orderId + " is unknown to the service!");
    }
    return this.orderMap.get(orderId);
  }

  @Override
  public Map<PizzaSize, Integer> getPizzaSizePriceList() {
    return pizzaSizePriceMap;
  }

  @Override
  public Map<Topping, Integer> getToppingsPriceList() {
    return pizzaToppingPriceMap;
  }
}
