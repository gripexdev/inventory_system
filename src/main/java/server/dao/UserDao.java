package server.dao;

import server.models.User;

import java.sql.*;

public class UserDao {

    private static final String URL = "jdbc:mysql://localhost:3306/inventory_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void addUser(String name, String email, String password){
        try{
            //clear users table before adding user
            String deleteSql = "DELETE FROM users";
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(deleteSql);
            stmt.executeUpdate();

            //adding user
            String addSql = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
            PreparedStatement addStmt = conn.prepareStatement(addSql);
            addStmt.setString(1, name);
            addStmt.setString(2, email);
            addStmt.setString(3, password);
            addStmt.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public User findUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setEmail(rs.getString("email"));
                    user.setPassword(rs.getString("password"));
                    user.setName(rs.getString("name"));
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
