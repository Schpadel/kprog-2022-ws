package prog.ex10.solution.javafx4pizzadelivery.gui;

import java.util.HashMap;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import prog.ex10.exercise.javafx4pizzadelivery.gui.ScreenController;
import prog.ex10.exercise.javafx4pizzadelivery.gui.UnknownTransitionException;

/**
 * Simple and straight-forward implementation of a ScreenController for the PizzaDeliveryService.
 */
public class PizzaDeliveryScreenController implements ScreenController {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(PizzaDeliveryScreenController.class);
  private Pane pane;


  HashMap<String, VBox> allScreens;

  /**
   * Constructs a new instance of the PizzaDeliveryScreenController, which manages all Screens
   * of the PizzaDeliveryService GUI.
   *
   * @param pane to be used to display the individual screens.
   */
  public PizzaDeliveryScreenController(final Pane pane) {
    allScreens = new HashMap<>();
    this.pane = pane;

    CreateOrderScreen createOrderScreen = new CreateOrderScreen(this);
    allScreens.put(CreateOrderScreen.SCREEN_NAME, createOrderScreen);

    EditPizzaScreen editPizzaScreen = new EditPizzaScreen(this);
    allScreens.put(EditPizzaScreen.SCREEN_NAME, editPizzaScreen);

    ShowOrderScreen showOrderScreen = new ShowOrderScreen(this);
    allScreens.put(ShowOrderScreen.SCREEN_NAME, showOrderScreen);

    // allow transition from null screen
    allScreens.put(null, null);

    pane.getChildren().add(createOrderScreen);

  }

  @Override
  public void switchTo(final String fromScreen, final String toScreen)
          throws UnknownTransitionException {
    if (!allScreens.containsKey(fromScreen)) {
      throw new UnknownTransitionException("Screen is unknown", fromScreen, toScreen);
    }

    switch (toScreen) {
      case EditPizzaScreen.SCREEN_NAME:
        EditPizzaScreen editScreen = (EditPizzaScreen) allScreens.get(toScreen);
        editScreen.updateScreen();
        break;
      case ShowOrderScreen.SCREEN_NAME:
        ShowOrderScreen orderScreen = (ShowOrderScreen) allScreens.get(toScreen);
        orderScreen.updateScreen();
        break;
      case CreateOrderScreen.SCREEN_NAME:
        CreateOrderScreen createOrderScreen = (CreateOrderScreen) allScreens.get(toScreen);
        createOrderScreen.updateScreen();
        break;
      default:
        throw new UnknownTransitionException("Screen is unknown", fromScreen, toScreen);
    }
    pane.getChildren().clear();
    pane.getChildren().add(allScreens.get(toScreen));

  }
}
