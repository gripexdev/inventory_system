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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/ressources/login.fxml"));
        if (fxmlLoader == null) {
            System.out.println("FXMLLoader returned null!");
        }
        Scene scene = new Scene(fxmlLoader.load());
        stage.setFullScreen(false);
        stage.setResizable(false);
        stage.setTitle("Inventory Management");

        Image icon = new Image(getClass().getResource("/client/ressources/logo.png").toExternalForm());
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.show();



    }
    public static void main(String[] args) {
        launch(args);
    }
}