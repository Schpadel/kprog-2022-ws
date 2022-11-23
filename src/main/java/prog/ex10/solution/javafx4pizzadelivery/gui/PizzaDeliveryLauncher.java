package prog.ex10.solution.javafx4pizzadelivery.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import prog.ex10.exercise.javafx4pizzadelivery.gui.ScreenController;
import prog.ex10.exercise.javafx4pizzadelivery.pizzadelivery.PizzaDeliveryService;
import prog.ex10.solution.javafx4pizzadelivery.pizzadelivery.SimplePizzaDeliveryService;

/**
 * Launches the PizzaDeliveryService order application.
 */
public class PizzaDeliveryLauncher extends Application {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(PizzaDeliveryLauncher.class);

  @Override
  public void start(final Stage stage) throws Exception {
    logger.info("Can't see something ...");
    TabPane root = new TabPane();
    Tab welcomeTab = new Tab("Welcome", new Label("Your advertisement could be here."));
    root.getTabs().add(welcomeTab);


    SingletonAttributeStore attributeStore = SingletonAttributeStore.getInstance();
    PizzaDeliveryService service = new SimplePizzaDeliveryService();
    attributeStore.setAttribute("PizzaDeliveryService", service);
    Pane paneToBeFilled = new Pane();
    paneToBeFilled.getChildren().add(new Label("Your advertisement could be here ..."));
    Tab pizzaDeliveryTab = new Tab("PizzaDeliveryService", paneToBeFilled);
    ScreenController controller = new PizzaDeliveryScreenController(paneToBeFilled);
    controller.switchTo(null, CreateOrderScreen.SCREEN_NAME);
    root.getTabs().add(pizzaDeliveryTab);

    stage.setTitle("KPROG JavaFX wonderful GUIs");
    stage.setScene(new Scene(root));
    stage.show();

  }
}
