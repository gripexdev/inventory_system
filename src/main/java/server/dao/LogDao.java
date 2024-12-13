package server.dao;

import server.models.Log;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LogDao {

    private static final String URL = "jdbc:mysql://localhost:3306/inventory_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Log> getLogs(String query){
        String sql = "SELECT * FROM logs WHERE user LIKE ? OR action LIKE ?";
        List<Log> logs = new ArrayList<>();
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            stmt.setString(2, "%" + query + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Log log = new Log(
                        rs.getInt("id"),
                        rs.getString("user"),
                        rs.getString("action"),
                        rs.getTimestamp("createdAt")
                );
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
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
