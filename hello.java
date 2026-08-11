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

class AccountNotFoundException extends Exception {

    AccountNotFoundException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends Exception {

    InsufficientFundsException(String message) {
        super(message);
    }
}

public class BankConsoleApp {

    static HashMap<Integer, Account> accounts = new HashMap<>();
    static Scanner scanner = new Scanner(System.in);

    static int nextAccountId = 1001;

    // Create Account
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

    // Deposit
    public static void deposit(int id, double amount)
            throws AccountNotFoundException {

        if (!accounts.containsKey(id)) {
            throw new AccountNotFoundException(
                    "Account not found: " + id
            );
        }

        Account account = accounts.get(id);

        account.balance += amount;

        System.out.println("Deposit successful!");
        System.out.println("Current balance: ₹" + account.balance);
    }

    // Withdraw
    public static void withdraw(int id, double amount)
            throws AccountNotFoundException,
                   InsufficientFundsException {

        if (!accounts.containsKey(id)) {
            throw new AccountNotFoundException(
                    "Account not found: " + id
            );
        }

        Account account = accounts.get(id);

        if (amount > account.balance) {
            throw new InsufficientFundsException(
                    "Insufficient funds!"
            );
        }

        account.balance -= amount;

        System.out.println("Withdrawal successful!");
        System.out.println("Current balance: ₹" + account.balance);
    }

    // Balance Inquiry
    public static void balance(int id)
            throws AccountNotFoundException {

        if (!accounts.containsKey(id)) {
            throw new AccountNotFoundException(
                    "Account not found: " + id
            );
        }

        Account account = accounts.get(id);

        System.out.println("Account ID: " + account.id);
        System.out.println("Customer Name: " + account.customerName);
        System.out.println("Balance: ₹" + account.balance);
    }

    // Close Account
    public static void closeAccount(int id)
            throws AccountNotFoundException {

        if (!accounts.containsKey(id)) {
            throw new AccountNotFoundException(
                    "Account not found: " + id
            );
        }

        accounts.remove(id);

        System.out.println("Account closed successfully!");
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== SecureBank =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Balance Inquiry");
            System.out.println("5. Close Account");
            System.out.println("6. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            try {

                switch (choice) {

                    case 1:
                        createAccount();
                        break;

                    case 2:
                        System.out.print("Enter Account ID: ");
                        int depositId = scanner.nextInt();

                        System.out.print("Enter amount: ");
                        double depositAmount = scanner.nextDouble();

                        deposit(depositId, depositAmount);
                        break;

                    case 3:
                        System.out.print("Enter Account ID: ");
                        int withdrawId = scanner.nextInt();

                        System.out.print("Enter amount: ");
                        double withdrawAmount = scanner.nextDouble();

                        withdraw(withdrawId, withdrawAmount);
                        break;

                    case 4:
                        System.out.print("Enter Account ID: ");
                        int balanceId = scanner.nextInt();

                        balance(balanceId);
                        break;

                    case 5:
                        System.out.print("Enter Account ID: ");
                        int closeId = scanner.nextInt();

                        closeAccount(closeId);
                        break;

                    case 6:
                        System.out.println("Thank you for using SecureBank!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid operation!");
                }

            } catch (AccountNotFoundException |
                     InsufficientFundsException e) {

                System.out.println(e.getMessage());
            }
        }
    }
    }
