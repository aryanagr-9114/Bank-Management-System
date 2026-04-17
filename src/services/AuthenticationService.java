package services;

import database.DatabaseConnection;
import ui.Menu;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class AuthenticationService {
    public static boolean login(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE username = ? and password = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(12));
            stmt.setString(2, hashedPassword);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int user_Id = rs.getInt("user_id");
                String role = rs.getString("role");

                System.out.println("Login Successful");

                if (role.equalsIgnoreCase("CUSTOMER")) {
                    Menu.displaycustomermenu(user_Id, username);
                } else if (role.equalsIgnoreCase("ADMIN")) {
                    Menu.displayadminmenu(user_Id, username);
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean register(String username, String password, String role) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // User exist or not
            String checkquery = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkstmt = conn.prepareStatement(checkquery);
            checkstmt.setString(1, username);
            ResultSet rs = checkstmt.executeQuery();
            if (rs.next()) {
                return false;
            }

            // Insert new user
            String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, role);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
