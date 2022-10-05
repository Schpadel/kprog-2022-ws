package prog.ex02.solution.printer;

import java.util.ArrayList;
import java.util.List;
import prog.ex02.exercise.printer.Printer;
import prog.ex02.exercise.printer.PrinterManager;


/**
 * Realizes a simple PrintManager.
 */
public class SimplePrinterManager implements PrinterManager {

  List<Printer> printerList;

  public SimplePrinterManager() {
    printerList = new ArrayList<>();
  }

  @Override
  public Printer getPrinter(final String name) {
    for (Printer current : printerList) {
      if (current.getName().equals(name)) {
        return current;
      }
    }

    return null;
  }

  @Override
  public List<Printer> getAllPrinters() {
    return this.printerList;
  }

  @Override
  public boolean addPrinter(final Printer printer) {

    if (printer == null) {
      return false;
    }

    if (getPrinter(printer.getName()) != null) {
      return false;
    }

    if (printer.getName().matches("\\p{Print}+") && !printer.getName().contains(" ")) {
      printerList.add(printer);
      return true;
    }
    return false;
  }

  @Override
  public boolean removePrinter(final String name) {

    if (name == null) {
      return false;
    }

    if (getPrinter(name) == null) {
      return false;
    } else {
      printerList.remove(getPrinter(name));
      return true;
    }

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
