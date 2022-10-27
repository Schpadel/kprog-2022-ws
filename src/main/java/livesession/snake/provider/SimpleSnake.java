package livesession.snake.provider;

import java.util.LinkedList;
import java.util.List;
import livesession.snake.Board;
import livesession.snake.BoardState;
import livesession.snake.Coordinate;
import livesession.snake.Direction;
import livesession.snake.Reason;
import livesession.snake.Snake;

/**
 * Simple and straight-forward implementation of the Snake interface.
 */
public class SimpleSnake implements Snake {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(SimpleSnake.class);
  private final InternalBoard board;
  private final ExtendedSnakeService service;
  // The LinkedList allows direct access to the head (first) and tail (last) of the snake.
  private final LinkedList<Coordinate> position;
  private Direction direction;

  /**
   * Creates a snake. A snake has at the beginning a length of 1.
   *
   * @param service service as single source of truth to get the internal board and to notify on
   *                events during wiggling.
   */
  public SimpleSnake(final ExtendedSnakeService service) {
    this.service = service;
    board = service.getInternalBoard();
    position = new LinkedList<>();
    position.addFirst(board.getStartPosition());
    direction = Direction.EAST;
  }

  /**
   * Advances the snake in the direction the snake is oriented to.
   *
   * @return new position of the head of the snake
   * @throws IllegalPositionException if the position is not allowed
   */
  public Coordinate advance() throws IllegalPositionException {
    // TODO: advance the snake
    Coordinate coordinate = position.get(0);
    Coordinate nextCoordinate = coordinate.getNeighbor(this.direction);
    board.assertPositionIsOnBoard(nextCoordinate.getRow(), nextCoordinate.getRow());

    // Snake moves onto food
    if (board.getStateFromPosition(nextCoordinate) == BoardState.FOOD) {
      Coordinate newElement;
      Coordinate lastElement = position.get(position.size() - 1);
      newElement = new Coordinate(lastElement.getRow(), lastElement.getColumn());
      position.add(newElement);
      this.service.foodEaten(nextCoordinate);
    }

    // Snake moves into a wall
    if (board.getStateFromPosition(nextCoordinate) == BoardState.WALL) {
      throw new IllegalPositionException(nextCoordinate, BoardState.WALL);
    }

    // Snake moves into snake

    for(Coordinate cord : position) {
      if (nextCoordinate.equals(cord)) {
        throw new IllegalPositionException(nextCoordinate, BoardState.SNAKE);
      }
    }

    // Snake did not hit anything adjust body?


    /*
    if (service.getExternalBoard().getStateFromPosition(nextCoordinate) == BoardState.SNAKE) {
      throw new IllegalPositionException(nextCoordinate, BoardState.SNAKE);
    }
    */
    return nextCoordinate;
  }

  private BoardState assertNewPositionIsPossible(final Coordinate newHead)
      throws IllegalPositionException {
    // TODO: Check if the position is valid
    if (this.board.getStateFromPosition(newHead) == BoardState.WALL) {
      throw new IllegalPositionException(newHead, BoardState.WALL);
    }
    if (this.service.getExternalBoard().getStateFromPosition(newHead) == BoardState.SNAKE) {
      throw new IllegalPositionException(newHead, BoardState.SNAKE);
    }
    return null;
  }

  @Override
  public List<Coordinate> getPosition() {
    // TODO: create cloned data
    List<Coordinate> newList = new LinkedList<>();
    for (Coordinate coordinate : position) {
      newList.add(new Coordinate(coordinate.getRow(), coordinate.getColumn()));
    }
    return newList;
    // TODO: create cloned data

  }

  @Override
  public Direction getDirection() {
    return direction;
  }

  /**
   * Changes the direction of the snake by turning left.
   */
  public void goLeft() {
    direction = direction.getLeft();
  }

  /**
   * Changes the direction of the snake by turning right.
   */
  public void goRight() {
    direction = direction.getRight();
  }
}
