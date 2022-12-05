package livesession.snake.javafx;

import java.util.HashMap;
import javafx.application.Platform;
import javafx.scene.layout.HBox;
import livesession.snake.BoardState;

/**
 * A single Cell which is used to display the state of the snake game.
 */
public class SnakeCell extends HBox {
  private BoardState state;

  private HashMap<BoardState, String> displayStates;

  /**
   * Constructs a new Instance of a SnakeCell.
   */
  public SnakeCell() {
    displayStates = new HashMap<>();
    displayStates.put(BoardState.GRASS, "-fx-background-color: green");
    displayStates.put(BoardState.SNAKE, "-fx-background-color: black");
    displayStates.put(BoardState.FOOD, "-fx-background-color: red");
    displayStates.put(BoardState.WALL, "-fx-background-color: orange");
    displayStates.put(null, "-fx-background-color: white");
    this.setStyle("-fx-background-color: green");


  }

  /**
   * Sets a new state for the cell. If the state is different to the current state, the UiDisplay
   * is being updated.
   *
   * @param state new state for this cell.
   */
  public void setState(BoardState state) {
    if (this.state != state) {
      this.state = state;
      Platform.runLater(() -> updateUiDisplay());
    }
  }

  private void updateUiDisplay() {
    this.setStyle(displayStates.get(state));
    this.setPrefHeight(20);
    this.setPrefWidth(20);

  }
}
