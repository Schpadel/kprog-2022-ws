package livesession.snake.provider;

import livesession.snake.BoardState;
import livesession.snake.Coordinate;

public class InternalBoard extends BaseBoard {
    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(InternalBoard.class);

    public InternalBoard(final int size) {
        super(size);

        // TODO: Init board with GRASS and WALLs

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (row == 0) {
                    board[row][col] = BoardState.WALL;
                    continue;
                }

                if (row == board.length - 1) {
                    board[row][col] = BoardState.WALL;
                    continue;
                }

                if (col == 0 || col == board[row].length - 1) {
                    board[row][col] = BoardState.WALL;
                    continue;
                }

                board[row][col] = BoardState.GRASS;

            }
        }
    }

    public Coordinate getStartPosition() {
        return new Coordinate(size / 2, size / 2);
    }

    protected void addFood(Coordinate coordinate) {
        assertPositionIsOnBoard(coordinate);
        board[coordinate.getRow()][coordinate.getColumn()] = BoardState.FOOD;
    }

    protected void removeFood(Coordinate coordinate) {
        assertPositionIsOnBoard(coordinate);
        board[coordinate.getRow()][coordinate.getColumn()] = BoardState.GRASS;
    }

}
