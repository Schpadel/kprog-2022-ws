package livesession.snake.javafx;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import livesession.snake.Board;
import livesession.snake.BoardState;

public class SnakeBoard extends GridPane implements Initializable {

  private SnakeServiceViewModel viewModel;
  private SnakeCell[][] snakeCells;

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




  public SnakeBoard(SnakeServiceViewModel viewModel) {
    this.viewModel = viewModel;

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SnakeBoard.fxml"));
    fxmlLoader.setRoot(this);
    fxmlLoader.setController(this);

    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }

    snakeCells = new SnakeCell[viewModel.getSizeOfBoard()][viewModel.getSizeOfBoard()];

    for (int row = 0; row < viewModel.getSizeOfBoard(); row++) {
      for (int col = 0; col < viewModel.getSizeOfBoard(); col++) {
        SnakeCell cell = new SnakeCell();

        cell.setState(viewModel.currentBoardProperty().get().getStateFromPosition(row,col));
        snakeCells[row][col] = cell;
      }
    }

    // add All Snake Cells to the gameField GridPane
    for (int row = 0; row < viewModel.getSizeOfBoard(); row++) {
      gameField.addRow(row, snakeCells[row]);

    }

    this.setFocusTraversable(true);

    addKeyEvents();

  }

  private void addKeyEvents() {
    this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
      if(key.getCode()==KeyCode.A) {
        viewModel.snakeTurnLeft();
      }
    });

    this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
      if(key.getCode()==KeyCode.D) {
        viewModel.snakeTurnRight();
      }
    });

    this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
      if(key.getCode()==KeyCode.ESCAPE) {
        viewModel.pauseGame();
      }
    });
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
    viewModel.currentBoardProperty().addListener(observable -> updateBoard());
    resumeButton.setOnAction(event -> viewModel.continueGame());
    abortButton.setOnAction(event -> viewModel.abortGame());
    startButton.setOnAction(event -> viewModel.startGame());
    pauseButton.setOnAction(event -> viewModel.pauseGame());
  }

  // rework later to only update changed cells, setState only calls UI update if state of cell really changed!
  public void updateBoard() {
    for (int row = 0; row < viewModel.getSizeOfBoard(); row++) {
      for (int col = 0; col < viewModel.getSizeOfBoard(); col++) {
        snakeCells[row][col].setState(viewModel.currentBoardProperty().get().getStateFromPosition(row,col));
      }
    }
    //this.autosize();
  }

}
