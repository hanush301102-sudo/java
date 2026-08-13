import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankConsoleApp {

    // ============================================================
    // COMMIT 2
    // HashMap-backed account ledger
    // ============================================================

    private static final Map<Integer, Account> accounts = new HashMap<>();

    // Generates unique account IDs
    private static int nextAccountId = 1;


    // ============================================================
    // COMMIT 2
    // Main console application
    // ============================================================

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
            System.out.println("4. Check Balance");
            System.out.println("5. Close Account");
            System.out.println("6. Exit");
            System.out.println("=================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {

                // ------------------------------------------------
                // COMMIT 2
                // Create Account
                // ------------------------------------------------
                case 1:

                    scanner.nextLine();

                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();

                    createAccount(customerName);
                    break;


                // ------------------------------------------------
                // COMMIT 3
                // Deposit
                // ------------------------------------------------
                case 2:

                    System.out.print("Enter account ID: ");
                    int depositId = scanner.nextInt();

                    System.out.print("Enter deposit amount: ₹");
                    double depositAmount = scanner.nextDouble();

                    deposit(depositId, depositAmount);
                    break;


                // ------------------------------------------------
                // COMMIT 3
                // Withdraw
                // ------------------------------------------------
                case 3:

                    System.out.print("Enter account ID: ");
                    int withdrawId = scanner.nextInt();

                    System.out.print("Enter withdrawal amount: ₹");
                    double withdrawAmount = scanner.nextDouble();

                    withdraw(withdrawId, withdrawAmount);
                    break;


                // ------------------------------------------------
                // COMMIT 4
                // Balance Inquiry
                // ------------------------------------------------
                case 4:

                    System.out.print("Enter account ID: ");
                    int balanceId = scanner.nextInt();

                    checkBalance(balanceId);
                    break;


                // ------------------------------------------------
                // COMMIT 4
                // Close Account
                // ------------------------------------------------
                case 5:

                    System.out.print("Enter account ID: ");
                    int closeId = scanner.nextInt();

                    closeAccount(closeId);
                    break;


                // ------------------------------------------------
                // Exit
                // ------------------------------------------------
                case 6:

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


    // ============================================================
    // COMMIT 2
    // Account Creation
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


    // ============================================================
    // COMMIT 3
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


    // ============================================================
    // COMMIT 3
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


    // ============================================================
    // COMMIT 4
    // Balance Inquiry
    // ============================================================

    private static void checkBalance(int id) {

        if (!accounts.containsKey(id)) {

            System.out.println();
            System.out.println("Account not found.");
            return;
        }

        Account account = accounts.get(id);

        System.out.println();
        System.out.println("========== ACCOUNT BALANCE ==========");
        System.out.println("Account ID: " + account.getId());
        System.out.println("Customer Name: " + account.getCustomerName());
        System.out.println("Current Balance: ₹" + account.getBalance());
        System.out.println("=====================================");
    }


    // ============================================================
    // COMMIT 4
    // Close Account
    // ============================================================

    private static void closeAccount(int id) {

        if (!accounts.containsKey(id)) {

            System.out.println();
            System.out.println("Account not found.");
            return;
        }

        Account account = accounts.get(id);

        accounts.remove(id);

        System.out.println();
        System.out.println("Account closed successfully.");
        System.out.println("Account ID: " + account.getId());
        System.out.println("Customer Name: " + account.getCustomerName());
    }
}


// =================================================================
// COMMIT 1
// Account Domain Model
// =================================================================

class Account {

    private int id;
    private String customerName;
    private double balance;


    // -------------------------------------------------------------
    // COMMIT 1
    // Constructor
    // -------------------------------------------------------------

    public Account(int id, String customerName) {

        this.id = id;
        this.customerName = customerName;
        this.balance = 0.0;
    }


    // -------------------------------------------------------------
    // COMMIT 1
    // Getters
    // -------------------------------------------------------------

    public int getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getBalance() {
        return balance;
    }


    // -------------------------------------------------------------
    // COMMIT 3
    // Deposit
    // -------------------------------------------------------------

    public void deposit(double amount) {

        balance += amount;
    }


    // -------------------------------------------------------------
    // COMMIT 3
    // Withdraw
    // -------------------------------------------------------------

    public void withdraw(double amount) {

        balance -= amount;
    }
}