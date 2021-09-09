package banking;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

// Included Database SQLite

class Account {
//    Select and Update
//    Update Sender and Receiver

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

class DataBase{
    String url;
    DataBase(String path) {
        url = "jdbc:sqlite:" + path;
    }
    protected Connection connect() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(this.url);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return con;
    }

    protected void create() {
        String sql = "CREATE TABLE IF NOT EXISTS card (\n"
                + "	id INTEGER PRIMARY KEY,\n"
                + "	number TEXT NOT NULL UNIQUE,\n"
                + "	pin TEXT NOT NULL,\n"
                + "balance INTEGER DEFAULT 0\n"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    protected int insert(String card_number, String card_pin, int balance) {
        String sql = "INSERT INTO card(number, pin, balance) VALUES(?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, card_number);
            pstmt.setString(2, card_pin);
            pstmt.setString(3, String.valueOf(balance));
            pstmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            return 0;
//            System.out.println(e.getMessage());
        }
    }

    protected Map<String, CreditCard> select(String cardNumber) throws Exception {
        String sql = "SELECT id, number, pin, balance FROM card WHERE number=" + cardNumber;
        Map<String, CreditCard> result = new HashMap<>();
        try (Connection conn = this.connect();
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
            // loop through the result set
            while (rs.next()) {
                CreditCard temp = new CreditCard();
                temp.setId(rs.getInt("id"));
                temp.setCardNumber(rs.getString("number"));
                temp.setCardPin(rs.getString("pin"));
                temp.setBalance(rs.getInt("balance"));
                result.put(temp.getCardNumber(), temp);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        if (result.size() == 0){
            throw new Exception();
        }
        return result;
    }
}

class CreditCard {
    private int id;
    private String cardNumber;
    private String cardPin;
    private int balance;

    public CreditCard() {
        this.cardNumber = genNum();
        this.cardPin = getFourDigitNum();
        this.balance=0;
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

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardPin() {
        return cardPin;
    }

    public void setCardPin(String cardPin) {
        this.cardPin = cardPin;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}

public class MainDB {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        File file = new File(args[1]);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DataBase db = new DataBase(file.getAbsolutePath());
        db.create(); // creates the required table.
        while (true) {
            System.out.println("1. Create an account\n" +
                                "2. Log into account\n" +
                                "0. Exit\n");
            int op;
            op = sc.nextInt();
            switch (op) {
                case 1:
                    CreditCard cc = new CreditCard();
                    int inserted;
                    do {
                        inserted = db.insert(cc.getCardNumber(), cc.getCardPin(), cc.getBalance());
                    } while (inserted != 0);
                    cc.printDetails();
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    String cardNumber;
                    cardNumber = sc.next();
                    System.out.println("Enter your PIN:");
                    String cardPin;
                    cardPin = sc.next();
                    CreditCard temp = new CreditCard();
                    temp.setCardNumber(cardNumber);
                    temp.setCardPin(cardPin);
                    if (cardNumber.length() == 16 && temp.isValid()) {
                        try {
                            Map<String, CreditCard> res = db.select(cardNumber);
                            CreditCard result = res.get(cardNumber);
                            if (result.getCardPin().equals(cardPin)) {
                                int state = Account.logIn(res.get(cardNumber));
                                if (state == 0) {
                                    return;
                                }
                            } else {
                                System.out.println("Wrong card number or PIN!\n");
                            }
                        } catch (Exception e) {
                            System.out.println("Wrong card number or PIN!\n");
                        }
                    } else {
                        System.out.println("Wrong card number or PIN!\n");
                    }
                    break;
                case 0: return;
            }
        }
    }
}
