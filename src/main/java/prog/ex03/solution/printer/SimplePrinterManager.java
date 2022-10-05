package prog.ex03.solution.printer;

import java.util.List;
import prog.ex03.exercise.printer.Printer;
import prog.ex03.exercise.printer.PrinterManager;
import prog.ex03.exercise.printer.exceptions.PrinterAlreadyRegisteredException;
import prog.ex03.exercise.printer.exceptions.PrinterNotRegisteredException;


/**
 * Implements the PrinterManager in a simple and straightforward way.
 */
public class SimplePrinterManager implements PrinterManager {

  List<Printer> printerList;

  @Override
  public Printer getPrinter(final String name)
      throws IllegalArgumentException, PrinterNotRegisteredException {

    if (name == null) {
      throw new IllegalArgumentException("Name should not be null!");
    }

    if (name.trim().length() == 0) {
      throw new IllegalArgumentException("Name should not be empty or consist of only spaces!");
    }

    for (Printer current : printerList) {
      if (current.getName().equals(name)) {
        return current;
      }
    }

    throw new PrinterNotRegisteredException(
        "Printer: " + name + " is not registered in this manager!");
  }

  @Override
  public List<Printer> getAllPrinters() {
    return this.printerList;
  }

  @Override
  public void addPrinter(final Printer printer)
      throws IllegalArgumentException, PrinterAlreadyRegisteredException {

    if (printer == null) {
      throw new IllegalArgumentException("Printer to add should not be null!");
    }

    try {
      getPrinter(printer.getName());
    } catch (PrinterNotRegisteredException e) {
      if (printer.getName().matches("\\p{Print}+") && !printer.getName().contains(" ")) {
        printerList.add(printer);
      }
      throw new IllegalArgumentException("Printer name not valid!");
    }
    throw new PrinterAlreadyRegisteredException("This printer was already registered!");
  }

  @Override
  public void removePrinter(final String name)
      throws IllegalArgumentException, PrinterNotRegisteredException {

    printerList.remove(getPrinter(name));
  }

  @Override
  public int getNumberOfColorPrinters() {
    int count = 0;
    for (Printer current : printerList) {
      if (current.hasColor()) {
        count++;
      }
    }
    return count;
  }

  @Override
  public int getNumberOfBwPrinters() {
    int count = 0;
    for (Printer current : printerList) {
      if (!current.hasColor()) {
        count++;
      }
    }
    return count;
  }
}
