package prog.ex06.solution.pizzadelivery;

import java.util.List;
import prog.ex06.exercise.pizzadelivery.Pizza;
import prog.ex06.exercise.pizzadelivery.PizzaSize;
import prog.ex06.exercise.pizzadelivery.Topping;

/**
 * Simple and straight-forward implementation of the Pizza interface.
 */
public class SimplePizza implements Pizza {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(SimplePizza.class);

  @Override
  public int getPizzaId() {
    return 0;
  }

  @Override
  public List<Topping> getToppings() {
    return null;
  }

  @Override
  public PizzaSize getSize() {
    return null;
  }

  @Override
  public int getPrice() {
    return 0;
  }
}
