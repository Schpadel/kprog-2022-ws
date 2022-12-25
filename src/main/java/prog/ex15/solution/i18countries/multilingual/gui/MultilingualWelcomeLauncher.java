package prog.ex15.solution.i18countries.multilingual.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import prog.ex15.exercise.i18ncountries.CountryKnowledgeContainer;
import prog.ex15.monolingual.StupidKnowledgeGenerator;
import prog.ex15.solution.i18countries.I18NKnowledgeGenerator;

/**
 * Main to launch the WelcomeToMyCountry content in a separate application.
 */
public class MultilingualWelcomeLauncher extends Application {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(MultilingualWelcomeLauncher.class);

  @Override
  public void start(final Stage stage) throws Exception {
    CountryKnowledgeContainer container = new I18NKnowledgeGenerator().fillContainer();
    FxKnowledgePresenter presenter = new FxKnowledgePresenter(container);
    stage.setScene(new Scene(presenter, 400, 300));
    stage.show();
  }
}
