package prog.ex06.pizzadelivery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import prog.ex06.exercise.pizzadelivery.PizzaSize;
import prog.ex06.exercise.pizzadelivery.Topping;
import prog.ex06.solution.pizzadelivery.SimpleOrder;
import prog.ex06.solution.pizzadelivery.SimplePizza;
import prog.ex06.solution.pizzadelivery.SimplePizzaDeliveryService;

public class TestSimpleOrderGoodCases {

  private SimpleOrder testOrder;
  private SimpleOrder comparisonOrder;

  private SimplePizza testPizza;

  @Before
  public void setUp() throws Exception {
    SimplePizzaDeliveryService testService = new SimplePizzaDeliveryService();
    testOrder = new SimpleOrder();
    comparisonOrder = new SimpleOrder();
    testPizza = new SimplePizza(PizzaSize.EXTRA_LARGE, 1100);
  }

  @Test
  public void emptyOrdersAreDifferent() {
    assertEquals(0, testOrder.getPizzaList().size());
    assertEquals(0, comparisonOrder.getPizzaList().size());
    assertNotEquals(testOrder, comparisonOrder);
    assertNotEquals(testOrder.getOrderId(), comparisonOrder.getOrderId());
  }

  @Test
  public void addPizzaToOrder() {
    testOrder.addPizza(testPizza);
    assertTrue(testOrder.getPizzaList().contains(testPizza));
  }

  @Test
  public void removePizzaFromOrder() {
    testOrder.addPizza(testPizza);
    assertTrue(testOrder.getPizzaList().contains(testPizza));
    testOrder.removePizzaWithId(testPizza.getPizzaId());
    assertTrue(testOrder.getPizzaList().size() == 0);
    assertFalse(testOrder.getPizzaList().contains(testPizza));
  }



  @Test
  public void getPizzaList() {
  }

  @Test
  public void getPizzaMap() {
  }

  @Test
  public void getValue() {
    SimplePizza mediumPizzaWithOneTopping = new SimplePizza(PizzaSize.MEDIUM, 700);
    mediumPizzaWithOneTopping.getToppings().add(Topping.CHEESE); // cheese price: 60

    SimplePizza smallPizzaWithTwoToppings = new SimplePizza(PizzaSize.SMALL, 500);
    smallPizzaWithTwoToppings.getToppings().add(Topping.PINEAPPLE); // pineapple price: 90
    smallPizzaWithTwoToppings.getToppings().add(Topping.VEGETABLES); // vegetables price: 20

    testOrder.addPizza(smallPizzaWithTwoToppings); // price with toppings = 500 + 90 + 20
    testOrder.addPizza(testPizza); // price: 1100
    testOrder.addPizza(mediumPizzaWithOneTopping); // price: 760 with topping

    assertEquals(2470, testOrder.getValue());
  }
}