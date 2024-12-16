package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Charger le fichier FXML pour la page de connexion
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/ressources/login.fxml"));
        if (fxmlLoader == null) {
            System.out.println("FXMLLoader a renvoyé null !");
        }

        // Créer la scène à partir du fichier FXML
        Scene scene = new Scene(fxmlLoader.load());

        // Désactiver le plein écran et rendre la fenêtre non redimensionnable
        stage.setFullScreen(false);
        stage.setResizable(false);

        // Définir le titre de la fenêtre
        stage.setTitle("Gestion d'Inventaire");

        // Ajouter une icône à la fenêtre
        Image icon = new Image(getClass().getResource("/client/ressources/logo.png").toExternalForm());
        stage.getIcons().add(icon);

        // Définir la scène et afficher la fenêtre
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Lancer l'application
        launch(args);
    }
}
