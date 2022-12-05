package livesession.snake.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * SnakeConfigurationMenu Class, shows and manges the configuration menu for the game.
 */
public class SnakeConfigureMenu extends VBox implements Initializable, SnakeScreen {
  public static final String SCREEN_NAME = "ConfigureMenu";

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

  /**
   * Constructs a new instance of SnakeConfigurationMenu.
   *
   * @param controller to be used for this config menu screen
   * @param snakeServiceViewModel to be used for this config menu screen
   */
  public SnakeConfigureMenu(SnakeScreenController controller,
      SnakeServiceViewModel snakeServiceViewModel) {
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

  /**
   * Event handler for the configure action, called when the done button is pressed. Calls configure
   * Game in the assigned viewModel.
   */
  public void configure() {
    try {
      snakeServiceViewModel.configureGame(
          Integer.parseInt(sizeInput.textProperty().get()),
          Integer.parseInt(speedInput.textProperty().get()),
          Integer.parseInt(numberOfFoodInput.textProperty().get()));
      controller.switchTo(SCREEN_NAME, SnakeMenu.SCREEN_NAME);
    } catch (Exception e) {
      //TODO: Implement pop-up with wrong exception warning
      Alert alert = new Alert(AlertType.ERROR);
      alert.setHeaderText("Configuration exception occurred! Please enter only numbers, ty!");
      alert.setContentText(e.getMessage());
      alert.showAndWait();
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
    doneButton.setOnAction(event -> configure());
  }
}
