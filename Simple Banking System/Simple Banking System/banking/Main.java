package banking;

import java.util.*;

class Account {
    public static int logIn(CreditCard accountHolder) {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nYou have successfully logged in!\n");
        while (true) {
            System.out.println("\n1. Balance\n" +
                    "2. Log out\n" +
                    "0. Exit\n");
            int op;
            op = sc.nextInt();
            switch (op) {
                case 1:
                    System.out.println("\nBalance: " + accountHolder.getBalance());
                    break;
                case 2:
                    System.out.println("\nYou have successfully logged out!\n");
                    return 2;
                case 0:
                    return 0;
            }
        }
    }
}

class CreditCard {
    private String cardNumber;
    private String cardPin;
    private int balance;

    public CreditCard() {
        setCardNumber(genNum());
        setCardPin(getFourDigitNum());
        setBalance(0);
    }

    public CreditCard(String temp, String pin) {
        setCardNumber(temp);
        setCardPin(pin);
    }

    // Generate a random 4 digit number
    private String genNum() {
        StringBuilder sb  = new StringBuilder();
        sb.append(bankIdentificationNumber()).append(accountIdentificationNumber());
        sb.append(luhnAlgorithm(sb.toString()));
        return sb.toString();
    }

    private String getFourDigitNum() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        sb.append(rand.nextInt(10)).append(rand.nextInt(10));
        sb.append(rand.nextInt(10)).append(rand.nextInt(10));
        return sb.toString();
    }

    private int luhnAlgorithm(String ban) {
        char[] arr = ban.toCharArray();
        int sum = 0;
        int checkSum = 0;
        // Multiply odd places by two indexing from 1 = Even
        // and check if sum is > 9
        for (int i = 0; i < arr.length; i++) {
            if (i%2 == 0) {
                int res = Integer.parseInt(String.valueOf(arr[i])) * 2;
                if (res > 9) {
                    arr[i] = String.valueOf(res -9).charAt(0);
                } else {
                    arr[i] = String.valueOf(res).charAt(0);
                }
            }
        }
        // Add all the numbers
        for (char c : arr) {
            sum += Integer.parseInt(String.valueOf(c));
        }
        while ((sum+checkSum)%10 != 0) {
            checkSum++;
        }
        return checkSum;
    }

    boolean isValid() {
        return Integer.parseInt(String.valueOf(cardNumber.charAt(15))) == luhnAlgorithm(cardNumber.substring(0, 15));
    }

    private String bankIdentificationNumber() {
        // Generates a 6 digit number
        return "400000";
    }

    private String accountIdentificationNumber() {
        // Generates a 9 digit Number
        Random rand = new Random();
        StringBuilder sb  = new StringBuilder();
        sb.append(getFourDigitNum()).append(getFourDigitNum()).append(rand.nextInt(10));
        return sb.toString();
    }

    // Print
    public void printDetails() {
        System.out.println("\nYour card number:");
        System.out.println(this.cardNumber);
        System.out.println("Your card PIN:");
        System.out.println(this.cardPin + "\n");
    }

    // Getters  and Setters
    public String getCardNumber() {
        return cardNumber;
    }

    private void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardPin() {
        return cardPin;
    }

    private void setCardPin(String cardPin) {
        this.cardPin = cardPin;
    }

    public int getBalance() {
        return balance;
    }

    private void setBalance(int balance) {
        this.balance = balance;
    }
}


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, CreditCard> accounts = new HashMap<>();
        while (true) {
            System.out.println("1. Create an account\n" +
                                "2. Log into account\n" +
                                "0. Exit\n");
            int op;
            op = sc.nextInt();
            switch (op) {
                case 1:
                    CreditCard cc = new CreditCard();
                    cc.printDetails();
                    accounts.put(cc.getCardNumber(), cc);
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    String tempCC;
                    tempCC = sc.next();
                    System.out.println("Enter your PIN:");
                    String pin;
                    pin = sc.next();
                    if (tempCC.length() == 16) {
                        CreditCard temp = new CreditCard(tempCC, pin);
                        if (temp.isValid()) {
                            try {
                                String getPin = accounts.get(tempCC).getCardPin();
                                if (pin.equals(getPin)) {
                                    int returnDate = Account.logIn(accounts.get(tempCC));
                                    if (returnDate == 0) {
                                        return;
                                    }
                                } else {
                                    System.out.println("\nWrong card number or PIN!\n");
                                }
                            } catch (Exception e) {
                                System.out.println("\nWrong card number or PIN!\n");
                            }
                        } else {
                            System.out.println("\nWrong card number or PIN!\n");
                        }
                    } else {
                        System.out.println("\nWrong card number or PIN!\n");
                    }
                    break;
                case 0: return;
            }
        }
    }
}
