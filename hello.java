import java.util.HashMap;
import java.util.Scanner;

class Account {
    int id;
    String customerName;
    double balance;

    Account(int id, String customerName, double balance) {
        this.id = id;
        this.customerName = customerName;
        this.balance = balance;
    }
}

public class BankConsoleApp {

    static HashMap<Integer, Account> accounts = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    static int nextAccountId = 1001;

    public static void createAccount() {
        System.out.print("Enter customer name: ");
        String customerName = scanner.nextLine();

        Account account = new Account(
                nextAccountId,
                customerName,
                0
        );

        accounts.put(nextAccountId, account);

        System.out.println("Account created successfully!");
        System.out.println("Account ID: " + nextAccountId);

        nextAccountId++;
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== SecureBank =====");
            System.out.println("1. Create Account");
            System.out.println("2. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    createAccount();
                    break;

                case 2:
                    System.out.println("Thank you!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid operation!");
            }
        }
    }
}