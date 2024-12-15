package client.controller;

import client.util.LogoutHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import java.rmi.Naming;
import java.sql.Timestamp;

public class UpdateProductController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private TextField categoryField;

    @FXML
    private Label nameErrorLabel, categoryErrorLabel, quantityErrorLabel, priceErrorLabel;

    private InventoryService inventoryService;
    private LogService logService;

    private Product currentProduct;

    public UpdateProductController() throws Exception {
        inventoryService = (InventoryService) Naming.lookup("rmi://localhost:1099/InventoryService");
        logService = new LogServiceImpl();
    }

    public void setProductDetails(Product product) {
        this.currentProduct = product;

        // Pre-fill the form fields with product details
        nameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        quantityField.setText(String.valueOf(product.getQuantity()));
        categoryField.setText(product.getCategory());
    }

    @FXML
    public void logout(ActionEvent event) {
        String username = UserSession.getInstance().getName();
        LogoutHelper.logout(username, event);
    }

    @FXML
    private void onUpdateProduct() {
        // Clear all previous error messages
        clearErrorLabels();

        boolean isValid = true;

        String name = nameField.getText();
        String category = categoryField.getText();
        String priceText = priceField.getText();
        String quantityText = quantityField.getText();

        // Validate each field and update error labels
        if (name == null || name.trim().isEmpty()) {
            nameErrorLabel.setText("Product name is required.");
            isValid = false;
        }

        if (category == null || category.trim().isEmpty()) {
            categoryErrorLabel.setText("Category is required.");
            isValid = false;
        }

        double price = 0.0;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) {
                priceErrorLabel.setText("Price must be a positive number.");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            priceErrorLabel.setText("Enter a valid price.");
            isValid = false;
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity < 0) {
                quantityErrorLabel.setText("Quantity must be a positive integer.");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            quantityErrorLabel.setText("Enter a valid quantity.");
            isValid = false;
        }

        // Check if the new data is identical to the old data
        if (isValid) {
            if (name.equals(currentProduct.getName()) &&
                    category.equals(currentProduct.getCategory()) &&
                    price == currentProduct.getPrice() &&
                    quantity == currentProduct.getQuantity()) {

                // Show error message if data is the same
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("No changes detected. The product data is the same as before.");
                alert.showAndWait();
                return;
            }

            // Proceed with updating the product
            try {
                // get the actual username
                String username = UserSession.getInstance().getName();

                // Log the old data
                String oldData = String.format("Old Data - Name: %s, Category: %s, Price: %.2f, Quantity: %d",
                        currentProduct.getName(), currentProduct.getCategory(), currentProduct.getPrice(), currentProduct.getQuantity());

                // Update the product
                currentProduct.setName(name);
                currentProduct.setCategory(category);
                currentProduct.setPrice(price);
                currentProduct.setQuantity(quantity);

                inventoryService.updateProduct(
                        currentProduct.getId(),
                        currentProduct.getName(),
                        currentProduct.getCategory(),
                        currentProduct.getQuantity(),
                        currentProduct.getPrice()
                );

                // Log the changes
                String newData = String.format("New Data - Name: %s, Category: %s, Price: %.2f, Quantity: %d",
                        name, category, price, quantity);
                String logMessage = String.format("Product updated successfully.\n%s\n%s", oldData, newData);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logService.addLog(username, logMessage, timestamp);

                // Show success message
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Product updated successfully!");
                alert.showAndWait();

                // Return to dashboard
                returnToDashboard();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Failed to update product. Please try again.");
                alert.showAndWait();
            }
        }
    }

    private void clearErrorLabels() {
        nameErrorLabel.setText("");
        categoryErrorLabel.setText("");
        priceErrorLabel.setText("");
        quantityErrorLabel.setText("");
    }

    @FXML
    private void returnToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/ressources/dashboard.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void loadLogs(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/ressources/logs.fxml"));
        Parent root = fxmlLoader.load();

        LogController logController = fxmlLoader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setFullScreen(false);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
