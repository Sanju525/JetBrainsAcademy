package calculator;

import java.util.Arrays;
import java.util.Scanner;

public class AddSub {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        while (true) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                continue;
            }

            if (line.matches("\\s*/exit\\s*")) {
                System.out.println("Bye!");
                break;
            } else if (line.matches("\\s*/help\\s*")) {
                System.out.println("The program calculates the sum of numbers with addition and subtraction");
            } else if (line.matches("[\\d[+\\s-]*]*\\d")) {
                line = line.replaceAll("\\s+", " ").replaceAll("\\++\\s*", "+").replaceAll("-(--)*\\s*", "-")
                        .replaceAll("(--)+\\s*", "+");
                int sum = Arrays.stream(line.split(" ")).mapToInt(Integer::parseInt).sum();
                System.out.println(sum);
            } else if (line.startsWith("/")) {
                System.out.println("Unknown command");
            } else {
                System.out.println("Invalid expression");
            }
        }
        scanner.close();
    }
}
