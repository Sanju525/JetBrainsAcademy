package cinema;

import java.util.Scanner;

class Theatre {
    char[][] seats;
    int rows;
    int sts;
    int totalIncome;
    float percentage=0;
    int currentIncome =0;
    int purchased;
    boolean gTs = false;
    Theatre(int rows, int seats) {
        if (rows * seats > 60) {
            this.gTs = true;
        }
        this.rows = rows;
        this.sts = seats;
        if (rows * seats > 60){
            int temp = rows/2;
            this.totalIncome = temp * seats * 10;
            this.totalIncome += (rows - temp)* seats * 8;
        } else {
            this.totalIncome = rows * seats * 10;
        }
        this.seats = new char[rows][seats];
        for (int i = 0; i < rows; i++){
            for (int j = 0; j < seats; j++) {
                this.seats[i][j] = 'S';
            }
        }
    }
    void statistics(){
        this.percentage = (float) this.purchased / (float)(this.rows * this.sts) * (float) 100;
        String s = String.format("Percentage %.2f", this.percentage);
        System.out.println("\nNumber of purchased tickets: " + this.purchased + "\n" +
                s + "%\n" +
                "Current income: $"+ this.currentIncome +"\n" +
                "Total income: $" + this.totalIncome);
    }
    void print(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.rows; i++){
            sb.append(i+1).append(" ");
            for (int j = 0; j < this.sts; j++) {
                sb.append(seats[i][j]).append(" ");
            }
            sb.append("\n");
        }
        StringBuilder col = new StringBuilder("  ");
        for (int i = 0; i < this.sts; i++) {
            col.append(i+1).append(" ");
        }
        System.out.println("\nCinema:");
        System.out.println(col.substring(0,col.length()-1));
        System.out.println(sb.substring(0, sb.length()-1));
    }
    int seatPrice(int row){
        int sp;
        if (this.gTs) {
            if (row > this.rows / 2) {
                sp = 8;
            } else {
                sp = 10;
            }
        } else {
            sp = 10;
        }
        return sp;
    }
    boolean fillSeat(int row, int seat) {
        try {
            if (this.seats[row-1][seat-1] == 'B') {
                System.out.println("\nThat ticket has already been purchased!");
            } else {
                this.seats[row - 1][seat - 1] = 'B';
                this.purchased++;
                this.currentIncome += seatPrice(row);
                System.out.println("\nTicket price: $" + seatPrice(row));
                return true;
            }
        } catch (Exception e) {
            System.out.println("\nWrong input!");
        }
        return false;
    }
}

public class Cinema {

    public static void main(String[] args) {
        // Write your code here
        Scanner sc = new Scanner(System.in);
        int rows;
        int seats;
        System.out.println("Enter the number of rows:");
        rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        seats = sc.nextInt();
        Theatre tr = new Theatre(rows, seats);

        int opt;

        while (true) {
            System.out.println("\n1. Show the seats\n" +
                    "2. Buy a ticket\n" +
                    "3. Statistics\n" +
                    "0. Exit");
            opt = sc.nextInt();
            switch (opt) {
                case 1:
                    tr.print();
                    break;
                case 2:
                    int row, seat;
                    System.out.println("\nEnter a row number:");
                    row = sc.nextInt();
                    System.out.println("Enter a seat number in that row:");
                    seat = sc.nextInt();
                    while (!tr.fillSeat(row, seat)) {
                        System.out.println("\nEnter a row number:");
                        row = sc.nextInt();
                        System.out.println("Enter a seat number in that row:");
                        seat = sc.nextInt();
                    }
                    break;
                case 3:
                    tr.statistics();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid Input");
                    return;
            }
        }
    }
}
