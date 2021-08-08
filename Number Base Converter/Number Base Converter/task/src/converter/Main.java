package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {
    static String getValue(int index) {
        String[] thirtySixEle = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
        return thirtySixEle[index];
    }
    static String integerToAny(BigInteger num, String to) {

        StringBuilder result = new StringBuilder();
        while(!num.equals(BigInteger.ZERO)) {
            int rem = num.remainder(BigInteger.valueOf(Long.parseLong(to))).intValue();
            num = num.divide(BigInteger.valueOf(Long.parseLong(to)));
            result.append(getValue(rem));
        }
        return result.reverse().toString();
    }

    static int getIndex(String str) {
        String[] thirtySixEle = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
        for (int i=0;i<36;i++) {
            if (str.equals(thirtySixEle[i])) {
                return i;
            }
        }
        return -1;
    }
    static BigInteger anyToInteger(String from, String value) {
        BigInteger result = new BigInteger("0");
        int index = 0;
        for (int i = value.length()-1; i >= 0; i--) {
            BigInteger pow = new BigInteger(from).pow(index);
            result = result.add(new BigInteger(String.valueOf(getIndex(String.valueOf(value.charAt(i)).toUpperCase()))).multiply(pow));
            index++;
        }
        return result;
    }


    static double getDecIndex(String str) {
        String[] thirtySixEle = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
        for (int i=0;i<36;i++) {
            if (str.equals(thirtySixEle[i])) {
                return (double) i;
            }
        }
        return -1;
    }
    static BigDecimal anyToDecimal(int from, String str) {
        BigDecimal result = new BigDecimal("0.0");
        String[] arr = str.split("\\.");
        int index = 0;
        for (int i = arr[0].length()-1 ; i >= 0; i--) {
            String s = String.valueOf(arr[0].charAt(i)).toUpperCase();
            BigDecimal pow = BigDecimal.valueOf(from).pow(index);
            result = result.add(new BigDecimal(String.valueOf(getIndex(s))).multiply(pow));
            index++;
        }
        index = 1;
        for (int i=0;i<arr[1].length();i++) {
            String s = String.valueOf(arr[1].charAt(i)).toUpperCase();
            BigDecimal pow = new BigDecimal(from).pow(index);
            BigDecimal res = BigDecimal.valueOf(getDecIndex(s)).divide(pow, 5, RoundingMode.CEILING);
            result = result.add(res);
            index++;
        }
        return result;
    }

    static String getDecValue(int index){
        String[] thirtySixEle = {
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
        };
        return thirtySixEle[index];
    }
    static String decimalToAny(BigDecimal num, String to) {
//        Multiply the number with the base raised to the power of decimals in result:
        BigDecimal result = num.multiply(new BigDecimal(to).pow(5));
        StringBuilder res = new StringBuilder("");
        int dec = 1;
        while (!result.equals(BigDecimal.valueOf(Long.parseLong("0")).setScale(1, RoundingMode.CEILING))) {
            BigDecimal[] qAndR = result.divideAndRemainder(new BigDecimal(to));
            res.append(getDecValue(qAndR[1].intValue()));
            if (dec == 5) {
                res.append(".");
            }
            dec++;
            result = qAndR[0].setScale(1, RoundingMode.CEILING);
        }
        return res.reverse().toString();
    }

    public static void main(String[] args) {
        // write your code here
        Scanner sc = new Scanner(System.in);
        String inp;
        while (true) {
            System.out.print("Enter two numbers in format: {source base} {target base} (To quit type /exit) ");
            inp = sc.nextLine();
            if (inp.matches("/exit")) {
                return;
            } else {
                while (true) {
                    String[] fromTo = inp.split(" ");
                    System.out.printf("Enter number in base %s to convert to base %s (To go back type /back) ", fromTo[0], fromTo[1]);
                    String inp1 = sc.nextLine();
                    if (inp1.matches("/back")) {
                        System.out.println();
                        break;
                    } else {
                        if (inp1.matches(".*\\..*")) {
                            if (fromTo[0].equals("10")) {
                                BigDecimal inpDec = new BigDecimal(inp1);
                                System.out.println("Conversion result: " + decimalToAny(inpDec, fromTo[1]));
                            } else if (fromTo[1].equals("10")) {
                                System.out.println("Conversion result: " + anyToDecimal(Integer.parseInt(fromTo[0]), inp1));
                            } else {
                                System.out.println("Conversion result: " + decimalToAny(anyToDecimal(Integer.parseInt(fromTo[0]), inp1), fromTo[1]));
                            }
                        } else {
                            if (fromTo[0].equals("10")) { // Decimal to Any
                                BigInteger inpInt = new BigInteger(inp1);
                                System.out.println("Conversion result: " + integerToAny(inpInt, fromTo[1]));
                            } else if (fromTo[1].equals("10")){ // Any to Decimal
                                System.out.println("Conversion result: " + anyToInteger(fromTo[0], inp1));
                            } else {
                                System.out.println("Conversion result: " + integerToAny(anyToInteger(fromTo[0], inp1), fromTo[1]));
                            }
                        }
                    }
                }
            }
        }
    }
}
