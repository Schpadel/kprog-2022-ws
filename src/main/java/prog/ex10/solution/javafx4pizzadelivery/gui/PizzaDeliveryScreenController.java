package prog.ex10.solution.javafx4pizzadelivery.gui;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import prog.ex10.exercise.javafx4pizzadelivery.gui.AttributeStore;
import prog.ex10.exercise.javafx4pizzadelivery.gui.ScreenController;
import prog.ex10.exercise.javafx4pizzadelivery.gui.UnknownTransitionException;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaDeliveryService;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaSize;

/**
 * Simple and straight-forward implementation of a ScreenController for the PizzaDeliveryService.
 */
public class PizzaDeliveryScreenController implements ScreenController, Initializable {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(PizzaDeliveryScreenController.class);
  private Pane pane;

  private SimpleIntegerProperty orderPriceProperty;


  HashMap<String, VBox> allScreens;

  public PizzaDeliveryScreenController(final Pane pane) {
    allScreens = new HashMap<>();
    this.pane = pane;
    CreateOrderScreen createOrderScreen = new CreateOrderScreen(this);
    allScreens.put(CreateOrderScreen.SCREEN_NAME, createOrderScreen);
    EditPizzaScreen editPizzaScreen = new EditPizzaScreen(this);

    allScreens.put(EditPizzaScreen.SCREEN_NAME, editPizzaScreen);

    pane.getChildren().add(createOrderScreen);

  }


  @Override
  public void switchTo(final String fromScreen, final String toScreen)
          throws UnknownTransitionException {
    if (!allScreens.containsKey(toScreen)) {
      throw new UnknownTransitionException("Screen is unknown", fromScreen, toScreen);
    }

    switch (toScreen) {
      case EditPizzaScreen.SCREEN_NAME:
        EditPizzaScreen editScreen = (EditPizzaScreen) allScreens.get(toScreen);
        editScreen.initScreen();
        break;
      case ShowOrderScreen.SCREEN_NAME:
        ShowOrderScreen orderScreen = (ShowOrderScreen) allScreens.get(toScreen);
        orderScreen.updateScreen();
        break;
      case CreateOrderScreen.SCREEN_NAME:
        // implement init
        break;
      default:
        throw new UnknownTransitionException("Screen is unknown", fromScreen, toScreen);
    }
    pane.getChildren().clear();
    pane.getChildren().add(allScreens.get(toScreen));

  }

  public void createNewOrder() {
    PizzaDeliveryService service = (PizzaDeliveryService) SingletonAttributeStore.getInstance().getAttribute("PizzaDeliveryService");
    int orderId = service.createOrder();
    SingletonAttributeStore.getInstance().setAttribute("orderId", orderId);

    ShowOrderScreen showOrderScreen = new ShowOrderScreen(this);
    allScreens.put(ShowOrderScreen.SCREEN_NAME, showOrderScreen);

    try {
      switchTo(CreateOrderScreen.SCREEN_NAME, ShowOrderScreen.SCREEN_NAME);
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

  }
}
