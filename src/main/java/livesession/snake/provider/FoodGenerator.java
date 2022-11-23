package livesession.snake.provider;

import java.util.Random;
import livesession.snake.BoardState;
import livesession.snake.Coordinate;

/**
 * Simple FoodGenerator class for the snake game.
 */
public class FoodGenerator {
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(FoodGenerator.class);

  private final SimpleSnakeService service;
  private final Random random;

  /**
   * Constructor.
   *
   * @param service SnakeService the FoodGenerator is assigned to
   */
  public FoodGenerator(final SimpleSnakeService service) {
    this.service = service;
    // Use a seed to make the series of "random" coordinates deterministic. Helps with tests.
    this.random = new Random(42);
  }

  Coordinate placeFood() {
    // TODO: place the food randomly.

    int numberOfCells = service.getConfiguration().getSize() * service.getConfiguration().getSize();
    int counter = 0;
    while(counter < numberOfCells) {
      Coordinate coordinate = getRandomCoordinate();
      if (service.getExternalBoard().getStateFromPosition(coordinate) == BoardState.GRASS) {
        service.getInternalBoard().addFood(coordinate);
        return coordinate;
      }
      counter++;


    }
    // no empty space for food found on entire board!
    return null;

    // TODO: end.

  }

  private Coordinate getRandomCoordinate() {
    int size = service.getBoard().size();

    int row = random.nextInt(size);
    int column = random.nextInt(size);

    return new Coordinate(row, column);
  }
}
