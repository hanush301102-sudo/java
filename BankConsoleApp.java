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
public class BankConsoleApp {
    public static void main(String[] args) {
        System.out.println("Welcome to SecureBank");
    }
}