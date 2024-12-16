package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.auth.UserSession;
import server.models.User;
import server.service.InventoryService;
import server.service.LogService;
import server.service.LogServiceImpl;
import server.service.UserService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;

public class LoginController {
    @FXML
    public TextField emailField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button loginButton;
    @FXML
    public Label registerLink;
    @FXML
    public Label errorLabel;

    InventoryService service = (InventoryService) Naming.lookup("rmi://localhost:1099/InventoryService");
    UserService userService = (UserService) Naming.lookup("rmi://localhost:1099/UserService");

    private LogService logService;

    public LoginController() throws MalformedURLException, NotBoundException, RemoteException {
        logService = new LogServiceImpl();
    }

    public void login(ActionEvent event) throws MalformedURLException, NotBoundException, RemoteException {
        if (emailField.getText().isBlank() == false && passwordField.getText().isBlank() == false) {
            User user = userService.findUser(emailField.getText(), passwordField.getText());
            if (user != null) {
                errorLabel.setText("Utilisateur trouvé");
                try {
                    UserSession.getInstance().setName(user.getName());
                    logService.addLog(UserSession.getInstance().getName(), "Authentification", new Timestamp(System.currentTimeMillis()));

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/ressources/dashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setFullScreen(false);
                    stage.setResizable(false);
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                errorLabel.setText("Utilisateur non trouvé !");
            }
        } else {
            String name = "nom test";
            String category = "catégorie test";
            int quantity = 10;
            double price = 100;
            service.addProduct(name, category, quantity, price);
            errorLabel.setText("Veuillez entrer un email et un mot de passe !");
        }
    }
}
