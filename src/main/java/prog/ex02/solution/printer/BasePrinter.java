package prog.ex02.solution.printer;

import prog.ex02.exercise.printer.Document;
import prog.ex02.exercise.printer.Printer;

/**
 * Realizes a basic Printer implementation.
 */
public abstract class BasePrinter implements Printer {

  protected int numberOfSheetsOfPaper;
  protected boolean duplex;
  protected String name;

  public BasePrinter(String name, boolean duplex) {
    this.name = name;
    this.duplex = duplex;
  }

  public abstract boolean hasColor();

  @Override
  public boolean print(final Document document, final boolean duplex) {

    if (document == null) {
      return false;
    }

    if (duplex && !hasDuplex()) {
      return false;
    }

    if (document.isColor() && !hasColor()) {
      return false;
    }

    if (duplex) {
      if ((document.getPages() / 2 + document.getPages() % 2) > numberOfSheetsOfPaper) {
        return false;
      }
      numberOfSheetsOfPaper -= (document.getPages() / 2 + document.getPages() % 2);
      return true;
    } else {
      if (document.getPages() > numberOfSheetsOfPaper) {
        return false;
      }
      numberOfSheetsOfPaper -= document.getPages();
    }
    return true;
  }

  @Override
  public boolean hasDuplex() {
    return this.duplex;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean addPaper(final int numberOfSheets) {
    if (numberOfSheets >= 0) {
      this.numberOfSheetsOfPaper += numberOfSheets;
      return true;
    }
    return false;
  }

  @Override
  public int getNumberOfSheetsOfPaper() {
    return this.numberOfSheetsOfPaper;
  }
}
