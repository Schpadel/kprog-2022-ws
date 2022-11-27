package livesession.snake.javafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

    // Start service early to test
    SnakeServiceViewModel viewModel = new SnakeServiceViewModel(service);
    service.start();
    paneToBeFilled.getChildren().add(new SnakeBoard(viewModel));

    stage.setResizable(true);
    stage.setWidth(900);
    stage.setHeight(900);

    stage.setTitle("KPROG JavaFX wonderful GUIs");
    stage.setScene(new Scene(paneToBeFilled));
    stage.show();
  }
}