package server.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class LogDao {

    private static final String URL = "jdbc:mysql://localhost:3306/inventory_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addLog(String user, String action, Timestamp createdAt) throws SQLException {
        try{
            Connection conn = getConnection();
            String addSql = "INSERT INTO logs(user, action, createdAt) VALUES (?, ?, ?)";
            PreparedStatement addStmt = conn.prepareStatement(addSql);
            addStmt.setString(1, user);
            addStmt.setString(2, action);
            addStmt.setTimestamp(3, createdAt);
            addStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
