package prog.ex11.solution.saveandload.pizzadelivery;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import prog.ex11.exercise.saveandload.pizzadelivery.Order;
import prog.ex11.exercise.saveandload.pizzadelivery.Pizza;


/**
 * Simple and straight-forward implementation of the Order interface.
 */
public class SimpleOrder implements Order, Serializable {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimpleOrder.class);

  private static int idCounter = 1000;
  private int id; //maybe with atomic Integer class to be thread safe
  private Map<Integer, SimplePizza> pizzaMap;

  /**
   * Construct a new SimpleOrder.
   */
  public SimpleOrder() {
    this.id = idCounter;
    idCounter++;
    pizzaMap = new HashMap<>();
  }

  @Override
  public int getOrderId() {
    return id;
  }

  @Override
  public List<Pizza> getPizzaList() {
    return new ArrayList<>(pizzaMap.values());
  }

  public Map<Integer, SimplePizza> getPizzaMap() {
    return this.pizzaMap;
  }

  @Override
  public int getValue() {
    int value = 0;
    for (Pizza pizza : pizzaMap.values()) {
      value += pizza.getPrice();
    }
    return value;
  }

  public void addPizza(SimplePizza pizza) {
    pizzaMap.put(pizza.getPizzaId(), pizza);
  }

  /**
   * Remove a pizza from the order according to the provided id.
   *
   * @param pizzaId to be removed from the order
   * @throws IllegalArgumentException thrown if provided id is not valid
   */
  public void removePizzaWithId(int pizzaId) throws IllegalArgumentException {
    if (!pizzaMap.containsKey(pizzaId)) {
      throw new IllegalArgumentException(
          "Pizza ID " + pizzaId + "is unknown for order " + this.getOrderId());
    }
    pizzaMap.remove(pizzaId);
  }

  // added for loading from file


  public void setId(int id) {
    this.id = id;
  }

  @Override
  public String toString() {
    String pizzaContent = "";


    for(Entry<Integer, SimplePizza> current : pizzaMap.entrySet()) {
      pizzaContent += ", " + current.getKey() + ":" + current.getValue().toString();
    }

    return "SimpleOrder{" +
        "id=" + id +
        ", pizzaContent=" + pizzaContent +
        '}';
  }
}