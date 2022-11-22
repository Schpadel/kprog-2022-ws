package prog.ex10.solution.javafx4pizzadelivery.gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import prog.ex10.exercise.javafx4pizzadelivery.gui.UnknownTransitionException;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaDeliveryService;

/**
 * Screen to create an order in the PizzaDeliveryService.
 */
public class CreateOrderScreen extends VBox implements PizzaDeliveryScreen, Initializable {

  public static final String SCREEN_NAME = "CreateOrderScreen";
  private static final org.slf4j.Logger logger =
      org.slf4j.LoggerFactory.getLogger(CreateOrderScreen.class);
  @FXML
  Button createNewOrderButton;
  private PizzaDeliveryScreenController controller;
  private CreateOrderViewModel viewModel;

  /**
   * Constructs a new CreateOrderScreen and loads the fxml definition.
   *
   * @param screenController of this screen
   */
  public CreateOrderScreen(PizzaDeliveryScreenController screenController,
      CreateOrderViewModel viewModel) {
    this.viewModel = viewModel;
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
   * Updates the observable structures in the view model.
   */
  @Override
  public void updateScreen() {
    // no structures for this screen
  }

  /**
   * Handler method of the createNewOrder Button, creates a new order and switches to the
   * ShowOrderScreen.
   */
  public void createNewOrder() {
    PizzaDeliveryService service = viewModel.getService();
    int orderId = service.createOrder();
    viewModel.setOrderId(orderId);

    try {
      controller.switchTo(CreateOrderScreen.SCREEN_NAME, ShowOrderScreen.SCREEN_NAME);
    } catch (UnknownTransitionException e) {
      e.printStackTrace();
    }
  }

  /**
   * Called to initialize a controller after its root element has been completely processed.
   *
   * @param location  The location used to resolve relative paths for the root object, or
   *                  {@code null} if the location is not known.
   * @param resources The resources used to localize the root object, or {@code null} if the root
   *                  object was not localized.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    createNewOrderButton.setOnAction(event -> createNewOrder());
  }
}
