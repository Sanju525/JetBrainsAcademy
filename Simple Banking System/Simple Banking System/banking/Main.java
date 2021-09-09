package banking;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;

class Account {
//    Select and Update
//    Update Sender and Receiver

    public static void menu(){
        System.out.println("1. Balance\n" +
                "2. Add income\n" +
                "3. Do transfer\n" +
                "4. Close account\n" +
                "5. Log out\n" +
                "0. Exit\n");
    }

    public static void addIncome(DataBase db, String number) {
        Scanner sc = new Scanner(System.in);
        int balance;
        System.out.println("Enter income:");
        balance = sc.nextInt();
        if (balance > 0) {
            db.updateBalance(balance, number);
        }
        System.out.println("Income was added!\n");
    }

    public static void doTransfer(DataBase db, String sender) { // return int
        Scanner sc = new Scanner(System.in);
        String receiver;
        System.out.println("Transfer\n" +
                            "Enter card number:");
        receiver = sc.next();
        CreditCard temp = new CreditCard(receiver);
        if (receiver.length() == 16 && temp.isValid()) {
            if (db.exists(receiver)) {
                int income;
                System.out.println("Enter how much money you want to transfer:");
                income = sc.nextInt();
                if (income < db.getBalance(sender)) {

                    db.updateBalance(-income, sender);
                    db.updateBalance(income, receiver);

                    System.out.println("Success!\n");
                } else {
                    System.out.println("Not enough money!\n");
                }
            } else {
                System.out.println("Such a card does not exist.\n");
            }
        } else {
            System.out.println("Probably you made a mistake in the card number. \n" +
                    "Please try again!\n");
        }

    }

    public static void closeAccount(DataBase db, String number) {
        db.deleteRecord(number);
    }

    public static int logIn(String number, DataBase db) { // Database and Number as parameters
        Scanner sc = new Scanner(System.in);
        System.out.println("\nYou have successfully logged in!\n");
        while (true) {
            menu();
            int op;
            op = sc.nextInt();
            switch (op) {
                case 1:
                    System.out.println("\nBalance: " + db.getBalance(number));
                    break;
                case 2:
                    addIncome(db, number);
                    break;
                case 3:
                    doTransfer(db, number);
                    break;
                case 4:
                    closeAccount(db, number);
                    System.out.println("\nThe account has been closed!\n");
                    return 2;
                case 5:
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


    // Update, Select, Drop
    protected String getPin(String number) {
        String sql = "SELECT pin FROM card WHERE number="+number;
        String res = null;
        try (Connection conn = connect();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            res = rs.getString("pin");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }

    protected boolean exists(String number) {
        String sql = "SELECT * FROM card WHERE number="+number;
        try (Connection connection = connect();
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql)){
            if (rs.next()) {
                System.out.println(rs.getFetchSize());
                return true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }


    protected int getBalance(String number) {
        String sql = "SELECT balance FROM card WHERE number="+number;
        int res = 0;
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)){
            res = rs.getInt("balance");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return res;
    }


    protected void updateBalance(int addBalance, String number) {
        String sql = "UPDATE card SET balance=? WHERE number=?";
        try (Connection connection  =connect();
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
            System.out.println("Send/Received"  +(getBalance(number)+addBalance));
            pstmt.setInt(1, (getBalance(number)+addBalance));
            pstmt.setString(2, number);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Login Required
    protected void deleteRecord(String number) {
        String sql = "DELETE FROM card WHERE number=?";
        try (Connection connection = connect();
        PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, number);
            pstmt.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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

    public CreditCard(String number) {
        this.cardNumber = number;
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

public class Main {
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
                                int state = Account.logIn(cardNumber, db);
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
