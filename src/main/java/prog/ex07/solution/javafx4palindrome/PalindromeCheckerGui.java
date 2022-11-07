package prog.ex07.solution.javafx4palindrome;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.FlowPane;
import prog.ex07.exercise.javafx4palindrome.Constants;
import prog.ex07.exercise.javafx4palindrome.PalindromeChecker;

/**
 * JavaFX component to wrap around a given PalindromeChecker.
 */
public class PalindromeCheckerGui extends FlowPane {
  private static final org.slf4j.Logger logger =
          org.slf4j.LoggerFactory.getLogger(PalindromeCheckerGui.class);
  TextField palindromeInput = new TextField();
  Label result = new Label();
  Button button = new Button("Check Palindrome!");

  /**
   * Create a new Palindrome Checker Gui.
   *
   * @param palindromeChecker checker to be used for GUI
   */
  public PalindromeCheckerGui(PalindromeChecker palindromeChecker) {

    palindromeInput.setOnKeyPressed((event) -> {
      if (event.getCode().equals(KeyCode.ENTER)) {
        evaluateInputPalindrome(palindromeChecker);
      }
    });
    this.getChildren().add(palindromeInput);
    this.getChildren().add(button);
    this.getChildren().add(result);
    button.setOnAction((event) -> {
      evaluateInputPalindrome(palindromeChecker);
      event.consume();
    });
  }

  private void evaluateInputPalindrome(PalindromeChecker palindromeChecker) {
    String resultText = palindromeChecker.isPalindrome(palindromeInput.getText())
        ? Constants.SUCCESS : Constants.FAILURE;
    result.setText(resultText);
  }
}
