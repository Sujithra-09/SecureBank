import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Account {
    int id;
    String customerName;
    double balance;
    List<Transaction> transactions;

    Account(int id, String customerName, double balance) {
        this.id = id;
        this.customerName = customerName;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }
}

class Transaction {
    String type;
    int fromId;
    int toId;
    double amount;

    Transaction(String type, int fromId, int toId, double amount) {
        this.type = type;
        this.fromId = fromId;
        this.toId = toId;
        this.amount = amount;
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

    public static void deposit(int id, double amount)
            throws AccountNotFoundException {

        if (!accounts.containsKey(id)) {
            throw new AccountNotFoundException(
                    "Account not found: " + id
            );
        }

        Account account = accounts.get(id);

        account.balance += amount;

        account.transactions.add(
                new Transaction("DEPOSIT", id, id, amount)
        );

        System.out.println("Deposit successful!");
        System.out.println("Current balance: ₹" + account.balance);
    }

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

        account.transactions.add(
                new Transaction("WITHDRAW", id, id, amount)
        );

        System.out.println("Withdrawal successful!");
        System.out.println("Current balance: ₹" + account.balance);
    }

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

    // Transfer money from one account to another
    public static void transfer(int fromId, int toId, double amount)
            throws AccountNotFoundException,
                   InsufficientFundsException {

        if (!accounts.containsKey(fromId)) {
            throw new AccountNotFoundException(
                    "Source account not found: " + fromId
            );
        }

        if (!accounts.containsKey(toId)) {
            throw new AccountNotFoundException(
                    "Target account not found: " + toId
            );
        }

        Account fromAccount = accounts.get(fromId);
        Account toAccount = accounts.get(toId);

        if (amount > fromAccount.balance) {
            throw new InsufficientFundsException(
                    "Insufficient funds in source account!"
            );
        }

        double sourceBalanceBefore = fromAccount.balance;
        double targetBalanceBefore = toAccount.balance;

        try {

            fromAccount.balance -= amount;
            toAccount.balance += amount;

            Transaction transaction =
                    new Transaction(
                            "TRANSFER",
                            fromId,
                            toId,
                            amount
                    );

            fromAccount.transactions.add(transaction);
            toAccount.transactions.add(transaction);

            System.out.println("Transfer successful!");
            System.out.println(
                    "Transferred ₹" + amount +
                    " from " + fromId +
                    " to " + toId
            );

        } catch (Exception e) {

            // Rollback both accounts
            fromAccount.balance = sourceBalanceBefore;
            toAccount.balance = targetBalanceBefore;

            throw e;
        }
    }

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== SecureBank =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Balance Inquiry");
            System.out.println("5. Close Account");
            System.out.println("6. Transfer");
            System.out.println("7. Exit");

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
                        System.out.print("Enter Source Account ID: ");
                        int fromId = scanner.nextInt();

                        System.out.print("Enter Target Account ID: ");
                        int toId = scanner.nextInt();

                        System.out.print("Enter amount: ");
                        double transferAmount = scanner.nextDouble();

                        transfer(fromId, toId, transferAmount);
                        break;

                    case 7:
                        System.out.println("Thank you for using SecureBank!");
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid operation!");
                }

            } catch (AccountNotFoundException |
                     InsufficientFundsException e) {

                System.out.println(e.getMessage());

            } catch (Exception e) {

                System.out.println(
                        "Operation failed. Transaction rolled back."
                );
            }
        }
    }
}
