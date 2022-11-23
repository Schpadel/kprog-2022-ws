module kprog.main {
  requires java.desktop;
  requires org.slf4j;
  requires javafx.controls;
  requires javafx.fxml;
  exports environment.testprograms;
  exports examples.javafx.bidirectional;
  exports examples.javafx.firststeps;
  exports examples.javafx.fxml;
  exports examples.javafx.listcell;
  exports examples.javafx.misc;
  exports examples.javafx.modal;
  exports examples.javafx.mvvm;
  exports examples.javafx.observable;
  exports examples.javafx.addressbook;
  exports examples.io;
  exports examples.designpattern.observer;
  exports examples.designpattern.singleton;
  exports prog.javafx.main;
  exports prog.ex10.solution.javafx4pizzadelivery.gui;
  opens examples.javafx.fxml to javafx.fxml;
  opens prog.ex10.solution.javafx4pizzadelivery.gui to javafx.fxml;
}
