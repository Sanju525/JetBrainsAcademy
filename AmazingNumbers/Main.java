import java.util.Scanner;

class AmazingNumbers {
    String[] availableProps = {"BUZZ", "DUCK", "PALINDROMIC", "GAPFUL", "SPY", "SQUARE", "SUNNY", "JUMPING", "EVEN", "ODD", "HAPPY","SAD",
            "-BUZZ", "-DUCK", "-PALINDROMIC", "-GAPFUL", "-SPY", "-SQUARE", "-SUNNY", "-JUMPING", "-EVEN", "-ODD", "-HAPPY","-SAD"};

    boolean propertySwitch(long num, String str) {
        if ("BUZZ".equals(str.toUpperCase())) {
            return isBuzz(num);
        } else if ("DUCK".equals(str.toUpperCase())) {
            return isDuck(num);
        } else if ("PALINDROMIC".equals(str.toUpperCase())) {
            return isPalindrome(num);
        } else if ("GAPFUL".equals(str.toUpperCase())) {
            return isGapFul(num);
        } else if ("SPY".equals(str.toUpperCase())) {
            return isSpy(num);
        } else if ("EVEN".equals(str.toUpperCase())) {
            return isEven(num);
        } else if ("ODD".equals(str.toUpperCase())) {
            return isOdd(num);
        } else if ("SUNNY".equals(str.toUpperCase())) {
            return isSunny(num);
        } else if ("SQUARE".equals(str.toUpperCase())) {
            return isSquare(num);
        } else if ("JUMPING".equals(str.toUpperCase())) { // Jumping
            return isJumping(num);
        }
        else if ("HAPPY".equals(str.toUpperCase()) || "-SAD".equals(str.toUpperCase())) {
            return isHappy(num);
        } else if ("SAD".equals(str.toUpperCase()) || "-HAPPY".equals(str.toUpperCase())) {
            return !isHappy(num);
        }
        else if ("-BUZZ".equals(str.toUpperCase())) {
            return !isBuzz(num);
        } else if ("-DUCK".equals(str.toUpperCase())) {
            return !isDuck(num);
        } else if ("-PALINDROMIC".equals(str.toUpperCase())) {
            return !isPalindrome(num);
        } else if ("-GAPFUL".equals(str.toUpperCase())) {
            return !isGapFul(num);
        } else if ("-SPY".equals(str.toUpperCase())) {
            return !isSpy(num);
        } else if ("-EVEN".equals(str.toUpperCase())) {
            return !isEven(num);
        } else if ("-ODD".equals(str.toUpperCase())) {
            return !isOdd(num);
        } else if ("-SUNNY".equals(str.toUpperCase())) {
            return !isSunny(num);
        } else if ("-SQUARE".equals(str.toUpperCase())) {
            return !isSquare(num);
        } else { // -Jumping
            return !isJumping(num);
        }
    }


    void printProps(long i) {
        System.out.println("\nProperties of " + i);
        System.out.println("        even: " + isEven(i) + "\n" +
                "         odd: " + isOdd(i) + "\n" +
                "        buzz: " + isBuzz(i) + "\n" +
                "        duck: " + isDuck(i) + "\n" +
                " palindromic: " + isPalindrome(i) + "\n" +
                "      gapful: " + isGapFul(i) + "\n" +
                "         spy: " + isSpy(i) + "\n" +
                "      square: " + isSquare(i) + "\n" +
                "       sunny: " + isSunny(i) + "\n" +
                "     jumping: " + isJumping(i) + "\n" +
                "       happy: " + isHappy(i) + "\n" +
                "         sad: " + !isHappy(i) + "\n");

    }
    void printProps(long num, long range) {
        long i = num;
        while (i < num + range) {
            StringBuilder strprops = new StringBuilder();
            if (isEven(i)) {
                strprops.append("even").append(", ");
            }
            if (isOdd(i)) {
                strprops.append("odd").append(", ");
            }
            if (isBuzz(i)) {
                strprops.append("buzz").append(", ");
            }
            if (isDuck(i)) {
                strprops.append("duck").append(", ");
            }
            if (isPalindrome(i)) {
                strprops.append("palindromic").append(", ");
            }
            if (isGapFul(i)) {
                strprops.append("gapful").append(", ");
            }
            if (isSpy(i)){
                strprops.append("spy").append(", ");
            }
            if (isSquare(i)) {
                strprops.append("square").append(", ");
            }
            if (isSunny(i)) {
                strprops.append("sunny").append(", ");
            }
            if (isJumping(i)) {
                strprops.append("jumping").append(", ");
            }
            if (isHappy(i)) {
                strprops.append("happy").append(", ");
            }
            if (!isHappy(i)) {
                strprops.append("sad").append(", ");
            }
            System.out.print("\n             " + i + " is " + strprops.substring(0, strprops.length() - 2));
            i++;
        }
    }

    void printProps(long num, long range, String[] args) {
        boolean checkProps;
        long checkRange = 0;
        long temp = num;
        boolean exclusive = false;
        String exStr1 = null;
        String exStr2 = null;
        for (int i = 0; i < args.length - 1; i++) {
            for (int j = i + 1; j < args.length; j++) {
                if (checkExclusive(args[i], args[j])) {
                    exclusive = true;
                    exStr1 = args[i];
                    exStr2 = args[j];
                    break;
                }
            }
            if (exclusive) {
                break;
            }
        }
        if (!exclusive) {
            while (checkRange < range) {
                checkProps = true;
                for (String s : args) {
                    if (!propertySwitch(temp, s)) {
                        checkProps = false;
                    }
                }
                if (checkProps) {
                    checkRange++;
                    printProps(temp, 1);
                }
                temp++;
            }
        } else {
            error4(exStr1, exStr2);
        }
    }

    static void suppportedRequests(){
        System.out.println("\nSupported requests:\n" +
                "- enter a natural number to know its properties;\n" +
                "- enter two natural numbers to obtain the properties of the list:\n" +
                "  * the first parameter represents a starting number;\n" +
                "  * the second parameter shows how many consecutive numbers are to be printed;\n" +
                "- two natural numbers and properties to search for;\n" +
                "- a property preceded by minus must not be present in numbers;\n" +
                "- separate the parameters with one space;\n" +
                "- enter 0 to exit.\n");
    }
    boolean isNatural(long i){
        return i > 0;
    }
    boolean inAvailableProperties(String s) {
        for (String c : this.availableProps) {
            if (c.equals(s.toUpperCase())) {
                return true;
            }
        }
        return false;
    }
    boolean isEven(long i){
        return i % 2 == 0;
    }
    boolean isOdd(long i){
        return i % 2 != 0;
    }
    boolean isBuzz(long i){
        long lastDigit = i % 10;
        if (i > 0) {
            return i % 7 == 0 || lastDigit == 7;
        } else {
            return false;
        }
    }
    boolean isDuck(long i){
        long temp=i;
        while(temp>0){
            long rem=temp%10;
            temp/=10;
            if(rem==0){
                return true;
            }
        }
        return false;
    }
    boolean isPalindrome(long i) {
        long temp;
        temp = i;
        long rev = 0;
        while (temp > 0){
            long rem;
            rem = temp % 10;
            rev = rev * 10 + rem;
            temp /= 10;
        }
        return rev == i;
    }
    boolean isGapFul(long i){
        StringBuilder str = new StringBuilder(String.valueOf(i));
        if (str.length() >= 3) {
            String s = str.charAt(0) + String.valueOf(str.charAt(str.length() - 1));
            int mod = Integer.parseInt(s);
            return i % mod == 0;
        } else {
            return false;
        }
    }
    boolean isSpy(long i) {
        long sum = 0;
        long mul = 1;
        long temp = i;
        while (temp > 0) {
            long rem = temp % 10;
            sum += rem;
            mul *= rem;
            temp /= 10;
        }
        return sum == mul;
    }
    boolean isSquare(long i) {
        double sqrt = Math.sqrt(i);
        int digit;
        double decimal;
        digit = (int) sqrt;
        decimal = sqrt - digit;
        return decimal == 0;
    }
    boolean isSunny(long i) {
        return isSquare(i+1);
    }
    boolean isJumping(long i) {
        long diff = i % 10;
        long temp = i / 10;
        while (temp > 0){
            long rem = temp % 10;
            if (Math.abs(rem - diff) != 1){
                return false;
            }
            diff = rem;
            temp /= 10;
        }
        return true;
    }
    boolean isHappy(long i) {
        long temp = i;
        long sum;
        while (temp>0) {
            sum=0;
            while (temp > 0) {
                long rem = temp % 10;
                long sqr = rem*rem;
                sum+=sqr;
                temp/=10;
            }
            temp = sum;
            if (sum == 1){
                return true;
            }
            if (sum == i || sum == 4) {
                return false;
            }
        }
        return true;
    }

    boolean isZero(long i) {
        return i == 0;
    }
    boolean checkExclusive(String str1, String str2) {
        boolean type1 = str1.toUpperCase().equals("EVEN") && str2.toUpperCase().equals("ODD") || str2.toUpperCase().equals("EVEN") && str1.toUpperCase().equals("ODD");
        boolean type2 = str1.toUpperCase().equals("SUNNY") && str2.toUpperCase().equals("SQUARE") || str2.toUpperCase().equals("SUNNY") && str1.toUpperCase().equals("SQUARE");;
        boolean type3 = str1.toUpperCase().equals("DUCK") && str2.toUpperCase().equals("SPY") || str2.toUpperCase().equals("DUCK") && str1.toUpperCase().equals("SPY");
        boolean type4 = str1.toUpperCase().equals("HAPPY") && str2.toUpperCase().equals("SAD") || str2.toUpperCase().equals("HAPPY") && str1.toUpperCase().equals("SAD");

        boolean type01 = str1.toUpperCase().equals("-EVEN") && str2.toUpperCase().equals("-ODD") || str2.toUpperCase().equals("-EVEN") && str1.toUpperCase().equals("-ODD");
//        boolean type02 = str1.toUpperCase().equals("-SUNNY") && str2.toUpperCase().equals("-SQUARE") || str2.toUpperCase().equals("-SUNNY") && str1.toUpperCase().equals("-SQUARE");;
//        boolean type03 = str1.toUpperCase().equals("-DUCK") && str2.toUpperCase().equals("-SPY") || str2.toUpperCase().equals("-DUCK") && str1.toUpperCase().equals("-SPY");
        boolean type04 = str1.toUpperCase().equals("-HAPPY") && str2.toUpperCase().equals("-SAD") || str2.toUpperCase().equals("-HAPPY") && str1.toUpperCase().equals("-SAD");

        boolean typeA1 = str1.toUpperCase().equals("EVEN") && str2.toUpperCase().equals("-EVEN") || str2.toUpperCase().equals("EVEN") && str1.toUpperCase().equals("-EVEN");
        boolean typeA2 = str1.toUpperCase().equals("ODD") && str2.toUpperCase().equals("-ODD") || str2.toUpperCase().equals("ODD") && str1.toUpperCase().equals("-ODD");
        boolean typeA3 = str1.toUpperCase().equals("SPY") && str2.toUpperCase().equals("-SPY") || str2.toUpperCase().equals("SPY") && str1.toUpperCase().equals("-SPY");
        boolean typeA4 = str1.toUpperCase().equals("SUNNY") && str2.toUpperCase().equals("-SUNNY") || str2.toUpperCase().equals("SUNNY") && str1.toUpperCase().equals("-SUNNY");
        boolean typeA5 = str1.toUpperCase().equals("DUCK") && str2.toUpperCase().equals("-DUCK") || str2.toUpperCase().equals("DUCK") && str1.toUpperCase().equals("-DUCK");
        boolean typeA6 = str1.toUpperCase().equals("PALINDROMIC") && str2.toUpperCase().equals("-PALINDROMIC") || str2.toUpperCase().equals("PALINDROMIC") && str1.toUpperCase().equals("-PALINDROMIC");
        boolean typeA7 = str1.toUpperCase().equals("SQUARE") && str2.toUpperCase().equals("-SQUARE") || str2.toUpperCase().equals("SQUARE") && str1.toUpperCase().equals("-SQUARE");
        boolean typeA8 = str1.toUpperCase().equals("JUMPING") && str2.toUpperCase().equals("-JUMPING") || str2.toUpperCase().equals("JUMPING") && str1.toUpperCase().equals("-JUMPING");
        boolean typeA9 = str1.toUpperCase().equals("GAPFUL") && str2.toUpperCase().equals("-GAPFUL") || str2.toUpperCase().equals("GAPFUL") && str1.toUpperCase().equals("-GAPFUL");
        boolean typeA10 = str1.toUpperCase().equals("BUZZ") && str2.toUpperCase().equals("-BUZZ") || str2.toUpperCase().equals("BUZZ") && str1.toUpperCase().equals("-BUZZ");

//      type01 || type02 || type03 ||
        if (type1 || type2 || type01 || type3 || type4 || type04 || typeA1 || typeA2 || typeA3 || typeA4 || typeA5 || typeA6 || typeA7 || typeA8 || typeA9 || typeA10) {
            return true;
        }
        return false;
    }
    void error1() {
        System.out.println("\nThe first parameter should be a natural number or zero.");
    }
    void error2() {
        System.out.println("\nThe second parameter should be a natural number or zero.\n");
    }
    void error3(String[] args) {
        StringBuilder sb = new StringBuilder();
        for (String s : args) {
            sb.append(s.toUpperCase()).append(", ");
        }
        if (args.length == 1 ){
            System.out.print("\nThe property ["+ sb.substring(0, sb.length()-2) +"] is wrong.\n" +
                    "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");
        } else {
            System.out.print("\nThe properties ["+ sb.substring(0, sb.length()-2) +"] are wrong.\n" +
                    "Available properties: [EVEN, ODD, BUZZ, DUCK, PALINDROMIC, GAPFUL, SPY, SQUARE, SUNNY, JUMPING, HAPPY, SAD]");

        }
            }
    void error4(String arg1, String arg2) {
        System.out.print("\nThe request contains mutually exclusive properties: ["+arg1.toUpperCase()+", "+arg2.toUpperCase() + "]\n" +
                "There are no numbers with these properties.");
    }
}
public class Main {

    public static void main(String[] args) {
//        write your code here
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Amazing Numbers!\n");
        AmazingNumbers.suppportedRequests();
        while (true) {
            AmazingNumbers an = new AmazingNumbers();
            System.out.print("Enter a request: ");
            String[] string;
            string = sc.nextLine().split(" ");
            if (string.length == 1) {
                try {
                    long temp = Long.parseLong(string[0]);
                    if (an.isNatural(Long.parseLong(string[0]))) {
                        an.printProps(Long.parseLong(string[0]));
                    } else if (an.isZero(Long.parseLong(string[0]))) {
                        System.out.println("\nGood Bye!");
                        System.exit(0);
                    } else {
                        an.error1();
                        System.out.println();
                    }
                 } catch (Exception e) {
                    an.error1();
                    System.out.println();
                }
            } else if (string.length == 2) {
                if (an.isNatural(Long.parseLong(string[0])) && an.isNatural(Long.parseLong(string[1]))){
                    an.printProps(Long.parseLong(string[0]), Long.parseLong(string[1]));
                    System.out.println("\n");
                } else if (!an.isNatural(Long.parseLong(string[0]))) {
                    an.error1();
                } else {
                    an.error2();
                }
            } else { // have property arguments.
                if (an.isNatural(Long.parseLong(string[0])) && an.isNatural(Long.parseLong(string[1]))){
                    String[] stringProps = new String[string.length-2];
                    int strpropslen=0;
                    int i=0;
                    StringBuilder notProps = new StringBuilder();
                    for (int l = 2; l < string.length; l++) {
                        if (an.inAvailableProperties(string[l].toUpperCase())){
                            stringProps[i] = string[l];
                            strpropslen++;
                            i++;
                        }
                        else {
                            notProps.append(string[l]).append(" ");
                        }
                    }
                    if (strpropslen == string.length-2){
                        an.printProps(Long.parseLong(string[0]), Long.parseLong(string[1]), stringProps);
                    } else {
                        an.error3(notProps.toString().split(" "));
                    }
                    System.out.println("\n");
                } else if (!an.isNatural(Long.parseLong(string[0]))) {
                    an.error1();
                    System.out.println();
                } else {
                    an.error2();
                }
            }
        }
    }
}
