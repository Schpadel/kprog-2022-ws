package prog.ex10.solution.javafx4pizzadelivery.gui;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import prog.ex10.exercise.javafx4pizzadelivery.gui.UnknownTransitionException;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaDeliveryService;

/**
 * Screen to create an order in the PizzaDeliveryService.
 */
public class CreateOrderScreen extends VBox {

  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CreateOrderScreen.class);

  public static final String SCREEN_NAME = "CreateOrderScreen";
  private PizzaDeliveryScreenController controller;


  /**
   * Constructs a new CreateOrderScreen and loads the fxml definition.
   *
   * @param screenController of this screen
   */
  public CreateOrderScreen(PizzaDeliveryScreenController screenController) {

    this.controller = screenController;

    //loads from the resources folder
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/CreateOrderScreen.fxml"));
    fxmlLoader.setController(this);
    try {
      Parent root = fxmlLoader.load();
      this.getChildren().clear();
      this.getChildren().addAll(root);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Updates the observable structures from the screen and loads the fxml definition.
   */
  public void updateScreen() {

  }

  /**
   * Handler method of the createNewOrder Button, creates a new order and switches to the
   * ShowOrderScreen.
   */
  public void createNewOrder() {
    PizzaDeliveryService service = (PizzaDeliveryService) SingletonAttributeStore.getInstance()
        .getAttribute("PizzaDeliveryService");
    int orderId = service.createOrder();
    SingletonAttributeStore.getInstance().setAttribute("orderId", orderId);

    try {
      controller.switchTo(CreateOrderScreen.SCREEN_NAME, ShowOrderScreen.SCREEN_NAME);
    } catch (UnknownTransitionException e) {
      e.printStackTrace();
    }
  }
}
