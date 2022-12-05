package livesession.snake.javafx;

import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import livesession.snake.provider.SimpleSnakeService;

/**
 * Simple and straight-forward implementation of a ScreenController for the Snake game.
 */
public class SnakeScreenController {

  HashMap<String, SnakeScreen> allScreens;

  private Pane pane;

  /**
   * Constructs a new instance of the SnakeScreenController, which manages all Screens of
   * the Snake Game GUI.
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

  /**
   * switches to the requested screen from the provided screen.
   *
   * @param fromScreen to switch away from
   * @param toScreen to switch to
   * @throws UnknownTransitionException When either screen is not known to the controller.
   */
  public void switchTo(final String fromScreen, final String toScreen)
      throws UnknownTransitionException {
    if (!allScreens.containsKey(fromScreen)) {
      throw new UnknownTransitionException("From Screen is unknown", fromScreen, toScreen);
    }

    if (allScreens.containsKey(toScreen)) {
      pane.getChildren().clear();
      pane.getChildren().add((Node) allScreens.get(toScreen));
    } else {
      throw new UnknownTransitionException("To Screen is unknown", fromScreen, toScreen);
    }
  }
}

