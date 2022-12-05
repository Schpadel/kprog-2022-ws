package livesession.snake.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;


/**
 * Class to show and manage the snake game menu.
 */
public class SnakeMenu extends VBox implements Initializable, SnakeScreen {


  private SnakeScreenController controller;

  public static final String SCREEN_NAME = "SnakeMenu";

  @FXML
  private Button playButton;
  @FXML
  private Button configureButton;
  @FXML
  private Button quitButton;

  /**
   * Construct a new instance of SnakeMenu.
   *
   * @param controller to be used for this menu
   */
  public SnakeMenu(SnakeScreenController controller) {

    this.controller = controller;
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SnakeMenu.fxml"));
    fxmlLoader.setController(this);
    try {
      this.getChildren().add(fxmlLoader.load());
    } catch (IOException e) {
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
    playButton.setOnAction(event -> playGame());
    quitButton.setOnAction(event -> Platform.exit());
    configureButton.setOnAction(event -> configureGame());
  }

  /**
   * Button handler method, starts the game by switching to SnakeDisplay.
   */
  public void playGame() {
    try {
      controller.switchTo(SCREEN_NAME, SnakeDisplay.SCREEN_NAME);
    } catch (UnknownTransitionException e) {
      e.printStackTrace();
    }
  }

  /**
   * Button handler method, allows to configure the game by switching to SnakeConfigureMenu.
   */
  public void configureGame() {
    try {
      controller.switchTo(SCREEN_NAME, SnakeConfigureMenu.SCREEN_NAME);
    } catch (UnknownTransitionException e) {
      e.printStackTrace();
    }
  }


}
