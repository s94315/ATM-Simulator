import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

// User class to represent ATM users
class User {
    private String userID;
    private String userPIN;
    private double accountBalance;

    public User(String userID, String userPIN, double accountBalance) {
        this.userID = userID;
        this.userPIN = userPIN;
        this.accountBalance = accountBalance;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserPIN() {
        return userPIN;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
}

// ATM class to encapsulate ATM functionalities
class ATM {
    private Map<String, User> users;

    public ATM() {
        users = new HashMap<>();
        // Sample user data
        users.put("123456", new User("123456", "1111", 1000.0));
        users.put("789012", new User("789012", "2222", 2000.0));
    }

    public boolean authenticateUser(String userID, String userPIN) {
        User user = users.get(userID);
        return user != null && user.getUserPIN().equals(userPIN);
    }

    public double checkBalance(String userID) {
        return users.get(userID).getAccountBalance();
    }

    public void deposit(String userID, double amount) {
        User user = users.get(userID);
        double currentBalance = user.getAccountBalance();
        user.setAccountBalance(currentBalance + amount);
    }

    public boolean withdraw(String userID, double amount) {
        User user = users.get(userID);
        double currentBalance = user.getAccountBalance();
        if (currentBalance >= amount) {
            user.setAccountBalance(currentBalance - amount);
            return true;
        } else {
            return false;
        }
    }
}

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ATM atm = new ATM();

        // User authentication
        System.out.print("Enter user ID: ");
        String userID = scanner.nextLine();
        System.out.print("Enter PIN: ");
        String userPIN = scanner.nextLine();

        if (atm.authenticateUser(userID, userPIN)) {
            System.out.println("Authentication successful. Welcome to the ATM!");
            boolean exit = false;
            while (!exit) {
                System.out.println("\nATM Menu:");
                System.out.println("1. Check Balance");
                System.out.println("2. Deposit Money");
                System.out.println("3. Withdraw Money");
                System.out.println("4. Exit");
                System.out.print("Select option: ");
                int option = scanner.nextInt();
                switch (option) {
                    case 1:
                        double balance = atm.checkBalance(userID);
                        System.out.println("Your current balance: $" + balance);
                        break;
                    case 2:
                        System.out.print("Enter deposit amount: $");
                        double depositAmount = scanner.nextDouble();
                        atm.deposit(userID, depositAmount);
                        System.out.println("Deposit successful.");
                        break;
                    case 3:
                        System.out.print("Enter withdrawal amount: $");
                        double withdrawAmount = scanner.nextDouble();
                        if (atm.withdraw(userID, withdrawAmount)) {
                            System.out.println("Withdrawal successful.");
                        } else {
                            System.out.println("Insufficient funds.");
                        }
                        break;
                    case 4:
                        exit = true;
                        System.out.println("Exiting ATM. Thank you!");
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Invalid user ID or PIN.");
        }
    }
}
