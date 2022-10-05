package prog.ex03.solution.printer;

import prog.ex03.exercise.printer.Document;
import prog.ex03.exercise.printer.Printer;
import prog.ex03.exercise.printer.exceptions.NoColorPrinterException;
import prog.ex03.exercise.printer.exceptions.NoDuplexPrinterException;
import prog.ex03.exercise.printer.exceptions.NotEnoughPaperException;

/**
 * Abstract class for BwPrinter and ColorPrinter.
 */
public abstract class BasePrinter implements Printer {

  protected int numberOfSheetsOfPaper;
  protected boolean duplex;
  protected String name;

  public BasePrinter(String name, boolean duplex) {
    this.name = name;
    this.duplex = duplex;
  }

  @Override
  public void print(final Document document, final boolean duplex) throws
      IllegalArgumentException, NotEnoughPaperException,
      NoDuplexPrinterException, NoColorPrinterException {

    if (document == null) {
      throw new IllegalArgumentException("Document is null!");
    }

    if (duplex && !hasDuplex()) {
      throw new NoDuplexPrinterException(
          "Printer does not support duplex, but duplex is required for this document!");
    }

    if (document.isColor() && !hasColor()) {
      throw new NoColorPrinterException(
          "Document is in color, but this printer does not support color!");
    }

    if (duplex) {
      if ((document.getPages() / 2 + document.getPages() % 2) > numberOfSheetsOfPaper) {
        throw new NotEnoughPaperException("Not enough paper in tray for print!",
            (document.getPages() / 2 + document.getPages() % 2) - this.numberOfSheetsOfPaper);
      }
      numberOfSheetsOfPaper -= (document.getPages() / 2 + document.getPages() % 2);
    } else {
      if (document.getPages() > numberOfSheetsOfPaper) {
        throw new NotEnoughPaperException("Not enough paper in tray for print!",
            document.getPages() - this.numberOfSheetsOfPaper);
      }
      numberOfSheetsOfPaper -= document.getPages();
    }
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
  public void addPaper(final int numberOfSheets) throws IllegalArgumentException {
    if (numberOfSheets < 0) {
      throw new IllegalArgumentException("numberOfSheets should be greater of equal 0!");
    }
    this.numberOfSheetsOfPaper += numberOfSheets;
  }

  @Override
  public int getNumberOfSheetsOfPaper() {
    return this.numberOfSheetsOfPaper;
  }
}
