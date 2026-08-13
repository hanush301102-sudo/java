import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankConsoleApp {
    // HashMap-backed account ledger

    private static final Map<Integer, Account> accounts = new HashMap<>();

    // Generates unique account IDs
    private static int nextAccountId = 1;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        while (running) {

            System.out.println();
            System.out.println("=================================");
            System.out.println("         SECURE BANK");
            System.out.println("=================================");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Exit");
            System.out.println("=================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {

                case 1:
                    scanner.nextLine(); // consume newline

                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();

                    createAccount(customerName);
                    break;

                case 2:
                    System.out.print("Enter account ID: ");
                    int depositId = scanner.nextInt();

                    System.out.print("Enter deposit amount: ₹");
                    double depositAmount = scanner.nextDouble();

                    deposit(depositId, depositAmount);
                    break;

                case 3:
                    System.out.print("Enter account ID: ");
                    int withdrawId = scanner.nextInt();

                    System.out.print("Enter withdrawal amount: ₹");
                    double withdrawAmount = scanner.nextDouble();

                    withdraw(withdrawId, withdrawAmount);
                    break;

                case 4:
                    running = false;
                    System.out.println();
                    System.out.println("Thank you for using SecureBank.");
                    break;

                default:
                    System.out.println();
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Account creation
    // ============================================================

    private static void createAccount(String customerName) {

        int id = nextAccountId++;

        Account account = new Account(id, customerName);

        accounts.put(id, account);

        System.out.println();
        System.out.println("Account created successfully.");
        System.out.println("Account ID: " + id);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Opening Balance: ₹0.00");
    }

    // Deposit
    // ============================================================

    private static void deposit(int id, double amount) {

        if (!accounts.containsKey(id)) {
            System.out.println();
            System.out.println("Account not found.");
            return;
        }

        if (amount <= 0) {
            System.out.println();
            System.out.println("Deposit amount must be greater than zero.");
            return;
        }

        Account account = accounts.get(id);

        account.deposit(amount);

        System.out.println();
        System.out.println("Deposit successful.");
        System.out.println("Account ID: " + id);
        System.out.println("Deposited: ₹" + amount);
        System.out.println("Current Balance: ₹" + account.getBalance());
    }

    // Withdraw
    // ============================================================

    private static void withdraw(int id, double amount) {

        if (!accounts.containsKey(id)) {
            System.out.println();
            System.out.println("Account not found.");
            return;
        }

        if (amount <= 0) {
            System.out.println();
            System.out.println("Withdrawal amount must be greater than zero.");
            return;
        }

        Account account = accounts.get(id);

        if (amount > account.getBalance()) {
            System.out.println();
            System.out.println("Insufficient funds.");
            System.out.println("Current Balance: ₹" + account.getBalance());
            System.out.println("Requested Amount: ₹" + amount);
            return;
        }

        account.withdraw(amount);

        System.out.println();
        System.out.println("Withdrawal successful.");
        System.out.println("Account ID: " + id);
        System.out.println("Withdrawn: ₹" + amount);
        System.out.println("Current Balance: ₹" + account.getBalance());
    }
}
// Account domain model
// ================================================================

class Account {

    private int id;
    private String customerName;
    private double balance;

    public Account(int id, String customerName) {
        this.id = id;
        this.customerName = customerName;
        this.balance = 0.0;
    }

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }

    // Mutates account balance
    // ============================================================

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }
}