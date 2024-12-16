package client.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import server.auth.UserSession;
import server.service.LogService;
import server.service.LogServiceImpl;

import java.io.IOException;
import java.sql.Timestamp;

public class LogoutHelper {
    private static LogService logService = new LogServiceImpl();

    // Méthode pour déconnecter l'utilisateur
    public static void logout(String username, javafx.event.ActionEvent event) {
        try {
            // Effacer la session de l'utilisateur
            UserSession.getInstance().setName(null);

            // Enregistrer l'action de déconnexion dans les journaux
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logService.addLog(username, "Utilisateur déconnecté", timestamp);

            // Rediriger vers la page de connexion
            FXMLLoader fxmlLoader = new FXMLLoader(LogoutHelper.class.getResource("/client/ressources/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
