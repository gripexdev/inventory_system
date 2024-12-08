package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();



    }
    public static void main(String[] args) {
        launch(args);
    }
}