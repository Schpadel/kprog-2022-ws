package livesession.snake.javafx;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import livesession.snake.BoardState;
import livesession.snake.GameState;

public class SnakeBoard extends GridPane {

  private SnakeServiceViewModel viewModel;
  private SnakeCell[][] snakeCells;
  private SnakeDisplay snakeDisplay;

  public SnakeBoard(SnakeServiceViewModel viewModel, SnakeDisplay snakeDisplay) {
    this.viewModel = viewModel;
    this.snakeDisplay = snakeDisplay;

    //register changeListener
    viewModel.currentBoardProperty().addListener(observable -> updateBoard());
    addKeyEvents();
  }

  public void reset() {
    this.getChildren().clear();
    snakeCells = new SnakeCell[viewModel.getSizeOfBoard()][viewModel.getSizeOfBoard()];

    for (int row = 0; row < viewModel.getSizeOfBoard(); row++) {
      for (int col = 0; col < viewModel.getSizeOfBoard(); col++) {
        SnakeCell cell = new SnakeCell();
        cell.setState(null);
        snakeCells[row][col] = cell;
      }
    }

    // add All Snake Cells to this GridPane
    for (int row = 0; row < viewModel.getSizeOfBoard(); row++) {
      this.addRow(row, snakeCells[row]);

    }

    this.setFocusTraversable(true);



  }

  private void addKeyEvents() {
    this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
      if (key.getCode() == KeyCode.A && viewModel.getCurrentGameState() == GameState.RUNNING) {
        viewModel.snakeTurnLeft();
      }
    });

    this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
      if (key.getCode() == KeyCode.D && viewModel.getCurrentGameState() == GameState.RUNNING) {
        viewModel.snakeTurnRight();
      }
    });

    this.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
      if (key.getCode() == KeyCode.ESCAPE && viewModel.getCurrentGameState() == GameState.RUNNING) {
        snakeDisplay.handleAction(method -> viewModel.pauseGame());
      }
    });
  }

  // only calls UI update if state of cell really changed!
  public void updateBoard() {
    for (int row = 0; row < viewModel.getSizeOfBoard(); row++) {
      for (int col = 0; col < viewModel.getSizeOfBoard(); col++) {
        snakeCells[row][col].setState(
            viewModel.currentBoardProperty().get().getStateFromPosition(row, col));
      }
    }
  }
}
