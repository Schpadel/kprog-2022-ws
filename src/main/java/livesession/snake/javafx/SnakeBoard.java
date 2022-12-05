package livesession.snake.javafx;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import livesession.snake.GameState;

/**
 * Class to model the board of the snake game as a GridPane with SnakeCells as Elements.
 */
public class SnakeBoard extends GridPane {

  private SnakeServiceViewModel viewModel;
  private SnakeCell[][] snakeCells;
  private SnakeDisplay snakeDisplay;

  /**
   * Create a new SnakeBoard instance, assign viewmodel and SnakeDisplay for this board.
   * Add all key events.
   *
   * @param viewModel to be used for this board
   * @param snakeDisplay to be used for this board.
   */
  public SnakeBoard(SnakeServiceViewModel viewModel, SnakeDisplay snakeDisplay) {
    this.viewModel = viewModel;
    this.snakeDisplay = snakeDisplay;

    //register changeListener
    viewModel.currentBoardProperty().addListener(observable -> updateBoard());
    addKeyEvents();
  }

  /**
   * Reset the size and state of this snakeBoard. Called by the constructor and when a new
   * GameConfiguration is set.
   */
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

  /**
   * Updates each cell in the board, setState only calls for a UI Change when there is a new state
   * available.
   */
  public void updateBoard() {
    for (int row = 0; row < viewModel.getSizeOfBoard(); row++) {
      for (int col = 0; col < viewModel.getSizeOfBoard(); col++) {
        snakeCells[row][col].setState(
            viewModel.currentBoardProperty().get().getStateFromPosition(row, col));
      }
    }
  }
}
