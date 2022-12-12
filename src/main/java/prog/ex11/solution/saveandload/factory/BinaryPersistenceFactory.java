package prog.ex11.solution.saveandload.factory;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import prog.ex11.exercise.saveandload.factory.PersistenceFactory;
import prog.ex11.exercise.saveandload.factory.WrongOrderFormatException;
import prog.ex11.exercise.saveandload.pizzadelivery.Order;
import prog.ex11.exercise.saveandload.pizzadelivery.Pizza;
import prog.ex11.exercise.saveandload.pizzadelivery.PizzaSize;
import prog.ex11.exercise.saveandload.pizzadelivery.TooManyToppingsException;
import prog.ex11.exercise.saveandload.pizzadelivery.Topping;
import prog.ex11.solution.saveandload.pizzadelivery.SimpleOrder;
import prog.ex11.solution.saveandload.pizzadelivery.SimplePizza;

/**
 * Simple and straight-forward implementation of the PersistenceFactory interface. This
 * implementation uses Data Stream to write and read primitive types in binary.
 */
public class BinaryPersistenceFactory implements PersistenceFactory {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(BinaryPersistenceFactory.class);

  @Override
  public void save(final File file, final Order order) throws IOException {
    try (DataOutputStream out = new DataOutputStream(
        new BufferedOutputStream(new FileOutputStream(file)))) {

      out.writeInt(order.getOrderId());
      out.writeInt(order.getValue());
      out.writeInt(order.getPizzaList().size());

      for (Pizza currentPizza : order.getPizzaList()) {
        out.writeInt(currentPizza.getPizzaId());
        out.writeInt(currentPizza.getPrice());
        out.writeInt(currentPizza.getSize().ordinal());
        out.writeInt(currentPizza.getToppings().size()); // Save amount of toppings for loading

        for (Topping currentTopping : currentPizza.getToppings()) {
          out.writeInt(currentTopping.ordinal());
        }
      }
    }


  }

  @Override
  public Order load(final File file) throws IOException, WrongOrderFormatException {
    SimpleOrder loadedOrder = new SimpleOrder();

    try (DataInputStream in = new DataInputStream(
        new BufferedInputStream(new FileInputStream(file)))) {
      int orderId = in.readInt();
      loadedOrder.setId(orderId);
      int orderValue = in.readInt();
      int loadedNumberOfPizzas = in.readInt();

      for (int i = 0; i < loadedNumberOfPizzas; i++) {
        logger.info("Loop run: " + i);
        int loadedPizzaId = in.readInt();
        int price = in.readInt();
        PizzaSize loadedPizzaSize;
        try {
          loadedPizzaSize = PizzaSize.values()[in.readInt()];
        } catch (ArrayIndexOutOfBoundsException e) {
          throw new WrongOrderFormatException("Pizza Size in File does not exists in this service!",
              e);
        }
        SimplePizza loadedPizza = new SimplePizza(loadedPizzaSize, price);
        loadedPizza.setId(loadedPizzaId);
        int numberOfToppings = in.readInt();
        for (int j = 0; j < numberOfToppings; j++) {
          try {
            loadedPizza.addTopping(Topping.values()[in.readInt()], 0);
          } catch (TooManyToppingsException e) {
            throw new WrongOrderFormatException(
                "Too many Toppings loaded from file for this service!", e);
          }
        }

        loadedOrder.addPizza(loadedPizza);
      }

    }

    return loadedOrder;
  }
}
