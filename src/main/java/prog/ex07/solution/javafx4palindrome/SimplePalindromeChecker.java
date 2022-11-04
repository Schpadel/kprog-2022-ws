package prog.ex07.solution.javafx4palindrome;

import java.util.ArrayList;
import java.util.List;
import prog.ex07.exercise.javafx4palindrome.PalindromeChecker;

/**
 * Simple palindrome checker.
 */
public class SimplePalindromeChecker implements PalindromeChecker {
  @Override
  public boolean isPalindrome(final String line) {
    char[] normalizedLine = normalizeLine(line);

    if(line.strip().length() == 0) {
      return false;
    }

    int reverseCounter = normalizedLine.length - 1;
    for (char c : normalizedLine) {
      if (c != normalizedLine[reverseCounter]) {
        return false;
      }
      reverseCounter--;
    }
    return true;
  }

  @Override
  public char[] normalizeLine(final String line) {
    String temp;
    List<Character> resultList = new ArrayList<>();
    temp = line.replace(" ", "");
    temp = temp.toLowerCase();
    for (char current : temp.toCharArray()) {
      if (Character.isAlphabetic(current) || Character.isDigit(current)) {
        resultList.add(current);
      }
    }

    char[] resultArray = new char[resultList.size()];

    for (int i = 0; i < resultList.size(); i++) {
      resultArray[i] = resultList.get(i);
    }

    return resultArray;
  }
}
