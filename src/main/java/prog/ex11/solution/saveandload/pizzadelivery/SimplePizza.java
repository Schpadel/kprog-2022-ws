package prog.ex11.solution.saveandload.pizzadelivery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import prog.ex11.exercise.saveandload.pizzadelivery.Pizza;
import prog.ex11.exercise.saveandload.pizzadelivery.PizzaDeliveryService;
import prog.ex11.exercise.saveandload.pizzadelivery.PizzaSize;
import prog.ex11.exercise.saveandload.pizzadelivery.TooManyToppingsException;
import prog.ex11.exercise.saveandload.pizzadelivery.Topping;


/**
 * Simple and straight-forward implementation of the Pizza interface.
 */
public class SimplePizza implements Pizza {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimplePizza.class);
  private static int idCounter = 0;
  private List<Topping> toppings;
  private PizzaSize size;
  private int price;
  private int id; // must be unique

  /**
   * Initialize a new pizza with an empty toppings list.
   *
   * @param size  of the pizza
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
    return Collections.unmodifiableList(toppings);
  }

  @Override
  public PizzaSize getSize() {
    return this.size;
  }

  @Override
  public int getPrice() {
    return this.price;
  }


  /**
   * add a new topping to the pizza.
   *
   * @param topping to be added.
   * @param price   price of the topping.
   * @throws TooManyToppingsException too many toppings have been added to the pizza.
   */
  public void addTopping(Topping topping, int price) throws TooManyToppingsException {
    if (getToppings().size() >= PizzaDeliveryService.MAX_TOPPINGS_PER_PIZZA) {
      throw new TooManyToppingsException(
          topping + " Topping could not be added, because the maximum toppings were reached");
    }
    this.toppings.add(topping);
    this.price += price;
  }

  public void removeTopping(Topping topping, int price) {
    this.toppings.remove(topping);
    this.price -= price;
  }
  // added for loading from text file


  public void setPrice(int price) {
    this.price = price;
  }

  public void setId(int id) {
    this.id = id;
  }
}