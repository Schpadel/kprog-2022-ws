package livesession.snake.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import livesession.snake.GameConfiguration;
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

    // Start service early to test
    // service.start();
    service.configure(new GameConfiguration(20, 100, 10));
    SnakeServiceViewModel viewModel = new SnakeServiceViewModel(service);
    paneToBeFilled.getChildren().add(new SnakeDisplay(viewModel));

    stage.setResizable(true);
    stage.setWidth(900);
    stage.setHeight(900);

    stage.setTitle("KPROG JavaFX wonderful GUIs");
    stage.setScene(new Scene(paneToBeFilled));
    stage.show();
  }
}