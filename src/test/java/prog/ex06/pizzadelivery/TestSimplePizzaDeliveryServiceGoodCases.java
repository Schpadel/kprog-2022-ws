package prog.ex06.pizzadelivery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;
import org.junit.Before;
import org.junit.Test;


import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaDeliveryService;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaSize;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.TooManyToppingsException;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.Topping;
import prog.ex10.solution.javafx4pizzadelivery.pizzadelivery.SimplePizza;
import prog.ex10.solution.javafx4pizzadelivery.pizzadelivery.SimplePizzaDeliveryService;

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
  public void getValueOfSmallOrder() {
    int mediumPizza = testService.addPizza(testOrderId, PizzaSize.MEDIUM); // 700
    try {
      testService.addTopping(mediumPizza, Topping.TOMATO); // 30
      testService.addTopping(mediumPizza, Topping.VEGETABLES); // 20
    } catch (TooManyToppingsException e) {
      throw new RuntimeException(e);
    }

    assertEquals(750, testService.getOrder(testOrderId).getValue());
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
    int pizzaIDontWant = testService.addPizza(testOrderId, PizzaSize.SMALL);
    testService.removePizza(testOrderId, pizzaIDontWant);
    assertEquals(0, testService.getOrder(testOrderId).getPizzaList().size());
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
  }

  @Test
  public void addExactAmountOfToppings() {
    int overloadPizza = testService.addPizza(testOrderId, PizzaSize.LARGE);

    try {
      testService.addTopping(overloadPizza, Topping.CHEESE);
      testService.addTopping(overloadPizza, Topping.SALAMI);
      testService.addTopping(overloadPizza, Topping.HAM);
      testService.addTopping(overloadPizza, Topping.SEAFOOD);
      testService.addTopping(overloadPizza, Topping.VEGETABLES);
      testService.addTopping(overloadPizza, Topping.PINEAPPLE);

    } catch (TooManyToppingsException e) {
      fail("Exception should only be thrown if there are more toppings than defined in MAX_TOPPINGS_PER_PIZZA!");
    }
    assertEquals(
        PizzaDeliveryService.MAX_TOPPINGS_PER_PIZZA, testService.getOrder(testOrderId).getPizzaList().get(0).getToppings().size());
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