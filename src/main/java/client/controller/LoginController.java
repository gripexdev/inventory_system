package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import server.service.InventoryService;

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

    public void login(ActionEvent event) throws MalformedURLException, NotBoundException, RemoteException {
        if(emailField.getText().isBlank() == false && passwordField.getText().isBlank() == false){
            errorLabel.setText("coool");
        }else{
            InventoryService service = (InventoryService) Naming.lookup("rmi://localhost:1099/InventoryService");
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
