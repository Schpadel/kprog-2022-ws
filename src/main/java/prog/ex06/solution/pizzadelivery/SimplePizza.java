package prog.ex06.solution.pizzadelivery;

import java.util.ArrayList;
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

  private List<Topping> toppings;
  private PizzaSize size;
  private int price;

  private static int idCounter = 0;
  private int id; // must be unique

  /**
   * Initialize a new pizza with an empty toppings list.
   *
   * @param size of the pizza
   * @param price of the pizza
   */

  public SimplePizza(PizzaSize size, int price) {
    this.id = idCounter;
    idCounter++;
    this.toppings = new ArrayList<>();
    this.size = size;
    this.price = price;

  }

  @Override
  public int getPizzaId() {
    return this.id;
  }

  @Override
  public List<Topping> getToppings() {
    return this.toppings;
  }

  @Override
  public PizzaSize getSize() {
    return this.size;
  }

  @Override
  public int getPrice() {
    return this.price;
  }
}
