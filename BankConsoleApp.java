import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankConsoleApp {

    // Commit 2: HashMap-backed account ledger
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
            System.out.println("2. Exit");
            System.out.println("=================================");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline

            switch (choice) {

                case 1:
                    System.out.print("Enter customer name: ");
                    String customerName = scanner.nextLine();

                    createAccount(customerName);
                    break;

                case 2:
                    running = false;
                    System.out.println("Thank you for using SecureBank.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    // Creates a new account and stores it in the HashMap
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
}


// Commit 1: Account domain model
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
}