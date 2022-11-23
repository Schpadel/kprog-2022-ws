package prog.ex10.solution.javafx4pizzadelivery.pizzadelivery;

import java.util.Map;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Order;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaDeliveryService;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaSize;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.TooManyToppingsException;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Topping;

/**
 * Simple and straight-forward implementation of the PizzaDeliveryService interface.
 */
public class SimplePizzaDeliveryService implements PizzaDeliveryService {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(SimplePizzaDeliveryService.class);

  public SimplePizzaDeliveryService() {
  }

  @Override
  public int createOrder() {
    return 0;
  }

  @Override
  public int addPizza(final int orderId, final PizzaSize size) throws IllegalArgumentException {
    return 0;
  }

  @Override
  public void removePizza(final int orderId, final int pizzaId) throws IllegalArgumentException {

  }

  @Override
  public void addTopping(final int pizzaId, final Topping topping)
          throws IllegalArgumentException, TooManyToppingsException {

  }

  @Override
  public void removeTopping(final int pizzaId, final Topping topping)
          throws IllegalArgumentException {

  }

  @Override
  public Order getOrder(final int orderId) {
    return null;
  }

  @Override
  public Map<PizzaSize, Integer> getPizzaSizePriceList() {
    return null;
  }

  @Override
  public Map<Topping, Integer> getToppingsPriceList() {
    return null;
  }
}
