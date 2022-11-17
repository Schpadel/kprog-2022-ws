package prog.ex10.solution.javafx4pizzadelivery.gui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

/**
 * Screen to create an order in the PizzaDeliveryService.
 */
public class CreateOrderScreen extends VBox {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(CreateOrderScreen.class);

  public static final String SCREEN_NAME = "CreateOrderScreen";

  public CreateOrderScreen(PizzaDeliveryScreenController screenController) {

    //loads from the resources folder
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CreateOrderScreen.fxml"));
    //fxmlLoader.setRoot(this);
    fxmlLoader.setController(screenController);
    try {
      Parent root = fxmlLoader.load();
      this.getChildren().clear();
      this.getChildren().addAll(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
