package livesession.snake.javafx;

import java.util.HashMap;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import livesession.snake.BoardState;

public class SnakeCell extends HBox {
  private BoardState state;

  private HashMap<BoardState, String> displayStates;


  public SnakeCell() {
    displayStates = new HashMap<>();
    displayStates.put(BoardState.GRASS, "-fx-background-color: green");
    displayStates.put(BoardState.SNAKE, "-fx-background-color: black");
    displayStates.put(BoardState.FOOD, "-fx-background-color: red");
    displayStates.put(BoardState.WALL, "-fx-background-color: orange");
    displayStates.put(null, "-fx-background-color: white");
    this.setStyle("-fx-background-color: green");


  }

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
