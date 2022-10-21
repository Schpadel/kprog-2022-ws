package prog.ex06.pizzadelivery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import prog.ex06.exercise.pizzadelivery.Order;
import prog.ex06.exercise.pizzadelivery.PizzaSize;
import prog.ex06.exercise.pizzadelivery.Topping;
import prog.ex06.solution.pizzadelivery.SimpleOrder;
import prog.ex06.solution.pizzadelivery.SimplePizza;
import prog.ex06.solution.pizzadelivery.SimplePizzaDeliveryService;

public class TestSimpleOrderGoodCases {

  private SimpleOrder testOrder;
  int testOrderId;
  private SimpleOrder comparisonOrder;
  int comparisonOrderId;

  private SimplePizza testPizza;
  private SimplePizzaDeliveryService testService;

  @Before
  public void setUp() throws Exception {
    testService = new SimplePizzaDeliveryService();
    testOrderId = testService.createOrder();
    comparisonOrderId = testService.createOrder();
    testOrder = (SimpleOrder) testService.getOrder(testOrderId); // cast because the methods of simpleOrder should be tested directly
    comparisonOrder = (SimpleOrder) testService.getOrder(comparisonOrderId); // cast because the methods of simpleOrder should be tested directly
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
    assertEquals(0, testOrder.getPizzaList().size());
    assertFalse(testOrder.getPizzaList().contains(testPizza));
  }
}