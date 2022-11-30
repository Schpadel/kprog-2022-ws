package livesession.snake.javafx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class SnakeDisplay extends StackPane implements Initializable {

  private SnakeServiceViewModel viewModel;

  private SnakeBoard snakeBoard;

  @FXML
  private VBox UIContainer;
  @FXML
  private HBox pauseMenu;
  @FXML
  private HBox gameOverMenu;
  @FXML
  private Button gameOverTryAgainButton;
  @FXML Button gameOverBackToMenu;
  @FXML
  private Button pauseMenuContinueButton;
  @FXML
  private Button pauseMenuAbortButton;

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

    snakeBoard = new SnakeBoard(viewModel, this);

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SnakeDisplay.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);


    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    // GridPane only: this.addRow(this.getRowCount() + 1, snakeBoard);
    snakeBoard.setAlignment(Pos.CENTER);
    pauseMenu.setVisible(false);
    gameOverMenu.setVisible(false);
    UIContainer.getChildren().add(snakeBoard);

    viewModel.gameEndedPropertyProperty().addListener(observable -> showGameOverMenu());

    Platform.runLater(() -> snakeBoard.requestFocus());
  }

  private void showGameOverMenu() {
    UIContainer.setEffect(new BoxBlur());
    gameOverMenu.setVisible(true);
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
    gameStateLabel.textProperty().bind(viewModel.currentGameStatePropertyProperty().asString());


    resumeButton.setOnAction(event -> handleAction( method -> viewModel.continueGame()));
    abortButton.setOnAction(event -> handleAction(method -> viewModel.abortGame()));
    startButton.setOnAction(event -> handleAction(method -> viewModel.startGame()));
    pauseButton.setOnAction(event -> handleAction(method -> viewModel.pauseGame()));

    // Pause Menu Buttons
    pauseMenuContinueButton.setOnAction(event -> handleAction(method -> viewModel.continueGame()));
    pauseMenuAbortButton.setOnAction(event -> handleAction(method -> viewModel.abortGame()));

    //Game Over Menu Buttons
    gameOverTryAgainButton.setOnAction(event -> tryAgain());
  }

  private void tryAgain() {
    viewModel.resetGame();
    snakeBoard.requestFocus();
    gameOverMenu.setVisible(false);
    UIContainer.setEffect(null);
  }

  public void handleAction(Consumer<SnakeServiceViewModel> viewModelConsumer) {
    this.snakeBoard.requestFocus();
    viewModelConsumer.accept(viewModel);

    switch(viewModel.getCurrentGameState()) {
      case PAUSED:
        pauseButton.setDisable(true);
        pauseButton.setVisible(false);
        pauseMenu.setVisible(true);
        UIContainer.setEffect(new BoxBlur());
        break;
      case RUNNING:
        pauseButton.setDisable(false);
        pauseButton.setVisible(true);
        startButton.setDisable(true);
        startButton.setVisible(false);
        pauseMenu.setVisible(false);
        UIContainer.setEffect(null);
        break;
      case PREPARED:
        pauseButton.setDisable(true);
        pauseButton.setVisible(false);
        resumeButton.setDisable(true);
        resumeButton.setVisible(false);
        pauseMenu.setVisible(false);
        break;
      case ABORTED:
        pauseButton.setDisable(true);
        pauseButton.setVisible(false);
        resumeButton.setDisable(true);
        resumeButton.setVisible(false);
        startButton.setDisable(true);
        startButton.setVisible(false);
        pauseMenu.setVisible(false);
        break;
    }

  }
}