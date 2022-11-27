package livesession.snake.javafx;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import livesession.snake.Board;
import livesession.snake.GameState;
import livesession.snake.Reason;
import livesession.snake.SnakeListener;
import livesession.snake.provider.ExtendedSnakeService;

public class SnakeServiceViewModel implements SnakeListener {

 private ExtendedSnakeService service;
  private IntegerProperty score;
  private ObjectProperty<GameState> currentGameState;
  private ObjectProperty<Board> board;


  public SnakeServiceViewModel(ExtendedSnakeService service) {
    this.service = service;
    service.addListener(this);
    score = new SimpleIntegerProperty();
    currentGameState = new SimpleObjectProperty<>();
    board = new SimpleObjectProperty<>();


  }

  public int getScore() {
    return score.get();
  }

  public int getSizeOfBoard() {
    return service.getBoard().size();
  }

  public IntegerProperty scoreProperty() {
    return score;
  }

  public GameState getCurrentGameState() {
    return currentGameState.get();
  }

  public ObjectProperty<GameState> currentGameStateProperty() {
    return currentGameState;
  }

  public ObjectProperty<Board> currentBoardProperty() {
    return this.board;
  }

  public void pauseGame() {
    service.pause();
  }

  public void startGame() {
    service.start();
  }

  public void continueGame() {
    service.resume();

  }

  public void abortGame() {
    service.abort();
  }

  public void snakeTurnLeft() {
    service.moveLeft();
  }

  public void snakeTurnRight() {
    service.moveRight();
  }
  @Override
  public void updateBoard(Board board) {
    this.board.set(board);

  }

  @Override
  public void newGameState(GameState state) {
    Platform.runLater(() -> currentGameState.set(state));
  }

  @Override
  public void gameEnded(Reason reason) {
    
  }

  @Override
  public void updateScore(int score) {
    Platform.runLater(() -> scoreProperty().setValue(score));
  }
}
