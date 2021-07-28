package machine;
import java.util.Scanner;

public class CoffeeMachine {
    int milk;
    int water;
    int coffeeBeans;
    int cups;
    int money;
    CoffeeMachine() {
        this.water = 400;
        this.milk = 540;
        this.coffeeBeans = 120;
        this.cups = 9;
        this.money = 550;
    }
    
    public String toString() {
        return "\nThe coffee machine has:\n" +
                this.water + " ml of water\n" +
                this.milk + " ml of milk\n" +
                this.coffeeBeans + " g of coffee beans\n" +
                this.cups + " disposable cups\n" + 
                "$" + this.money + " of money\n";
    }
    
    boolean checkResources(int op) {
        if (op ==1 ) {
            // For the espresso, the coffee machine needs 250 ml of water and 16 g of coffee beans. It costs $4.
            if (this.water >= 250 && this.coffeeBeans >= 16) {
                return true;
            } else {
                if (this.water < 250) {
                    System.out.println("Sorry, not enough water!\n");
                } else {
                    System.out.println("Sorry, not enough coffee beans!\n");    
                }
                return false;
            }
        } else if (op == 2) {
            // For the latte, the coffee machine needs 350 ml of water, 75 ml of milk, and 20 g of coffee beans. It costs $7.
            if (this.water >= 350 && this.milk >= 75 && this.coffeeBeans >= 20) {
                return true;
            } else {
                if (this.water < 250) {
                    System.out.println("Sorry, not enough water!\n");
                } else if (this.milk < 75){
                    System.out.println("Sorry, not enough milk!\n");    
                } else {
                    System.out.println("Sorry, not enough coffee beans!\n");   
                }
                return false;
            }
        } else {
            // For the cappuccino, the coffee machine needs 200 ml of water, 100 ml of milk, and 12 g of coffee beans. It costs $6.
            if (this.water >= 200 && this.milk >= 100 && this.coffeeBeans >= 12) {
                return true;
            } else {
                if (this.water < 250) {
                    System.out.println("Sorry, not enough water!\n");
                } else if (this.milk < 75){
                    System.out.println("Sorry, not enough milk!\n");    
                } else {
                    System.out.println("Sorry, not enough coffee beans!\n");   
                }
                return false;
            }
        }
    }
    
    void buy(int op) {
        if (checkResources(op)) {
            if (op ==1 ) {
                // For the espresso, the coffee machine needs 250 ml of water and 16 g of coffee beans. It costs $4.
                this.water -= 250;
                this.coffeeBeans -= 16;
                this.money += 4;
                this.cups -= 1;
            } else if (op == 2) {
                // For the latte, the coffee machine needs 350 ml of water, 75 ml of milk, and 20 g of coffee beans. It costs $7.
                this.water -= 350;
                this.milk -= 75;
                this.coffeeBeans -= 20;
                this.money += 7;
                this.cups -= 1;
            } else {
                // For the cappuccino, the coffee machine needs 200 ml of water, 100 ml of milk, and 12 g of coffee beans. It costs $6.
                this.water -= 200;
                this.milk -= 100;
                this.coffeeBeans -= 12;
                this.money += 6;
                this.cups -= 1;
            }
        }
        System.out.println("I have enough resources, making you a coffee!\n");
    }
    
    void fill() {
        System.out.println("Write how many ml of water you want to add: ");
        this.water += new Scanner(System.in).nextInt();
        System.out.println("Write how many ml of milk you want to add: ");
        this.milk += new Scanner(System.in).nextInt();
        System.out.println("Write how many grams of coffee beans you want to add: ");
        this.coffeeBeans += new Scanner(System.in).nextInt();
        System.out.println("Write how many disposable cups of coffee you want to add: ");
        this.cups += new Scanner(System.in).nextInt();
        System.out.println();
    }
    
    public static void main(String[] args) {
        CoffeeMachine cm = new CoffeeMachine();
		Scanner sc = new Scanner(System.in);
		String opt;
        String op;
		while (true) {
            System.out.println("Write action (buy, fill, take, remaining, exit): ");
		    opt = sc.next();
            switch (opt) {
                case "buy" : 
                    System.out.println("\nWhat do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino: ");
                    op = sc.next();
                    if (op.matches("back")) {
                        break;   
                    } else {
                        cm.buy(Integer.parseInt(op));    
                    }
                    break;
                case "fill" :
                    cm.fill();
                    break;
                case "take" :
                    System.out.println("\nI gave you $" + cm.money + "\n");
                    cm.money -= cm.money;
                    break;
                case "remaining":
                    System.out.println(cm);
                    break;
                case "exit":
                    return;
            }
        }
	
    }
}
