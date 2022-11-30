package livesession.snake.javafx;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import livesession.snake.Board;
import livesession.snake.GameState;
import livesession.snake.Reason;
import livesession.snake.SnakeListener;
import livesession.snake.provider.ExtendedSnakeService;

public class SnakeServiceViewModel implements SnakeListener {

 private ExtendedSnakeService service;
  private IntegerProperty score;
  private ObjectProperty<GameState> currentGameStateProperty;

  private GameState currentGameState;
  private ObjectProperty<Board> board;
  private ObjectProperty<Reason> gameEndedProperty;

  public SnakeServiceViewModel(ExtendedSnakeService service) {
    this.service = service;
    service.addListener(this);
    score = new SimpleIntegerProperty();
    currentGameStateProperty = new SimpleObjectProperty<>();
    board = new SimpleObjectProperty<>();
    gameEndedProperty = new SimpleObjectProperty<>();
  }

  public Reason getGameEndedProperty() {
    return gameEndedProperty.get();
  }

  public ObjectProperty<Reason> gameEndedPropertyProperty() {
    return gameEndedProperty;
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
    return currentGameState;
  }

  public ObjectProperty<GameState> currentGameStatePropertyProperty() {
    return currentGameStateProperty;
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

  public void resetGame() {
    service.abort();
    service.reset();
    service.addListener(this);
    service.start();
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
    currentGameState = state;
    Platform.runLater(() -> currentGameStateProperty.set(state));
  }

  @Override
  public void gameEnded(Reason reason) {
    //TODO: Implement gameEnded Property to inform UI the game has ended with reason --> show UI End Screen
    gameEndedProperty.setValue(reason);
  }

  @Override
  public void updateScore(int score) {
    Platform.runLater(() -> scoreProperty().setValue(score));
  }
}
