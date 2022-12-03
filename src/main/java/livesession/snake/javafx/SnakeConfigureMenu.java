package livesession.snake.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import livesession.snake.IllegalConfigurationException;
import prog.ex10.exercise.javafx4pizzadelivery.gui.UnknownTransitionException;

public class SnakeConfigureMenu extends VBox implements Initializable, SnakeScreen {
  public final static String SCREEN_NAME = "ConfigureMenu";

  private SnakeScreenController controller;
  private SnakeServiceViewModel snakeServiceViewModel;

  @FXML
  private TextField sizeInput;
  @FXML
  private TextField speedInput;
  @FXML
  private TextField numberOfFoodInput;
  @FXML
  private Button doneButton;

  public SnakeConfigureMenu(SnakeScreenController controller, SnakeServiceViewModel snakeServiceViewModel) {
    this.controller = controller;
    this.snakeServiceViewModel = snakeServiceViewModel;

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SnakeConfigureMenu.fxml"));
    fxmlLoader.setController(this);
    try {
      this.getChildren().add(fxmlLoader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  public void configure() {
    try {
      snakeServiceViewModel.configureGame(Integer.parseInt(sizeInput.textProperty().get()), Integer.parseInt(speedInput.textProperty().get()), Integer.parseInt(numberOfFoodInput.textProperty().get()));
    } catch (IllegalConfigurationException e) {
      //TODO: Implement pop-up with wrong exception warning
      e.printStackTrace();
    }
  }

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location  The location used to resolve relative paths for the root object, or
   *                  {@code null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if the root
   *                  object was not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    doneButton.setOnAction(event -> {
      try {
        configure();
        controller.switchTo(SCREEN_NAME, SnakeMenu.SCREEN_NAME);
      } catch (UnknownTransitionException e) {
        throw new RuntimeException(e);
      }
    });

  }
}
