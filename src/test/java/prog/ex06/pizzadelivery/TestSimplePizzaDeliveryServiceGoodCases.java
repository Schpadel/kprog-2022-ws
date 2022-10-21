package prog.ex06.pizzadelivery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import prog.ex06.exercise.pizzadelivery.PizzaSize;
import prog.ex06.exercise.pizzadelivery.TooManyToppingsException;
import prog.ex06.exercise.pizzadelivery.Topping;
import prog.ex06.solution.pizzadelivery.SimpleOrder;
import prog.ex06.solution.pizzadelivery.SimplePizza;
import prog.ex06.solution.pizzadelivery.SimplePizzaDeliveryService;

public class TestSimplePizzaDeliveryServiceGoodCases {

  private SimplePizzaDeliveryService testService;
  private SimplePizza testPizza;
  private int testOrderId;


  @Before
  public void setUp() throws Exception {
    testService = new SimplePizzaDeliveryService();
    testOrderId = testService.createOrder();


  }

  @Test
  public void getValueOfOrder() {
    int orderID = testService.createOrder();

    int mediumPizza = testService.addPizza(orderID, PizzaSize.MEDIUM); // 700
    try {
      testService.addTopping(mediumPizza, Topping.TOMATO); // 30
      testService.addTopping(mediumPizza, Topping.VEGETABLES); // 20
    } catch (TooManyToppingsException e) {
      throw new RuntimeException(e);
    }

    assertEquals(750, testService.getOrder(orderID).getValue());
  }

  @Test
  public void getValueOfBigOrder() {
    int orderID = testService.createOrder();

    // add all sizes
    int smallPizza = testService.addPizza(orderID, PizzaSize.SMALL); //       price: 500
    int mediumPizza = testService.addPizza(orderID, PizzaSize.MEDIUM); //     price: 700
    int largePizza = testService.addPizza(orderID, PizzaSize.LARGE); //       price: 900
    int xlargePizza = testService.addPizza(orderID,PizzaSize.EXTRA_LARGE); // price: 1100

    // add various toppings

    try {
      testService.addTopping(smallPizza, Topping.TOMATO); //    price: 30
      testService.addTopping(smallPizza, Topping.PINEAPPLE); // price: 90
      testService.addTopping(smallPizza, Topping.PINEAPPLE); // price: 90
      testService.addTopping(smallPizza, Topping.PINEAPPLE); // price: 90
      testService.addTopping(mediumPizza, Topping.HAM); //      price: 70
      testService.addTopping(mediumPizza, Topping.HAM); //      price: 70
      testService.addTopping(mediumPizza, Topping.HAM); //      price: 70
      testService.addTopping(xlargePizza, Topping.CHEESE); //   price: 60
      testService.addTopping(xlargePizza, Topping.SEAFOOD); //  price: 150
      testService.addTopping(xlargePizza, Topping.SALAMI); //   price: 50
    } catch (TooManyToppingsException e) {
      System.err.println("Too many toppings added in test!");
    }
    assertEquals(3970, testService.getOrder(orderID).getValue());
  }

  @Test
  public void removePizza() {
  }

  @Test
  public void addTopping() {
    int testPizza = testService.addPizza(testOrderId, PizzaSize.MEDIUM);

    try {
      this.testService.addTopping(testPizza, Topping.TOMATO);
    } catch (TooManyToppingsException e) {
      System.err.println(e.getMessage());
    }

    assertTrue(testService.getOrder(testOrderId).getPizzaList().get(0).getToppings().contains(Topping.TOMATO));
    try {
      testService.addTopping(testPizza, Topping.CHEESE);
      testService.addTopping(testPizza, Topping.SALAMI);
      testService.addTopping(testPizza, Topping.HAM);
      testService.addTopping(testPizza, Topping.SEAFOOD);
      testService.addTopping(testPizza, Topping.VEGETABLES);
      testService.addTopping(testPizza, Topping.PINEAPPLE);
      fail("Too many toppings exception should be raised here!");
    } catch (TooManyToppingsException e) {
      assertTrue(true);
    }
  }

  @Test
  public void removeTopping() throws TooManyToppingsException {
    int testPizza = testService.addPizza(testOrderId, PizzaSize.MEDIUM);

    testService.addTopping(testPizza, Topping.TOMATO);
    testService.addTopping(testPizza, Topping.CHEESE);
    testService.addTopping(testPizza, Topping.SALAMI);

    testService.removeTopping(testPizza, Topping.CHEESE);

    assertFalse(testService.getOrder(testOrderId).getPizzaList().get(0).getToppings().contains(Topping.CHEESE));
    assertEquals(2, testService.getOrder(testOrderId).getPizzaList().get(0).getToppings().size());
  }

  @Test
  public void getOrder() {
  }

  @Test
  public void getPizzaSizePriceList() {
    Map<PizzaSize, Integer> priceList = this.testService.getPizzaSizePriceList();
    assertEquals(Integer.valueOf(500), priceList.get(PizzaSize.SMALL));
    assertEquals(Integer.valueOf(700), priceList.get(PizzaSize.MEDIUM));
    assertEquals(Integer.valueOf(900), priceList.get(PizzaSize.LARGE));
    assertEquals(Integer.valueOf(1100), priceList.get(PizzaSize.EXTRA_LARGE));
  }

  @Test
  public void getToppingsPriceList() {
    Map<Topping, Integer> toppingList = this.testService.getToppingsPriceList();
    assertEquals(Integer.valueOf(30), toppingList.get(Topping.TOMATO));
    assertEquals(Integer.valueOf(60), toppingList.get(Topping.CHEESE));
    assertEquals(Integer.valueOf(50), toppingList.get(Topping.SALAMI));
    assertEquals(Integer.valueOf(70), toppingList.get(Topping.HAM));
    assertEquals(Integer.valueOf(90), toppingList.get(Topping.PINEAPPLE));
    assertEquals(Integer.valueOf(20), toppingList.get(Topping.VEGETABLES));
    assertEquals(Integer.valueOf(150), toppingList.get(Topping.SEAFOOD));
  }
}