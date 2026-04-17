package services;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminService {
    public static void viewallusers(){
        try (Connection conn = DatabaseConnection.getConnection()) {
            String userQuery = "SELECT u.user_id, u.username, u.role, a.account_number, a.account_type, a.balance " +
                    "FROM users u LEFT JOIN accounts a ON u.user_id = a.user_id " +
                    "WHERE u.role = 'CUSTOMER' ORDER BY u.user_id";
            PreparedStatement stmt = conn.prepareStatement(userQuery);
            ResultSet rs = stmt.executeQuery();

            int lastUserId = -1;
            while (rs.next()) {
                int userId = rs.getInt("user_id");
                if (userId != lastUserId) {
                    System.out.println("\nUser ID: " + userId);
                    System.out.println("Username: " + rs.getString("username"));
                    lastUserId = userId;
                }
                String accNum = rs.getString("account_number");
                if (accNum != null) {
                    System.out.println("    Account Number: " + accNum);
                    System.out.println("    Type: " + rs.getString("account_type"));
                    System.out.println("    Balance: " + rs.getDouble("balance"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(String username) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String deleteQuery = "DELETE FROM users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteQuery);
            stmt.setString(1, username);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Bank account deleted successfully.");
            } else {
                System.out.println("Bank account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
