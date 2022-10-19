package prog.ex06.pizzadelivery;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import prog.ex06.exercise.pizzadelivery.Order;
import prog.ex06.solution.pizzadelivery.SimpleOrder;
import prog.ex06.solution.pizzadelivery.SimplePizzaDeliveryService;

public class TestSimplePizzaDeliveryServiceBadCases {

  private SimplePizzaDeliveryService testService;

  @Before
  public void setUp() throws Exception {
     testService = new SimplePizzaDeliveryService();

  }

  @Test
  public void getOrder() {
    try {
      testService.getOrder(69420);
      fail("Order does not exist -> throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void removeInvalidPizzaIdFromInvalidOrderId() {
    try {
      testService.removePizza(69420, 7777);
      fail("Order does not exist -> throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void removeInvalidPizzaIdFromValidOrderId() {
    int validOrderId = testService.createOrder();

    try {
      testService.removePizza(validOrderId, 7777);
      fail("Pizza ID does not exist -> throw IllegalArgumentException");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void testDuplicateService() {
    // TODO: more tests

    SimplePizzaDeliveryService secondTestService = new SimplePizzaDeliveryService();

    int firstServiceOrderId = testService.createOrder();
    try {
      secondTestService.getOrder(firstServiceOrderId);
      fail("There should be no order in the second service, because it was created inside the first service!");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

  }
}