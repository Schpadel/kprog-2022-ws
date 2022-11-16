package prog.ex06.pizzadelivery;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaSize;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.TooManyToppingsException;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Topping;
import prog.ex10.solution.javafx4pizzadelivery.pizzadelivery.SimplePizzaDeliveryService;

public class TestSimplePizzaDeliveryServiceBadCases {

  private SimplePizzaDeliveryService testService;

  @Before
  public void setUp() throws Exception {
     testService = new SimplePizzaDeliveryService();

  }

  @Test
  public void getInvalidOrder() {
    try {
      testService.getOrder(69420);
      fail("Order does not exist, IllegalArgumentException should be thrown");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }
  }

  @Test
  public void removeInvalidPizzaIdFromInvalidOrderId() {
    try {
      testService.removePizza(69420, 7777);
      fail("Order does not exist, throw IllegalArgumentException");
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

    SimplePizzaDeliveryService secondTestService = new SimplePizzaDeliveryService();

    int firstServiceOrderId = testService.createOrder();
    int secondServiceOrderId = secondTestService.createOrder();
    try {
      secondTestService.getOrder(firstServiceOrderId);
      fail("There should be no order in the second service, because it was created inside the first service!");
    } catch (IllegalArgumentException e) {
      assertTrue(true);
    }

    assertEquals(testService.getOrder(firstServiceOrderId).getOrderId(), firstServiceOrderId);

    // add pizza to order in first service and second service check if they influence each other

    int firstServicePizzaID = testService.addPizza(firstServiceOrderId, PizzaSize.EXTRA_LARGE);
    int secondServicePizzaID = secondTestService.addPizza(secondServiceOrderId, PizzaSize.SMALL);
    int secondServicePizzaID2 = secondTestService.addPizza(secondServiceOrderId, PizzaSize.MEDIUM);

    try {
      testService.addTopping(firstServicePizzaID, Topping.PINEAPPLE);
      secondTestService.addTopping(secondServicePizzaID, Topping.HAM);

      // remove topping then add it again in second service to check if something breaks
      secondTestService.removeTopping(secondServicePizzaID, Topping.HAM);
      secondTestService.addTopping(secondServicePizzaID, Topping.HAM);
    } catch (TooManyToppingsException e) {
      System.out.println(e.getMessage());
    }

    assertEquals(1, testService.getOrder(firstServiceOrderId).getPizzaList().size());
    assertEquals(2, secondTestService.getOrder(secondServiceOrderId).getPizzaList().size());
    assertEquals(Topping.PINEAPPLE, testService.getOrder(firstServiceOrderId).getPizzaList().get(0).getToppings().get(0));
    assertEquals(Topping.HAM, secondTestService.getOrder(secondServiceOrderId).getPizzaList().get(0).getToppings().get(0));

    // check if price calculation is influenced by multiple instances of the service
    assertEquals(1190, testService.getOrder(firstServiceOrderId).getValue());
    assertEquals(1270, secondTestService.getOrder(secondServiceOrderId).getValue());

  }

  @Test
  public void addTooManyToppings() {
    int orderId = testService.createOrder();
    int pizzaId = testService.addPizza(orderId, PizzaSize.LARGE);

    try {
      testService.addTopping(pizzaId, Topping.HAM);
      testService.addTopping(pizzaId, Topping.HAM);
      testService.addTopping(pizzaId, Topping.HAM);
      testService.addTopping(pizzaId, Topping.HAM);
      testService.addTopping(pizzaId, Topping.HAM);
      testService.addTopping(pizzaId, Topping.HAM);
      testService.addTopping(pizzaId, Topping.HAM);
      fail("Too many toppings were added, TooManyToppingsException should be thrown!");
    } catch (TooManyToppingsException e) {
      assertTrue(true);
    }
  }
}