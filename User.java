package Societym;
import java.sql.*;
import java.util.Scanner;

public class User {
    private final String username;
    private final DatabaseConnector dbConnector = new DatabaseConnector();

    public User(String username) {
        this.username = username;
    }

    public void showMenu() 
    {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Welcome, " + username);
            System.out.println("1. View Profile");
            System.out.println("2. Store Member Details");
            System.out.println("3. Manage Requests and Complaints");
           // System.out.println("4. Staff/Worker Management");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
          // scanner.close();

            switch (choice) {
                case 1:
                    viewProfile();
                    break;
                case 2:
                    storeMemberDetails();
                    break;
                case 3:
                    manageRequestsAndComplaints();
                    break;
                // case 4:
                //     manageStaff();
                //     break;
                case 5:
                    System.out.println("Logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
        
    }

    private void viewProfile() {
        System.out.println("Displaying profile for user: " + username);
        // Additional profile details can be fetched from DB

    }

    private void storeMemberDetails() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter contact information: ");
        String contactInfo = scanner.nextLine();
        System.out.print("Enter family members (comma-separated): ");
        String familyMembers = scanner.nextLine();
        System.out.print("Enter vehicle details: ");
        String vehicleDetails = scanner.nextLine();
scanner.close();
        String query = "INSERT INTO member_details (username, contact_info, family_members, vehicle_details) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, contactInfo);
            stmt.setString(3, familyMembers);
            stmt.setString(4, vehicleDetails);
            stmt.executeUpdate();

            System.out.println("Member details stored successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to store member details.");
        }
    }

    private void manageRequestsAndComplaints() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your request or complaint: ");
        String request = scanner.nextLine();
scanner.close();
        String query = "INSERT INTO requests_and_complaints (username, request) VALUES (?, ?)";
        try (Connection conn = dbConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, request);
            stmt.executeUpdate();

            System.out.println("Request/Complaint submitted successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failed to submit request/complaint.");
        }
    }

   
}