package services;

import database.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Random;

public class AccountService {

    public static void createnewaccount(int userId, String accountType, double initialDeposit) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String accountNumber = generateUniqueAccountNumber(conn);

            String query = "INSERT INTO accounts (account_number, user_id, account_type, balance) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, accountNumber);
            stmt.setInt(2, userId);
            stmt.setString(3, accountType);
            stmt.setDouble(4, initialDeposit);
            stmt.executeUpdate();

            System.out.println("Account created. Account Number: " + accountNumber);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void viewaccount(String accountNumber) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM accounts WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Account Number: " + rs.getString("account_number"));
                System.out.println("Type: " + rs.getString("account_type"));
                System.out.println("Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteaccount(String accountNumber) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "DELETE FROM accounts WHERE account_number = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, accountNumber);
            int rows = stmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Account deleted.");
            } else {
                System.out.println("Account not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String generateUniqueAccountNumber(Connection conn) throws SQLException {
        Random rand = new Random();
        String accNum;
        boolean exists;

        do {
            accNum = String.format("%07d", rand.nextInt(10000000));
            String checkQuery = "SELECT account_number FROM accounts WHERE account_number = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, accNum);
            ResultSet rs = checkStmt.executeQuery();
            exists = rs.next();
        } while (exists);

        return accNum;
    }
}


//public class accountservice {
//    public static void createnewaccount(int userid, String accounttype, double balance) {
//        if(balance<1000){
//            System.out.println("Initial deposit must be at least 1000.");
//            return;
//        }
//        try (Connection conn = databaseconnection.getconnection()) {
//            String account_Number = generateUniqueAccountNumber();
//            String query = "INSERT INTO accounts (account_number, user_id, account_type, balance) VALUES (?, ?, ?, ?)";
//            PreparedStatement stmt = conn.prepareStatement(query);
//            stmt.setString(1, account_Number);
//            stmt.setInt(2, userid);
//            stmt.setString(3, accounttype);
//            stmt.setDouble(4, balance);
//            stmt.executeUpdate();
//            System.out.println("Account Created Successfully");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void viewaccount(String accountNumber) {
//        try (Connection conn = databaseconnection.getconnection()) {
//            String query = "SELECT * FROM accounts WHERE account_number = ?";
//            PreparedStatement stmt = conn.prepareStatement(query);
//            stmt.setInt(1, account_Number);
//            ResultSet rs = stmt.executeQuery();
//            boolean found = false;
//            while (rs.next()) {
//                found = true;
//                System.out.println("Account ID: " + rs.getInt("account_number"));
//                System.out.println("Account Type: " + rs.getString("account_type"));
//                System.out.println("Balance: Rs" + rs.getDouble("balance"));
//                System.out.println("-----------------------------------");
//            }
//            if (!found) {
//                System.out.println("No account found");
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void deleteaccount(String accountNumber) {
//        try (Connection conn = databaseconnection.getconnection()) {
//            String query = "DELETE FROM accounts WHERE account_number = ?";
//            PreparedStatement stmt = conn.prepareStatement(query);
//            stmt.setInt(1, account_Number);
//            int row = stmt.executeUpdate();
//            if (row > 0) {
//                System.out.println("Account Deleted Successfully");
//            } else {
//                System.out.println("Account Not Found");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//}
