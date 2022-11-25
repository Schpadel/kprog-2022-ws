package livesession.snake.javafx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import livesession.snake.Board;
import livesession.snake.BoardState;
import livesession.snake.GameState;
import livesession.snake.Reason;
import livesession.snake.SnakeListener;
import livesession.snake.provider.ExtendedSnakeService;

public class SnakeServiceViewModel implements SnakeListener {

 private ExtendedSnakeService service;
  private IntegerProperty score;
  private ObjectProperty<BoardState> currentBoardState;
  private ObjectProperty<Board> board;


  public SnakeServiceViewModel(ExtendedSnakeService service) {
    this.service = service;
    service.addListener(this);
    score = new SimpleIntegerProperty();
    currentBoardState = new SimpleObjectProperty<>();
    board = new SimpleObjectProperty<>();
  }

  public int getScore() {
    return score.get();
  }

  public IntegerProperty scoreProperty() {
    return score;
  }

  public BoardState getCurrentBoardState() {
    return currentBoardState.get();
  }

  public ObjectProperty<BoardState> currentBoardStateProperty() {
    return currentBoardState;
  }

  public void pauseGame() {
    service.pause();
  }

  public void startGame() {
    service.start();
  }

  @Override
  public void updateBoard(Board board) {

  }

  @Override
  public void newGameState(GameState state) {

  }

  @Override
  public void gameEnded(Reason reason) {

  }

  @Override
  public void updateScore(int score) {

  }
}
