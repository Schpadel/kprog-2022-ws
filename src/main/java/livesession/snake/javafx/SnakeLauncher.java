package livesession.snake.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import livesession.snake.provider.SimpleSnakeService;

/**
 * Launches the PizzaDeliveryService order application.
 */
public class SnakeLauncher extends Application {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SnakeLauncher.class);

  @Override
  public void start(final Stage stage) throws Exception {
    logger.info("Can't see something ...");

    SimpleSnakeService service = new SimpleSnakeService();
    Pane paneToBeFilled = new Pane();


    stage.setResizable(true);
    stage.setWidth(1250);
    stage.setHeight(950);
    SnakeScreenController snakeScreenController = new SnakeScreenController(paneToBeFilled);
    snakeScreenController.switchTo(null, SnakeMenu.SCREEN_NAME);

    stage.setTitle("KPROG JavaFX wonderful GUIs");
    stage.setScene(new Scene(paneToBeFilled));
    stage.show();
  }
}