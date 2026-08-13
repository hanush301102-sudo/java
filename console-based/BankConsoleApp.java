import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankConsoleApp {

    // ============================================================
    // COMMIT 2
    // HashMap-backed account ledger
    // ============================================================

    private static final Map<Integer, Account> accounts = new HashMap<>();

    // Generates unique account IDs.
    // IDs are never reused after an account is closed.
    private static int nextAccountId = 1;


    // ============================================================
    // COMMIT 2
    // Main console application
    // ============================================================

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        System.out.println("=================================");
        System.out.println("       WELCOME TO SECUREBANK");
        System.out.println("=================================");

        while (running) {

            printMenu();

            try {

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
                        int depositId = readAccountId(scanner);
                        double depositAmount = readAmount(scanner, "Enter deposit amount: ₹");

                        try {
                            deposit(depositId, depositAmount);
                        } catch (AccountNotFoundException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;


                    // ------------------------------------------------
                    // COMMIT 3
                    // Withdraw
                    // ------------------------------------------------
                    case 3:
                        int withdrawId = readAccountId(scanner);
                        double withdrawAmount = readAmount(scanner, "Enter withdrawal amount: ₹");

                        try {
                            withdraw(withdrawId, withdrawAmount);
                        } catch (AccountNotFoundException | InsufficientFundsException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;


                    // ------------------------------------------------
                    // COMMIT 4
                    // Balance Inquiry
                    // ------------------------------------------------
                    case 4:
                        int balanceId = readAccountId(scanner);

                        try {
                            checkBalance(balanceId);
                        } catch (AccountNotFoundException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;


                    // ------------------------------------------------
                    // COMMIT 4
                    // Close Account
                    // ------------------------------------------------
                    case 5:
                        int closeId = readAccountId(scanner);

                        try {
                            closeAccount(closeId);
                        } catch (AccountNotFoundException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;


                    // ------------------------------------------------
                    // COMMIT 5
                    // Testing
                    // ------------------------------------------------
                    case 6:
                        runTests();
                        break;


                    // ------------------------------------------------
                    // Exit
                    // ------------------------------------------------
                    case 7:
                        running = false;

                        System.out.println();
                        System.out.println("Thank you for using SecureBank.");
                        break;


                    default:
                        System.out.println();
                        System.out.println("Error: Invalid menu choice.");
                }

            } catch (Exception e) {

                /*
                 * Prevent unexpected invalid console input from
                 * crashing the entire application.
                 */
                System.out.println();
                System.out.println("Invalid input. Please enter the correct value.");

                scanner.nextLine();
            }
        }

        scanner.close();
    }


    // ============================================================
    // COMMIT 2
    // Menu
    // ============================================================

    private static void printMenu() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("           SECUREBANK");
        System.out.println("=================================");
        System.out.println("1. Create Account");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Check Balance");
        System.out.println("5. Close Account");
        System.out.println("6. Run Tests");
        System.out.println("7. Exit");
        System.out.println("=================================");
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
    // COMMIT 3 + COMMIT 5
    // Deposit
    // ============================================================

    private static void deposit(int id, double amount)
            throws AccountNotFoundException {

        // COMMIT 5: Account existence validation
        if (!accounts.containsKey(id)) {
            throw new AccountNotFoundException(
                    "Account ID " + id + " does not exist."
            );
        }

        // COMMIT 5: Amount validation
        if (amount <= 0) {
            System.out.println();
            System.out.println(
                    "Deposit rejected: amount must be greater than zero."
            );
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
    // COMMIT 3 + COMMIT 5
    // Withdraw
    // ============================================================

    private static void withdraw(int id, double amount)
            throws AccountNotFoundException, InsufficientFundsException {

        // COMMIT 5: Account existence validation
        if (!accounts.containsKey(id)) {
            throw new AccountNotFoundException(
                    "Account ID " + id + " does not exist."
            );
        }

        // COMMIT 5: Amount validation
        if (amount <= 0) {
            System.out.println();
            System.out.println(
                    "Withdrawal rejected: amount must be greater than zero."
            );
            return;
        }

        Account account = accounts.get(id);

        // COMMIT 5: Insufficient funds validation
        if (amount > account.getBalance()) {
            throw new InsufficientFundsException(
                    "Insufficient funds. Current balance: ₹"
                            + account.getBalance()
                            + ", requested: ₹"
                            + amount
            );
        }

        account.withdraw(amount);

        System.out.println();
        System.out.println("Withdrawal successful.");
        System.out.println("Account ID: " + id);
        System.out.println("Withdrawn: ₹" + amount);
        System.out.println("Current Balance: ₹" + account.getBalance());
    }


    // ============================================================
    // COMMIT 4 + COMMIT 5
    // Balance Inquiry
    // ============================================================

    private static void checkBalance(int id)
            throws AccountNotFoundException {

        if (!accounts.containsKey(id)) {
            throw new AccountNotFoundException(
                    "Account ID " + id + " does not exist."
            );
        }

        Account account = accounts.get(id);

        System.out.println();
        System.out.println("=================================");
        System.out.println("        ACCOUNT DETAILS");
        System.out.println("=================================");
        System.out.println("Account ID: " + account.getId());
        System.out.println("Customer Name: " + account.getCustomerName());
        System.out.println("Current Balance: ₹" + account.getBalance());
        System.out.println("=================================");
    }


    // ============================================================
    // COMMIT 4 + COMMIT 5
    // Close Account
    // ============================================================

    private static void closeAccount(int id)
            throws AccountNotFoundException {

        if (!accounts.containsKey(id)) {
            throw new AccountNotFoundException(
                    "Account ID " + id + " does not exist."
            );
        }

        Account account = accounts.get(id);

        accounts.remove(id);

        System.out.println();
        System.out.println("Account closed successfully.");
        System.out.println("Account ID: " + account.getId());
        System.out.println("Customer Name: " + account.getCustomerName());
    }


    // ============================================================
    // COMMIT 5
    // Input Validation Helpers
    // ============================================================

    private static int readAccountId(Scanner scanner) {

        System.out.print("Enter account ID: ");

        return scanner.nextInt();
    }


    private static double readAmount(Scanner scanner, String message) {

        System.out.print(message);

        return scanner.nextDouble();
    }


    // ============================================================
    // COMMIT 5
    // Testing
    // ============================================================

    private static void runTests() {

        System.out.println();
        System.out.println("=================================");
        System.out.println("       SECUREBANK TESTS");
        System.out.println("=================================");

        testCreateAccounts();

        System.out.println();

        testDepositAndWithdraw();

        System.out.println();

        testInsufficientFunds();

        System.out.println();

        testClosedAccount();

        System.out.println();
        System.out.println("=================================");
        System.out.println("         TESTS COMPLETE");
        System.out.println("=================================");
    }


    // ============================================================
    // COMMIT 5
    // Test: Create 3 accounts
    // ============================================================

    private static void testCreateAccounts() {

        int startingAccountCount = accounts.size();

        createAccount("Test Customer 1");
        createAccount("Test Customer 2");
        createAccount("Test Customer 3");

        int accountsCreated =
                accounts.size() - startingAccountCount;

        if (accountsCreated == 3) {
            System.out.println("PASS: Created 3 accounts.");
        } else {
            System.out.println("FAIL: Could not create 3 accounts.");
        }
    }


    // ============================================================
    // COMMIT 5
    // Test: Deposit ₹5,000 and withdraw ₹2,000
    // Expected balance = ₹3,000
    // ============================================================

    private static void testDepositAndWithdraw() {

        int testAccountId = nextAccountId;

        createAccount("Transaction Test");

        try {

            deposit(testAccountId, 5000);

            withdraw(testAccountId, 2000);

            Account account = accounts.get(testAccountId);

            if (account != null && account.getBalance() == 3000) {

                System.out.println(
                        "PASS: ₹5,000 deposit + ₹2,000 withdrawal = ₹3,000."
                );

            } else {

                System.out.println(
                        "FAIL: Expected balance ₹3,000."
                );
            }

        } catch (AccountNotFoundException |
                 InsufficientFundsException e) {

            System.out.println("FAIL: " + e.getMessage());
        }
    }


    // ============================================================
    // COMMIT 5
    // Test: Insufficient Funds
    // ============================================================
    private static void testInsufficientFunds() {

    int testAccountId = nextAccountId;

    createAccount("Insufficient Funds Test");

    try {

        deposit(testAccountId, 3000);

        withdraw(testAccountId, 10000);

        System.out.println(
                "FAIL: ₹10,000 withdrawal should have been rejected."
        );

    } catch (InsufficientFundsException e) {

        Account account = accounts.get(testAccountId);

        if (account != null && account.getBalance() == 3000) {

            System.out.println(
                    "PASS: ₹10,000 withdrawal rejected."
            );

            System.out.println(
                    "PASS: Balance remained ₹3,000."
            );

        } else {

            System.out.println(
                    "FAIL: Balance changed after rejected withdrawal."
            );
        }

    } catch (AccountNotFoundException e) {

        System.out.println(
                "FAIL: " + e.getMessage()
        );
    }
}

    // ============================================================
    // COMMIT 5
    // Test: Closed Account
    // ============================================================

    private static void testClosedAccount() {

        int testAccountId = nextAccountId;

        createAccount("Closed Account Test");

        try {

            closeAccount(testAccountId);

            // Try to deposit into closed account.
            deposit(testAccountId, 500);

            System.out.println(
                    "FAIL: Deposit into closed account was accepted."
            );

        } catch (AccountNotFoundException e) {

            System.out.println(
                    "PASS: Operation on closed account rejected."
            );

        }
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


// =================================================================
// COMMIT 5
// Custom Exception: Account Not Found
// =================================================================

class AccountNotFoundException extends Exception {

    public AccountNotFoundException(String message) {

        super(message);
    }
}


// =================================================================
// COMMIT 5
// Custom Exception: Insufficient Funds
// =================================================================

class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {

        super(message);
    }
}