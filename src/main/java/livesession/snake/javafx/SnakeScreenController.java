package livesession.snake.javafx;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import livesession.snake.GameConfiguration;
import livesession.snake.IllegalConfigurationException;
import livesession.snake.provider.SimpleSnakeService;
import prog.ex10.exercise.javafx4pizzadelivery.gui.UnknownTransitionException;

public class SnakeScreenController {

  HashMap<String, SnakeScreen> allScreens;
  /**
   * Simple and straight-forward implementation of a ScreenController for the PizzaDeliveryService.
   */
  private Pane pane;

  /**
   * Constructs a new instance of the PizzaDeliveryScreenController, which manages all Screens of
   * the PizzaDeliveryService GUI.
   *
   * @param pane to be used to display the individual screens.
   */
  public SnakeScreenController(final Pane pane) {
    allScreens = new HashMap<>();
    this.pane = pane;

    SimpleSnakeService service = new SimpleSnakeService();
    SnakeServiceViewModel snakeServiceViewModel = new SnakeServiceViewModel(service);

    SnakeMenu snakeMenu = new SnakeMenu(this);
    allScreens.put(SnakeMenu.SCREEN_NAME, snakeMenu);

    SnakeConfigureMenu snakeConfigureMenu = new SnakeConfigureMenu(this, snakeServiceViewModel);
    allScreens.put(SnakeConfigureMenu.SCREEN_NAME, snakeConfigureMenu);

    SnakeDisplay snakeDisplay = new SnakeDisplay(snakeServiceViewModel, this);
    allScreens.put(SnakeDisplay.SCREEN_NAME, snakeDisplay);

    // allow transition from null screen
    allScreens.put(null, null);

  }


  public void switchTo(final String fromScreen, final String toScreen)
      throws UnknownTransitionException {
    if (!allScreens.containsKey(fromScreen)) {
      throw new UnknownTransitionException("From Screen is unknown", fromScreen, toScreen);
    }

    if (allScreens.containsKey(toScreen)) {
      //allScreens.get(toScreen).updateScreen();
      pane.getChildren().clear();
      pane.getChildren().add((Node) allScreens.get(toScreen));
    } else {
      throw new UnknownTransitionException("To Screen is unknown", fromScreen, toScreen);
    }
  }
}

