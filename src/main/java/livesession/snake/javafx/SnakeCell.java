package livesession.snake.javafx;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import livesession.snake.BoardState;

public class SnakeCell extends HBox {
  private BoardState state;

  //for testing
  private Label stateLabel;


  public SnakeCell() {
    stateLabel = new Label("Empty");
    this.getChildren().add(stateLabel);


  }

  public void setState(BoardState state) {
    if (this.state != state) {
      this.state = state;
      Platform.runLater(() -> updateUIDisplay() );
    }
  }

  private void updateUIDisplay() {
    stateLabel.setText(state.name());
    stateLabel.autosize();

  }
}
