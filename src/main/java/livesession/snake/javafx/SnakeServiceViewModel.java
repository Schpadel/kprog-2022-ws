package livesession.snake.javafx;

import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import livesession.snake.Board;
import livesession.snake.GameConfiguration;
import livesession.snake.GameState;
import livesession.snake.IllegalConfigurationException;
import livesession.snake.Reason;
import livesession.snake.SnakeListener;
import livesession.snake.provider.ExtendedSnakeService;
import livesession.snake.provider.SimpleSnakeService;

public class SnakeServiceViewModel implements SnakeListener {

  private ExtendedSnakeService service;
  private IntegerProperty score;
  private ObjectProperty<GameState> currentGameStateProperty;

  private GameState currentGameState;
  private ObjectProperty<Board> board;
  private ObjectProperty<Reason> gameEnded;
  private ObjectProperty<GameConfiguration> gameConfig;

  public GameConfiguration getGameConfig() {
    return gameConfig.get();
  }

  public ObjectProperty<GameConfiguration> gameConfigProperty() {
    return gameConfig;
  }

  public SnakeServiceViewModel(SimpleSnakeService service) {
    this.service = service;
    service.addListener(this);
    score = new SimpleIntegerProperty();
    currentGameStateProperty = new SimpleObjectProperty<>();
    board = new SimpleObjectProperty<>();
    gameEnded = new SimpleObjectProperty<>();
    gameConfig = new SimpleObjectProperty<>();
  }

  public Reason getGameEnded() {
    return gameEnded.get();
  }

  public ObjectProperty<Reason> gameEndedProperty() {
    return gameEnded;
  }

  public int getScore() {
    return score.get();
  }

  public int getSizeOfBoard() {
    return service.getConfiguration().getSize();
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

  public void configureGame(int size, int velocity, int numberOfFood)
      throws IllegalConfigurationException {

    service.configure(new GameConfiguration(size, velocity, numberOfFood));
    gameConfig.setValue(service.getConfiguration());
    service.addListener(this);
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
    gameEnded.setValue(reason);
  }

  @Override
  public void updateScore(int score) {
    Platform.runLater(() -> scoreProperty().setValue(score));
  }
}
