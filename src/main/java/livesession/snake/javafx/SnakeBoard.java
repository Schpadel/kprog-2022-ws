package livesession.snake.javafx;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import livesession.snake.BoardState;

public class SnakeBoard extends GridPane {

  private SnakeServiceViewModel viewModel;
  private SnakeCell[][] snakeCells;

  public SnakeBoard(SnakeServiceViewModel viewModel) {
    this.viewModel = viewModel;

    snakeCells = new SnakeCell[viewModel.getSizeOfBoard()][viewModel.getSizeOfBoard()];

    for (int row = 0; row < viewModel.getSizeOfBoard(); row++) {
      for (int col = 0; col < viewModel.getSizeOfBoard(); col++) {
        SnakeCell cell = new SnakeCell();
        cell.setState(BoardState.GRASS);
        snakeCells[row][col] = cell;
      }
    }

    // add All Snake Cells to this GridPane
    for (int row = 0; row < viewModel.getSizeOfBoard(); row++) {
      this.addRow(row, snakeCells[row]);

    }

    this.setFocusTraversable(true);

    addKeyEvents();

    //register changeListener
    viewModel.currentBoardProperty().addListener(observable -> updateBoard());

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

  // rework later to only update changed cells, setState only calls UI update if state of cell really changed!
  public void updateBoard() {
    for (int row = 0; row < viewModel.getSizeOfBoard(); row++) {
      for (int col = 0; col < viewModel.getSizeOfBoard(); col++) {
        snakeCells[row][col].setState(viewModel.currentBoardProperty().get().getStateFromPosition(row,col));
      }
    }
  }

}
