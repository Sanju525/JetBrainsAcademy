package tictactoe;

import java.util.Scanner;
class TicTac{
    char[][] data = new char[3][5];
    boolean X = false;
    boolean O = false;
    boolean filled = false;
    TicTac() {
        this.data[0][0] = '|';
        this.data[1][0] = '|';
        this.data[2][0] = '|';
        this.data[0][4] = '|';
        this.data[1][4] = '|';
        this.data[2][4] = '|';
        this.data[0][1] = ' ';
        this.data[0][2] = ' ';
        this.data[0][3] = ' ';
        this.data[1][1] = ' ';
        this.data[1][2] = ' ';
        this.data[1][3] = ' ';
        this.data[2][1] = ' ';
        this.data[2][2] = ' ';
        this.data[2][3] = ' ';
    }
    boolean insert(int r, int c, char data) {
        if (this.data[r][c] != 'X' && this.data[r][c] != 'O'){
            this.data[r][c] = data;
            return true;
        }
        return false;
    }
    void print() {
        System.out.println("---------");
        for (int i=0;i<3;i++){
            for (int j=0;j<5;j++){
                System.out.print(this.data[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("---------");
    }
    void checkRow1Col1() {
        char atZero = this.data[0][1];
        if (atZero == this.data[0][2] && atZero == this.data[0][3]){
            if (atZero =='X') {
                this.X = true;
            }
            if (atZero == 'O') {
                this.O = true;
            }
            return;
        }
        if (atZero == this.data[1][1] && atZero == this.data[2][1]) {
            if (atZero =='X') {
                this.X = true;
            }
            if (atZero == 'O') {
                this.O = true;
            }
        }
    }
    void checkRow2Col2() {
        char atZero = this.data[1][2];
        if (atZero == this.data[1][1] && atZero == this.data[1][3]){
            if (atZero =='X') {
                this.X = true;
            }
            if (atZero == 'O') {
                this.O = true;
            }
            return;
        }
        if (atZero == this.data[0][2] && atZero == this.data[2][2]) {
            if (atZero =='X') {
                this.X = true;
            }
            if (atZero == 'O') {
                this.O = true;
            }
        }
    }
    void checkRow3Col3() {
        char atZero = this.data[2][3];
        if (atZero == this.data[2][1] && atZero == this.data[2][2]){
            if (atZero =='X') {
                this.X = true;
            }
            if (atZero == 'O') {
                this.O = true;
            }
            return;
        }
        if (atZero == this.data[0][3] && atZero == this.data[1][3]) {
            if (atZero =='X') {
                this.X = true;
            }
            if (atZero == 'O') {
                this.O = true;
            }
        }
    }
    void checkDiagonals() {
        char comChar = this.data[1][2];
        if (comChar == this.data[0][1] && comChar == this.data[2][3]){
            if (comChar =='X') {
                this.X = true;
            }
            if (comChar == 'O') {
                this.O = true;
            }
            return;
        }
        if (comChar == this.data[0][3] && comChar == this.data[2][1]) {
            if (comChar =='X') {
                this.X = true;
            }
            if (comChar == 'O') {
                this.O = true;
            }
        }
    }
    void checkWin() {
        checkRow1Col1();
        checkRow2Col2();
        checkRow3Col3();
        checkDiagonals();
    }
}
public class Main {
    public static void main(String[] args) {
        // write your code here
        Scanner sc = new Scanner(System.in);
        TicTac tt = new TicTac();
        tt.print();
        int turn = 0;
        while (true) {
            System.out.print("Enter the coordinates: ");
            int cordInput1, cordInput2;
            String input2 = sc.nextLine();
            try {
                cordInput1 = Integer.parseInt(String.valueOf(input2.charAt(0)));
                cordInput2 = Integer.parseInt(String.valueOf(input2.charAt(2)));
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (cordInput1 > 0 && cordInput1 < 4) {
                if (cordInput2 > 0 && cordInput2 < 4) {
                    boolean inserted;
                    if (turn % 2 == 0){
                        inserted = tt.insert(cordInput1-1, cordInput2, 'X');

                    } else {
                        inserted = tt.insert(cordInput1-1, cordInput2, 'O');
                    }
                    if (!inserted) {
                        System.out.println("This cell is occupied! Choose another one!");
                        continue;
                    } else {
                        tt.print();
                        turn++;
                        if (turn == 9) {
                            tt.filled = true;
                        }
                        tt.checkWin();
                        if (tt.X) {
                            System.out.println("X wins");
                            break;
                        } else if(tt.O) {
                            System.out.println("O wins");
                            break;
                        } else {
                            if (tt.filled) {
                                System.out.println("Draw");
                                break;
                            }
                        }
                    }
                } else {
                    System.out.println("Coordinates should be from 1 to 3!");
                    continue;
                }
            } else {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }
        }
    }
}
