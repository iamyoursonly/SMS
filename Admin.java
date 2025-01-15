package Societym;

import java.sql.*;
import java.util.Scanner;

public class Admin {
    private final String username;

    public Admin(String username) {
        this.username = username;
    }

    public void showMenu() {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        int choice;
        
        // Loop to keep showing the menu until the admin logs out
        while (true) {
            System.out.println("Welcome, Admin " + username);
            System.out.println("1. Manage Users");
            System.out.println("2. Show User Details");
            System.out.println("3. Staff/Worker Management");
            System.out.println("4. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            
            switch (choice) {
                case 1:
                    manageUsers(scanner);
                    break;
                case 2:
                    showUserDetails();
                    break;
                    case 3:
                try {
                    manageStaff();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                    break;
                case 4:
                    System.out.println("Logged out successfully.");
                    return; // Exit the method and terminate the program

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void manageUsers(Scanner scanner) {
        System.out.println("Managing users...");
        System.out.println("1. Add New User");
        System.out.println("2. Update User");
        System.out.println("3. Delete User");
        
        System.out.println("4. Go Back");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        switch (choice) {
            case 1:
                addNewUser(scanner);
                break;
            case 2:
                updateUser(scanner);
                break;
            case 3:
                deleteUser(scanner);
                break;
            case 4:
                return; // Go back to the main menu
            default:
                System.out.println("Invalid choice.");
        }
    }

    private void addNewUser(Scanner scanner) {
        System.out.println("Enter user details:");
        System.out.print("Enter user name: ");
        String username = scanner.nextLine();
        System.out.print("Enter user email: ");
        String email = scanner.nextLine();
        System.out.print("Enter user phone number: ");
        String phone_number = scanner.nextLine();
        System.out.println("Enter password for user");
        String password =scanner.nextLine();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SocietyManagement", "root", "amit")) {
            String query = "INSERT INTO user_login (username, email, phone_number,password) VALUES (?, ?, ?,?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, email);
                stmt.setString(3, phone_number);
                stmt.setString(4, password);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("New user added successfully.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUser(Scanner scanner) {
        System.out.println("Enter the ID of the user to update:");
        showUserDetails(); // Display users so the admin can choose an ID
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        System.out.print("Enter new name (leave blank to keep current): ");
        String newName = scanner.nextLine();
        System.out.print("Enter new email (leave blank to keep current): ");
        String newEmail = scanner.nextLine();
        System.out.print("Enter new phone number (leave blank to keep current): ");
        String newPhone = scanner.nextLine();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/societymanagement", "root", "amit")) 
        {
            String query = "UPDATE user_login SET username = IFNULL(?, username), email = IFNULL(?, email), phone_number = IFNULL(?, phone_number) WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) 
            {
                stmt.setString(1, newName.isEmpty() ? null : newName);
                stmt.setString(2, newEmail.isEmpty() ? null : newEmail);
                stmt.setString(3, newPhone.isEmpty() ? null : newPhone);
                stmt.setInt(4, id);
                int rowsUpdated = stmt.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("User updated successfully.");
                }
            }
        } 
        catch (SQLException e)
         {
            e.printStackTrace();
        }
    }

    private void deleteUser(Scanner scanner) {
        System.out.println("Enter the ID of the user to delete:");
        showUserDetails(); // Display users so the admin can choose an ID
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline character

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/societymanagement", "root", "amit")) {
            String query = "DELETE FROM user_login WHERE user_id = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, id);
                int rowsDeleted = stmt.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("User deleted successfully.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showUserDetails() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SocietyManagement", "root", "amit")) {
            String query = "SELECT * FROM user_login";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
                if (!rs.isBeforeFirst()) {
                    System.out.println("No users found.");
                } else {
                    System.out.println("User Details:");
                    System.out.printf("%-5s  %-5s %-20s %-30s\n",  "user_id","username", "email", "phone_number");
                    while (rs.next()) {
                        //int id = rs.getInt("id");
                        String name = rs.getString("username");
                        String user_id = rs.getString("user_id");
                        String email = rs.getString("email");
                        String phone = rs.getString("phone_number");

                        System.out.printf(" %-20s %-30s %-15s\n", user_id, name, email, phone);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void manageStaff() throws SQLException 
    {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter staff name: ");
        String name = scanner.nextLine();
        System.out.print("Enter contact information: ");
        String contactInfo = scanner.nextLine();
        System.out.print("Enter salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter attendance: ");
        int attendance = scanner.nextInt();
     scanner.close();
        
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/societymanagement", "root", "amit"))
        {
         String query = "INSERT INTO staff_details (name, contact_info, salary, attendance) VALUES (?, ?, ?, ?)";

           try(  PreparedStatement stmt = conn.prepareStatement(query))
            {
            stmt.setString(1, name);
            stmt.setString(2, contactInfo);
            stmt.setDouble(3, salary);
            stmt.setInt(4, attendance);
            stmt.executeUpdate();

            System.out.println("Staff details stored successfully.");

         } 
          catch (SQLException e) 
          {
            e.printStackTrace();
              System.out.println("Failed to store staff details.");
           } 
        
        }
    }



    public static void main(String[] args) {
        Admin admin = new Admin("Amit");
        admin.showMenu();
    }
}
