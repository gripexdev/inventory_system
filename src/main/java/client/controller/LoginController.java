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
import server.models.User;
import server.service.InventoryService;
import server.service.UserService;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

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

    public LoginController() throws MalformedURLException, NotBoundException, RemoteException {
    }


    public void login(ActionEvent event) throws MalformedURLException, NotBoundException, RemoteException {
        if(emailField.getText().isBlank() == false && passwordField.getText().isBlank() == false){
            User user = userService.findUser(emailField.getText(), passwordField.getText());
            if(user != null){
                errorLabel.setText("User Found" );
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/ressources/dashboard.fxml"));
                    Parent root = fxmlLoader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                errorLabel.setText("User Not Found !");
            }
        }else{
            String name = "test name";
            String category = "test category";
            int quantity = 10;
            double price = 100;
            service.addProduct(name, category, quantity, price);
            errorLabel.setText("Veuillez Entrer un email & mot de passe !");
        }
    }


//    public void validateLogin(){
//        DatabaseConnection connection = new DatabaseConnection();
//        Connection connectDB = connection.getConnection();
//
//        String testSelect = "SELECT * FROM users";
//
//        try{
//            Statement statement = connectDB.createStatement();
//            ResultSet resultSet = statement.executeQuery(testSelect);
//
//            while(resultSet.next()){
//                int id = resultSet.getInt("id");
//                String username = resultSet.getString("name");
//                String email = resultSet.getString("email");
//                String password = resultSet.getString("password");
//
//                System.out.println("ID: " + id + ", Username: " + username + ", Email: " + email + ", Password : " + password);
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }

}
