package prog.ex12.solution.datetimeapi;

import java.io.File;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import prog.ex12.exercise.datetimeapi.DateTimeService;
import prog.ex12.exercise.datetimeapi.EventInTime;
import prog.ex12.exercise.datetimeapi.NoDateTimeServiceStateException;

/**
 * Simple and straight-forward implementation of the DateTimeService interface.
 */
public class SimpleDateTimeService implements DateTimeService {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(SimpleDateTimeService.class);

  private Map<Integer, EventInTime> events;
  private static int idCounter = 0;

  public SimpleDateTimeService() {
    this.events = new HashMap<>();
  }

  @Override
  public Year nearestLeapYear(final Year year) throws IllegalArgumentException {
    if (year == null) {
      throw new IllegalArgumentException("Year to check for nearest Leap Year should not be null!");
    }

    if (year.isLeap()) {
      return year;
    }

    Year early = Year.from(year);
    Year later = Year.from(year);

    while (true) {
      early = early.minusYears(1);
      if (early.isLeap()) {
        return early;
      }
      later = later.plusYears(1);
      if (later.isLeap()) {
        return later;
      }
    }
  }

  @Override
  public DayOfWeek getDayOfWeek(final LocalDate localDate) throws IllegalArgumentException {
    if (localDate == null) {
      throw new IllegalArgumentException("Date should not be null!");
    }
    return localDate.getDayOfWeek();
  }

  @Override
  public Period timeBetweenNowAndThen(final LocalDate event) throws IllegalArgumentException {
    if (event == null) {
      throw new IllegalArgumentException("Date until should not be null!");
    }
    LocalDate now = LocalDate.now();
    return now.until(event);
  }

  @Override
  public Period timeBetweenNowAndThen(final int eventId) throws IllegalArgumentException {
    EventInTime currentEvent = events.get(eventId);

    if(currentEvent == null) {
      throw new IllegalArgumentException("No event found for given event id: " +eventId);
    }

    return LocalDate.now().until(currentEvent.getLocalDate());
  }

  @Override
  public int addEvent(final String event, final LocalDate localDate)
          throws IllegalArgumentException {
    if (event == null) {
      throw new IllegalArgumentException("Name of the event to be added should not be null!");
    }

    if (localDate == null) {
      throw new IllegalArgumentException("Date of the event to be added should not be null!");
    }

    int validLength = 0;
    for (Character current : event.toCharArray()) {
      if (Character.isAlphabetic(current) || Character.isDigit(current) || Character.isSpaceChar(current)) {
        validLength++;
        if (validLength >= 2) {
          break;
        }
      }else{
        throw new IllegalArgumentException("Name of the event is invalid!"
            + " Should only contain alphabetic, digits or space characters!");
      }
    }

    if (validLength < 2) {
      throw new IllegalArgumentException("Name of the event is too short, minimum 2 "
          + "characters are required!");
    }

    events.put(idCounter, new EventInTime(idCounter, event, localDate));
    int lastAssignedId = idCounter;
    idCounter++;
    return lastAssignedId;
  }


  @Override
  public List<EventInTime> getEvents() {
    return List.copyOf(events.values());
  }

  @Override
  public void removeEvent(final int eventId) throws IllegalArgumentException {

    if (!events.containsKey(eventId)) {
      throw new IllegalArgumentException("Event for event id " + eventId + " could not be found!");
    }
    events.remove(eventId);
  }

  @Override
  public void load(final File file) throws IOException, NoDateTimeServiceStateException {

  }

  @Override
  public void save(final File file) throws IOException {

  }
}
