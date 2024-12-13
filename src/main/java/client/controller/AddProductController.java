package client.controller;

import client.util.LogoutHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import server.auth.UserSession;
import server.models.Product;
import server.service.InventoryService;
import server.service.LogService;
import server.service.LogServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class AddProductController implements Initializable {
    @FXML
    private TextField nameField, categoryField, quantityField, priceField;
    @FXML
    private Label nameErrorLabel, categoryErrorLabel, quantityErrorLabel, priceErrorLabel;

    private InventoryService inventoryService;
    private LogService logService;

    public AddProductController() {
        try {
            inventoryService = (InventoryService) Naming.lookup("rmi://localhost:1099/InventoryService");
            logService = new LogServiceImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveProduct(ActionEvent event) {
        boolean isValid = true;

        // Clear previous error messages
        clearErrorMessages();

        // Validate fields
        String name = nameField.getText();
        String category = categoryField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        if (name == null || name.trim().isEmpty()) {
            nameErrorLabel.setText("Product name is required.");
            isValid = false;
        }
        if (category == null || category.trim().isEmpty()) {
            categoryErrorLabel.setText("Category is required.");
            isValid = false;
        }
        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                quantityErrorLabel.setText("Quantity must be a positive number.");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            quantityErrorLabel.setText("Invalid quantity. Please enter a valid number.");
            isValid = false;
        }

        try {
            double price = Double.parseDouble(priceText);
            if (price <= 0) {
                priceErrorLabel.setText("Price must be a positive number.");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            priceErrorLabel.setText("Invalid price. Please enter a valid number.");
            isValid = false;
        }

        if (isValid) {
            // Create a Product object and add it
            Product product = new Product(name, category, Integer.parseInt(quantityText), Double.parseDouble(priceText));
            try {
                inventoryService.addProduct(name, category, Integer.parseInt(quantityText), Double.parseDouble(priceText));

                // Log the product creation action
                String username = UserSession.getInstance().getName();
                String logMessage = "Product added: " + product.getName() + ", Category: " + product.getCategory();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logService.addLog(username, logMessage, timestamp);

                // Show success alert
                showAlert("Success", "Product added successfully.", Alert.AlertType.INFORMATION);

                // Clear fields after adding
                nameField.clear();
                categoryField.clear();
                quantityField.clear();
                priceField.clear();
            } catch (RemoteException e) {
                e.printStackTrace();
                showAlert("Error", "Failed to add product: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void cancel(ActionEvent event) {
        try {
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
            showAlert("Error", "Failed to load the dashboard: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void logout(ActionEvent event) {
        String username = UserSession.getInstance().getName();
        LogoutHelper.logout(username, event);
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearErrorMessages() {
        nameErrorLabel.setText("");
        categoryErrorLabel.setText("");
        quantityErrorLabel.setText("");
        priceErrorLabel.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Optionally initialize some data here if needed
    }
}
