package server.dao;

import server.models.User;

import java.sql.*;

public class UserDao {

    // Informations de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/inventory_management";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    // Méthode pour obtenir une connexion à la base de données
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Méthode pour ajouter un utilisateur à la base de données
    public void addUser(String name, String email, String password){
        try{
            // Effacer la table des utilisateurs avant d'ajouter un nouvel utilisateur
            String deleteSql = "DELETE FROM users";
            Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(deleteSql);
            stmt.executeUpdate();

            // Ajouter l'utilisateur
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

    // Méthode pour trouver un utilisateur en fonction de l'email et du mot de passe
    public User findUser(String email, String password) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                // Si l'utilisateur est trouvé, retourner un objet User avec les informations
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
