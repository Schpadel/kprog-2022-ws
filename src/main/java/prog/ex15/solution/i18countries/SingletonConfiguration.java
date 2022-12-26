package prog.ex15.solution.i18countries;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
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
    country2LocaleMap.put(Country.ENGLAND, new Locale("en", "EN"));
    country2LocaleMap.put(Country.DENMARK, new Locale("dk", "DK"));
    country2LocaleMap.put(Country.GERMANY, Locale.GERMANY);
    country2LocaleMap.put(Country.NETHERLANDS, new Locale("nl", "NL"));
    this.listenerArrayList = new ArrayList<>();
    currentLocale = Locale.ENGLISH;

    loadBundles();

  }

  private void loadBundles() {
    currentTypicalBundle = ResourceBundle.getBundle("prog.ex15.solution.i18countries.multilingual.listresourcebundle.CountryBundle",
        currentLocale);
    currentMessageBundle = ResourceBundle.getBundle("bundles/i18ncountries", currentLocale);
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
    logger.info("Setting Locale to: " + locale.toString());
    Locale oldLocale = currentLocale;
    currentLocale = locale;


    // Reload resource bundles

    loadBundles();

    logger.info("Current message bundle: " + currentMessageBundle.toString());
    logger.info("Current typical bundle: " + currentTypicalBundle.getClass());

    for (PropertyChangeListener current : listenerArrayList) {
      current.propertyChange(new PropertyChangeEvent(this, "currentLocale", oldLocale, currentLocale));
    }
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
