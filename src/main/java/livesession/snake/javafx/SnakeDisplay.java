package livesession.snake.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class SnakeDisplay extends GridPane implements Initializable {

  private SnakeServiceViewModel viewModel;

  private SnakeBoard snakeBoard;

  @FXML
  private Label scoreLabel;

  @FXML
  private Label gameStateLabel;
  @FXML
  private GridPane gameField;
  @FXML
  private Button startButton;
  @FXML
  private Button pauseButton;
  @FXML
  private Button resumeButton;
  @FXML
  private Button abortButton;

  public SnakeDisplay(SnakeServiceViewModel viewModel) {

    this.viewModel = viewModel;

    snakeBoard = new SnakeBoard(viewModel);

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SnakeDisplay.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);


    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    this.addRow(this.getRowCount() + 1, snakeBoard);

    Platform.runLater(() -> snakeBoard.requestFocus());
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
    scoreLabel.textProperty().bind(viewModel.scoreProperty().asString());
    gameStateLabel.textProperty().bind(viewModel.currentGameStateProperty().asString());


    resumeButton.setOnAction(event -> handleAction( method -> viewModel.continueGame()));
    abortButton.setOnAction(event -> handleAction(method -> viewModel.abortGame()));
    startButton.setOnAction(event -> handleAction(method -> viewModel.startGame()));
    pauseButton.setOnAction(event -> handleAction(method -> viewModel.pauseGame()));
  }

  public void handleAction(Consumer<SnakeServiceViewModel> viewModelConsumer) {
    this.snakeBoard.requestFocus();
    viewModelConsumer.accept(viewModel);
  }
}
