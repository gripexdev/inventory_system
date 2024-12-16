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

    // Constructeur de la classe
    public UpdateProductController() throws Exception {
        inventoryService = (InventoryService) Naming.lookup("rmi://localhost:1099/InventoryService");
        logService = new LogServiceImpl();
    }

    // Méthode pour initialiser les détails du produit
    public void setProductDetails(Product product) {
        this.currentProduct = product;

        // Remplir les champs du formulaire avec les détails du produit
        nameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        quantityField.setText(String.valueOf(product.getQuantity()));
        categoryField.setText(product.getCategory());
    }

    // Méthode de déconnexion
    @FXML
    public void logout(ActionEvent event) {
        String username = UserSession.getInstance().getName();
        LogoutHelper.logout(username, event);
    }

    // Méthode pour mettre à jour le produit
    @FXML
    private void onUpdateProduct() {
        // Réinitialiser les messages d'erreur
        clearErrorLabels();

        boolean isValid = true;

        String name = nameField.getText();
        String category = categoryField.getText();
        String priceText = priceField.getText();
        String quantityText = quantityField.getText();

        // Validation de chaque champ et mise à jour des étiquettes d'erreur
        if (name == null || name.trim().isEmpty()) {
            nameErrorLabel.setText("Le nom du produit est requis.");
            isValid = false;
        }

        if (category == null || category.trim().isEmpty()) {
            categoryErrorLabel.setText("La catégorie est requise.");
            isValid = false;
        }

        double price = 0.0;
        try {
            price = Double.parseDouble(priceText);
            if (price < 0) {
                priceErrorLabel.setText("Le prix doit être un nombre positif.");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            priceErrorLabel.setText("Entrez un prix valide.");
            isValid = false;
        }

        int quantity = 0;
        try {
            quantity = Integer.parseInt(quantityText);
            if (quantity < 0) {
                quantityErrorLabel.setText("La quantité doit être un entier positif.");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            quantityErrorLabel.setText("Entrez une quantité valide.");
            isValid = false;
        }

        // Vérifier si les nouvelles données sont identiques aux anciennes
        if (isValid) {
            if (name.equals(currentProduct.getName()) &&
                    category.equals(currentProduct.getCategory()) &&
                    price == currentProduct.getPrice() &&
                    quantity == currentProduct.getQuantity()) {

                // Afficher un message d'erreur si aucune modification n'est détectée
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Aucune modification détectée. Les données du produit sont identiques.");
                alert.showAndWait();
                return;
            }

            // Procéder à la mise à jour du produit
            try {
                // obtenir le nom d'utilisateur actuel
                String username = UserSession.getInstance().getName();

                // Journaliser les anciennes données
                String oldData = String.format("Anciennes données - Nom: %s, Catégorie: %s, Prix: %.2f, Quantité: %d",
                        currentProduct.getName(), currentProduct.getCategory(), currentProduct.getPrice(), currentProduct.getQuantity());

                // Mettre à jour le produit
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

                // Journaliser les nouvelles données
                String newData = String.format("Nouvelles données - Nom: %s, Catégorie: %s, Prix: %.2f, Quantité: %d",
                        name, category, price, quantity);
                String logMessage = String.format("Produit mis à jour avec succès.\n%s\n%s", oldData, newData);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logService.addLog(username, logMessage, timestamp);

                // Afficher un message de succès
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setContentText("Produit mis à jour avec succès !");
                alert.showAndWait();

                // Retour au tableau de bord
                returnToDashboard();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Échec de la mise à jour du produit. Veuillez réessayer.");
                alert.showAndWait();
            }
        }
    }

    // Méthode pour réinitialiser les étiquettes d'erreur
    private void clearErrorLabels() {
        nameErrorLabel.setText("");
        categoryErrorLabel.setText("");
        priceErrorLabel.setText("");
        quantityErrorLabel.setText("");
    }

    // Méthode pour retourner au tableau de bord
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

    // Méthode pour charger les journaux
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
