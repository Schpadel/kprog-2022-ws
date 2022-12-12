package livesession.snake.provider;

import livesession.snake.BoardState;
import livesession.snake.Coordinate;
import livesession.snake.Snake;

/**
 * Board implementation to be given to UI code. Contains all elements: GRASS, WALL, SNAKE, FOOD.
 * This is a copy of the internal board. So the UI code cannot manipulate the internal board.
 */
public class ExternalBoard extends BaseBoard {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(ExternalBoard.class);

  /**
   * Creates the external board based on the contents of the internal board.
   *
   * @param internalBoard internal board the contents is copied from
   * @param snake         actual snake
   */
  public ExternalBoard(InternalBoard internalBoard, final SimpleSnake snake) {
    super(internalBoard.size());
    copyContents(internalBoard);
    addSnake(snake);
  }

  /**
   * Copies the slow changing content from the internal board.
   *
   * @param internalBoard internal board with WALL, GRASS, FOOD, no snake.
   */
  private void copyContents(final InternalBoard internalBoard) {
    // TODO: Copy the contents of the internal board
    BoardState[][] copy = new BoardState[internalBoard.size][];
    int length = internalBoard.board.length;
    for (int i = 0; i < length; i++) {
      copy[i] = new BoardState[internalBoard.board[i].length];
      System.arraycopy(internalBoard.board[i], 0, copy[i], 0, internalBoard.board[i].length);
    }
    this.board = copy;
  }

  /**
   * Adds the snake to the board.
   *
   * @param snake snake to be added.
   */
  private void addSnake(Snake snake) {
    // TODO: Add the snake to the board
    for (Coordinate currentCoordinate : snake.getPosition()) {
      this.board[currentCoordinate.getRow()][currentCoordinate.getColumn()] = BoardState.SNAKE;
    }
    // TODO: end
  }
}
