package calculator;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class RegexCheck {
    String input;
    RegexCheck(String input) {
        this.input = input;
    }
    Pattern formatRegex = Pattern.compile("[\\-\\+]*[0-9]+|^[+-]?[0-9][\\d\\s\\+\\-\\*\\/]*[0-9]$");
    Pattern plusRegex = Pattern.compile("[\\+]+");
    Pattern spaceRegex = Pattern.compile("[ ]+");
    Pattern minusOddRegex = Pattern.compile("\\-{3}");
    Pattern minusEvenRegex = Pattern.compile("\\-{2}");


    Pattern slashRegex = Pattern.compile("^/.*");

    String checkFormat() {
        Matcher slashMatcher = slashRegex.matcher(this.input);
        if (slashMatcher.matches()){
            if (this.input.equals("/exit")) {
                return "Bye!";
            } else if (this.input.equals("/help")){
                return "The program calculates the sum of numbers";
            } else {
                return "Unknown Command";
            }
        } else {
            Matcher formatMatcher = formatRegex.matcher(this.input);
            if (formatMatcher.matches()) {
                Matcher minusOddMatcher = minusOddRegex.matcher(this.input);
                while (minusOddMatcher.find()) {
                    this.input = minusOddMatcher.replaceAll("-");
                    minusOddMatcher = minusOddRegex.matcher(this.input);
                }
                Matcher minusEvenMatcher = minusEvenRegex.matcher(this.input);
                this.input = minusEvenMatcher.replaceAll("+");
                Matcher plusMatcher = plusRegex.matcher(this.input);
                this.input = plusMatcher.replaceAll(" + ");
                Matcher spaceMatcher = spaceRegex.matcher(this.input);
                this.input = spaceMatcher.replaceAll(" ");
                return this.input;
            } else {
                return "Invalid expression";
            }
        }
    }
}
public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        while (true) {
            String inp = scanner.nextLine();
            if (!inp.equals("")) {
                RegexCheck rc = new RegexCheck(inp);
                String retStr = rc.checkFormat();
                if (!retStr.matches(".*[a-zA-Z].*")) {
                    Pattern minusSpaceRegex = Pattern.compile("- ");
                    Matcher matcher = minusSpaceRegex.matcher(retStr);
                    retStr = matcher.replaceAll("-");
//                    System.out.println(retStr);

                    Pattern plusSpaceRegex = Pattern.compile(" \\+ ");
                    matcher = plusSpaceRegex.matcher(retStr);
                    retStr = matcher.replaceAll(" ");
//                    System.out.println(retStr);

                    String[] numbSym = retStr.split(" ");
                    int sum = 0;
                    for (int i = 0; i < numbSym.length; i++) {
                        if (!numbSym[i].equals(""))
                            sum += Integer.parseInt(numbSym[i]);
                    }
                    System.out.println(sum);
                } else {
                    if (retStr.equals("Bye!")) {
                        System.out.println(retStr);
                        return;
                    } else {
                        System.out.println(retStr);
                    }
                }
            }
        }
    }
}
