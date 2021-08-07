package calculator;

import java.math.BigInteger;
import java.util.*;
import java.util.regex.Pattern;

public class Main {
    static int count(String[] str, String var) {
        int count = 0;
        for (String s: str) {
            if (s.equals(var)) {
                count+=1;
            }
        }
        return count;
    }
    static int priority(String s) {
        switch (s) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
        }
        return -1;
    }
    static String infixToPostfix (String[] str) {
        StringBuilder result = new StringBuilder();
        Deque<String> stack = new ArrayDeque<>();
        for (String s: str)
        {
            // If the scanned character is an
            // operand, add it to output.
            if (s.matches("\\d+|[a-zA-Z]+")) {
                result.append(s).append(" ");
            }
            // If the scanned character is an '(',
            // push it to the stack.
            else if (s.equals("(")) {
                stack.offerLast(s);
            }
            //  If the scanned character is an ')',
            // pop and output from the stack
            // until an '(' is encountered.
            else if (s.equals(")"))
            {
                while (!stack.isEmpty() && !stack.peekLast().equals("(")) {
                    result.append(stack.pollLast()).append(" ");
                }
                stack.pollLast();
            }
            else // an operator is encountered
            {
                while (!stack.isEmpty() && priority(s) <= priority(stack.peekLast())){
                    result.append(stack.pollLast()).append(" ");
                }
                stack.offerLast(s);
            }
        }

        // pop all the operators from the stack
        while (!stack.isEmpty()){
            if(stack.peekLast().equals("("))
                return "Invalid Expression";
            result.append(stack.pollLast()).append(" ");
        }
        return result.substring(0, result.length()-1).toString();
    }
    static String evaluatePostfix(String[] str, Map<String, BigInteger> variableMap) {
        String result;
//        System.out.println(variableMap);
        Deque<String> stack = new LinkedList<>();
        if (str.length >= 3) {
            for (String s : str) {
                if (s.matches("\\d+|[a-zA-Z]+")) {
                    stack.offerLast(s);
//                    System.out.println("stack = " + stack);
                } else {
                    String val1 = stack.pollLast();
                    String val2 = stack.pollLast();
                    assert val1 != null;
                    if (val1.matches("[a-zA-Z]+")) {
                        if (variableMap.containsKey(val1)) {
                            val1 = String.valueOf(variableMap.get(val1));
                        } else {
                            return "Unknown variable";
                        }
                    }
                    assert val2 != null;
                    if (val2.matches("[a-zA-Z]+")) {
                        if (variableMap.containsKey(val2)) {
                            val2 = String.valueOf(variableMap.get(val2));
                        } else {
                            return "Unknown variable";
                        }
                    }
                    switch (s) {
                        case "+":
                            assert val2 != null;
                            assert val1 != null;
                            stack.offerLast(String.valueOf(new BigInteger(val2).add(new BigInteger(val1))));
                            break;
                        case "-":
                            assert val2 != null;
                            assert val1 != null;
                            stack.offerLast(String.valueOf(new BigInteger(val2).subtract(new BigInteger(val1))));
                            break;
                        case "*":
                            assert val2 != null;
                            assert val1 != null;
                            stack.offerLast(String.valueOf(new BigInteger(val2).multiply(new BigInteger(val1))));
                            break;
                        case "/":
                            assert val2 != null;
                            assert val1 != null;
                            stack.offerLast(String.valueOf(new BigInteger(val2).divide(new BigInteger(val1))));
                            break;
                    }
                }
            }
        } else {
            for (String s: str) {
                String val1 = "";
                if (s.matches("[a-zA-Z0-9]+")) {
                    stack.offerLast(s);
                } else {
                    if (variableMap.containsKey(stack.peekLast())) {
                        val1 = String.valueOf(variableMap.get(stack.peekLast()));
                    } else {
                        val1 = stack.pollLast();
                    }
                    switch (s) {
                        case "+":
                            assert val1 != null;
                            stack.offerLast(String.valueOf(Integer.parseInt(val1)));
                            break;
                        case "-":
                            assert val1 != null;
                            stack.offerLast(String.valueOf(-Integer.parseInt(val1)));
                            break;
                        case "*":
                        case "/":
                            return "Invalid Expression";
                    }
                }
            }
        }
        if (variableMap.containsKey(stack.peekLast())) {
            result = String.valueOf(variableMap.get(stack.peekLast()));
        } else if (stack.peekLast().matches("[-\\+]?\\d+")){
            result = stack.peekLast();
        } else {
            result = "Unknown variable";
        }
        return result;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // put your code here
        Map<String, BigInteger> variableMap = new HashMap<>();
        Pattern commandPattern = Pattern.compile("^/.*");
        Pattern variablePattern = Pattern.compile("([\\w\\s]+=)+(([-\\+\\s]*[\\d]+)|([-\\+\\s]*[\\w\\s]+))");
//        [a-zA-Z\s]+=[\d\s]+
        Pattern expressionPattern = Pattern.compile("[\\w[+\\)\\(\\*\\/\\s-]*]*([\\w]|[\\s|\\)])");
        while (true) {
            String inp = scanner.nextLine();
            if (inp.isEmpty()) {
                continue;
            }

            // First Check Commands.
            if (commandPattern.matcher(inp).matches()) {
                if (inp.equals("/exit")) {
                    System.out.println("Bye!");
                    return;
                } else if (inp.equals("/help")) {
                    System.out.println("The program calculates the sum of numbers");
                } else {
                    System.out.println("Unknown Command");
                } //  Second Assigning of variables.
            } else if (variablePattern.matcher(inp).matches()) {
                inp = inp.replaceAll(" ", "");
                String arr[] = inp.split("=");
                if (arr.length == 2) {
                    if (arr[0].matches(".*\\d.*")) {
                        System.out.println("Invalid identifier");
                    } else if (!arr[1].matches("\\d+|[a-zA-Z]+|[-\\+]*\\d+")) {
                        System.out.println("Invalid assignment");
                    } else {
                        try {
                            variableMap.put(arr[0], new BigInteger(arr[1]));
                        } catch (Exception e) {
                            if (variableMap.containsKey(arr[1])) {
                                variableMap.put(arr[0], variableMap.get(arr[1]));
                            } else {
                                System.out.println("Unknown Variable");
                            }
                        }
                    }
                } else {
                    System.out.println("Invalid assignment");
                }
                // Third computation
            } else if (expressionPattern.matcher(inp).matches()) {
                inp = inp.replaceAll("\\s*\\++\\s*", " + ").replaceAll("\\s*-(--)*\\s*", "-")
                        .replaceAll("\\s*(--)+\\s*", " + ").replaceAll("-", " - ");
                if (inp.matches(".*\\*{2,}.*|.*\\/{2,}.*")) {
                    System.out.println("Invalid expression");
                } else {
                    inp = inp.replaceAll("\\*", " * ");
                    inp = inp.replaceAll("\\/", " / ");
                    inp = inp.replaceAll("\\(", " ( ");
                    inp = inp.replaceAll("\\)", " ) ");
                    inp = inp.replaceAll("\\s+", " ");
                    String arr[] = inp.split("[ ]+");
                    int sum = 0;
                    boolean uk = false;
//                    System.out.println(inp);
                    if (count(inp.split(" "), "(") == count(inp.split(" "), ")")) {
                        String postFix = infixToPostfix(arr);
                        if (postFix.equals("Invalid Expression")) {
                            System.out.println("Invalid expression");
                        } else {
//                            System.out.println(postFix);
                            String result = evaluatePostfix(postFix.split(" "), variableMap);
                            System.out.println(result);
                        }
                    } else {
                        System.out.println("Invalid expression");
                    }
                }
            } else {
                System.out.println("Invalid expression");
            }
        }

    }
}
