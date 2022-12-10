package prog.ex11.solution.saveandload.factory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.StringTokenizer;
import prog.ex11.exercise.saveandload.factory.PersistenceFactory;
import prog.ex11.exercise.saveandload.factory.WrongOrderFormatException;
import prog.ex11.exercise.saveandload.pizzadelivery.Order;
import prog.ex11.exercise.saveandload.pizzadelivery.Pizza;
import prog.ex11.exercise.saveandload.pizzadelivery.PizzaSize;
import prog.ex11.exercise.saveandload.pizzadelivery.TooManyToppingsException;
import prog.ex11.exercise.saveandload.pizzadelivery.Topping;
import prog.ex11.solution.saveandload.pizzadelivery.SimpleOrder;
import prog.ex11.solution.saveandload.pizzadelivery.SimplePizza;
import prog.ex11.solution.saveandload.pizzadelivery.SimplePizzaDeliveryService;

/**
 * Simple and straight-forward implementation of the PersistenceFactory interface.
 * This implementation uses text in a csv format.
 */
public class PlainTextPersistenceFactory implements PersistenceFactory {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(PlainTextPersistenceFactory.class);

  @Override
  public void save(final File file, final Order order) throws IOException {
    try (BufferedWriter buff = new BufferedWriter(new FileWriter(file))) {
      buff.write(String.format("%d;%d;%d%n", order.getOrderId(), order.getValue(), order.getPizzaList().size()));
      for(Pizza currentPizza : order.getPizzaList()) {
        buff.write(String.format("%s;%s;%s", currentPizza.getPizzaId(), currentPizza.getPrice(), currentPizza.getSize()));
        for (Topping currentTopping : currentPizza.getToppings()) {
          buff.write(String.format(";%s", currentTopping));
        }
        buff.write(String.format("%n"));
      }
    }



  }

  @Override
  public Order load(final File file) throws IOException, WrongOrderFormatException {
    SimplePizzaDeliveryService service = new SimplePizzaDeliveryService();
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String input;

    String orderInfo = reader.readLine();

    StringTokenizer tokenizer = new StringTokenizer(orderInfo, ";");

    SimpleOrder loadedOrder = new SimpleOrder();

    if(tokenizer.countTokens() != 3) {
      throw new WrongOrderFormatException("Order header information too short!");
    }
    try {
      loadedOrder.setId(Integer.parseInt(tokenizer.nextToken())); // orderId
    } catch (Exception e) {
      throw new WrongOrderFormatException("Order ID need to be of type int!");
    }
    tokenizer.nextToken(); //price of order
    tokenizer.nextToken(); // number of pizzas


    while ((input = reader.readLine()) != null) {
      StringTokenizer pizzaToken = new StringTokenizer(input, ";");

      // setup order
      int pizzaId;
      int pizzaValue;
      try {
        pizzaId = Integer.parseInt(pizzaToken.nextToken());
        pizzaValue = Integer.parseInt(pizzaToken.nextToken());
      } catch (Exception e) {
        throw new WrongOrderFormatException("Order ID need to be of type int!");
      }

      SimplePizza loadedPizza;
      String loadedSize = pizzaToken.nextToken();
      if(Arrays.stream(PizzaSize.values()).anyMatch((t) -> t.name().equals(loadedSize))) {
         loadedPizza = new SimplePizza(PizzaSize.valueOf(loadedSize), 0);
      }else{
        throw new WrongOrderFormatException("Pizza Size: " +  loadedSize + " not available at this serivce!");
      }


      // add Toppings
      String currentTopping;
      while(pizzaToken.hasMoreTokens()) {
        currentTopping = pizzaToken.nextToken();
        if (!toppingExists(currentTopping)) {
          throw new WrongOrderFormatException("Topping: " + currentTopping + " not available at this service!");
        }
        try {
          loadedPizza.addTopping(Topping.valueOf(currentTopping), 0);
        } catch (TooManyToppingsException e) {
          e.printStackTrace();
        }

      }
      loadedPizza.setId(pizzaId);
      loadedPizza.setPrice(pizzaValue);
      loadedOrder.addPizza(loadedPizza);
    }

    return loadedOrder;

  }

  private boolean toppingExists(String currentTopping) {
    return Arrays.stream(Topping.values()).anyMatch((t) -> t.name().equals(currentTopping));
  }
}
