package prog.ex15.solution.i18countries;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import prog.ex15.exercise.i18ncountries.Configuration;
import prog.ex15.exercise.i18ncountries.Country;

/**
 * Singleton-based implementation of the Configuration interface.
 */
public class SingletonConfiguration implements Configuration {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(SingletonConfiguration.class);

  private Locale currentLocale;

  private HashMap<Country, Locale> country2LocaleMap;
  private ArrayList<PropertyChangeListener> listenerArrayList;
  private ResourceBundle currentTypicalBundle;
  private ResourceBundle currentMessageBundle;

  private static SingletonConfiguration instance = new SingletonConfiguration();

  public SingletonConfiguration() {
    this.country2LocaleMap = new HashMap<>();
    this.listenerArrayList = new ArrayList<>();
    //TODO: load dummy ressource bundle.
  }

  public static SingletonConfiguration getInstance() {
    return instance;
  }

  @Override
  public Locale getLocale() {
    return currentLocale;
  }

  @Override
  public void setLocale(final Locale locale) {
    currentLocale = locale;
  }

  @Override
  public ResourceBundle getTypicalBundle() {
    return currentTypicalBundle;
  }

  @Override
  public ResourceBundle getMessageBundle() {
    return currentMessageBundle;
  }

  @Override
  public Map<Country, Locale> getCountry2LocaleMap() {
    return country2LocaleMap;
  }

  @Override
  public void addPropertyChangeListener(final PropertyChangeListener listener) {
    listenerArrayList.add(listener);
  }

  @Override
  public void removePropertyChangeListener(final PropertyChangeListener listener) {
    listenerArrayList.remove(listener);
  }
}
