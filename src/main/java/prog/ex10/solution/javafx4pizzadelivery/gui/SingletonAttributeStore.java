package prog.ex10.solution.javafx4pizzadelivery.gui;

import java.util.HashMap;
import prog.ex10.exercise.javafx4pizzadelivery.gui.AttributeStore;

/**
 * Simple and straight-forward singleton based implementation of an AttributeStore.
 */
public class SingletonAttributeStore implements AttributeStore {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SingletonAttributeStore.class);

  private static final SingletonAttributeStore self = new SingletonAttributeStore();
  private static HashMap<String, Object> attributes;

  public static SingletonAttributeStore getInstance() {
    return self;
  }

  private SingletonAttributeStore() {
    attributes = new HashMap<>();
  }

  @Override
  public void setAttribute(final String name, final Object object) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name should not be null!");
    }
    if (name.trim().length() == 0) {
      throw new IllegalArgumentException("Name should not be empty!");
    }
    attributes.put(name, object);
  }

  @Override
  public Object getAttribute(final String name) throws IllegalArgumentException {
    if (name == null) {
      throw new IllegalArgumentException("Name should not be null!");
    }
    if (name.trim().length() == 0) {
      throw new IllegalArgumentException("Name should not be empty!");
    }
    return attributes.get(name);
  }

  @Override
  public void removeAttribute(final String name) {
    attributes.remove(name);
  }
}
