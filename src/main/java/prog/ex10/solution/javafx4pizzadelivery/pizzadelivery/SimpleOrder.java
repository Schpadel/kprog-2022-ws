package prog.ex10.solution.javafx4pizzadelivery.pizzadelivery;

import java.util.List;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Order;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Pizza;

/**
 * Simple and straight-forward implementation of the Order interface.
 */
public class SimpleOrder implements Order {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(SimpleOrder.class);

  @Override
  public int getOrderId() {
    return 0;
  }

  @Override
  public List<Pizza> getPizzaList() {
    return null;
  }

  @Override
  public int getValue() {
    return 0;
  }
}
