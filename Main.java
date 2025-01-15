package Societym;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LoginManager loginManager = new LoginManager();
        
        while (true) {
            System.out.println("Welcome to Society Management System");
            System.out.println("1. User Login");
            System.out.println("2. Admin Login");
            System.out.println("3. Exit");
            int choice = -1;

            while (true) {
                try {
                    System.out.print("Enter your choice: ");
                    choice = Integer.parseInt(scanner.nextLine()); // Use nextLine() and parse manually
                    if (choice == 1 || choice == 2 || choice == 3) {
                        break; // Valid choice, exit loop
                    } else {
                        System.out.println("Invalid choice. Please enter 1 for User, 2 for Admin, or 3 to Exit.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                }
            }

            if (choice == 3) {
                System.out.println("Exiting the program.");
                break; // Exit the program
            }

            System.out.print("Enter Username: ");
            String username = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            boolean loginSuccessful = false;

            // Loop for login retry in case of invalid login
            if (choice == 1) {
                while (!loginSuccessful) {
                    if (loginManager.authenticateUser(username, password)) {
                        System.out.println("User Login Successful!");
                        User user = new User(username);
                        user.showMenu();
                        loginSuccessful = true; // Login success
                    } else {
                        System.out.println("Invalid User Credentials.");
                        System.out.print("Do you want to try again? (y/n): ");
                        String retryChoice = scanner.nextLine();
                        if (retryChoice.equalsIgnoreCase("n")) {
                            break; // Exit the login loop and return to the main menu
                        }
                        System.out.print("Enter Username: ");
                        username = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        password = scanner.nextLine();
                    }
                }
            } else if (choice == 2) {
                while (!loginSuccessful) {
                    if (loginManager.authenticateAdmin(username, password)) {
                        System.out.println("Admin Login Successful!");
                        Admin admin = new Admin(username);
                        admin.showMenu();
                        loginSuccessful = true; // Login success
                    } else {
                        System.out.println("Invalid Admin Credentials.");
                        System.out.print("Do you want to try again? (y/n): ");
                        String retryChoice = scanner.nextLine();
                        if (retryChoice.equalsIgnoreCase("n")) {
                            break; // Exit the login loop and return to the main menu
                        }
                        System.out.print("Enter Username: ");
                        username = scanner.nextLine();
                        System.out.print("Enter Password: ");
                        password = scanner.nextLine();
                    }
                }
            }
        }

        scanner.close();
    }
}
