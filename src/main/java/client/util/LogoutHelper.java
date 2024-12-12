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

    public static void logout(String username, javafx.event.ActionEvent event) {
        try {
            // Clear the user session
            UserSession.getInstance().setName(null);

            // Log the logout action
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logService.addLog(username, "User logged out", timestamp);

            // Navigate to the login page
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
