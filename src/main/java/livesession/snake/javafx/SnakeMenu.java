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
import prog.ex10.exercise.javafx4pizzadelivery.gui.UnknownTransitionException;

public class SnakeMenu extends VBox implements Initializable, SnakeScreen {


  private SnakeScreenController controller;

  public final static String SCREEN_NAME = "SnakeMenu";

  @FXML
  private Button playButton;
  @FXML
  private Button configureButton;
  @FXML
  private Button quitButton;


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

  public void playGame() {
    try {
      controller.switchTo(SCREEN_NAME, SnakeDisplay.SCREEN_NAME);
    } catch (UnknownTransitionException e) {
      e.printStackTrace();
    }
  }

  public void configureGame() {
    try {
      controller.switchTo(SCREEN_NAME, SnakeConfigureMenu.SCREEN_NAME);
    } catch (UnknownTransitionException e) {
      e.printStackTrace();
    }
  }


}
