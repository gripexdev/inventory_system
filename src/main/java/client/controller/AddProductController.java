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

    // Constructeur : Configuration des services RMI pour gérer les produits et les logs
    public AddProductController() {
        try {
            inventoryService = (InventoryService) Naming.lookup("rmi://localhost:1099/InventoryService");
            logService = new LogServiceImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode appelée lors de la soumission du formulaire pour enregistrer un produit
    @FXML
    public void saveProduct(ActionEvent event) {
        boolean isValid = true;

        // Effacer les messages d'erreur précédents
        clearErrorMessages();

        // Validation des champs
        String name = nameField.getText();
        String category = categoryField.getText();
        String quantityText = quantityField.getText();
        String priceText = priceField.getText();

        // Vérification du champ "nom"
        if (name == null || name.trim().isEmpty()) {
            nameErrorLabel.setText("Le nom du produit est requis.");
            isValid = false;
        }
        // Vérification du champ "catégorie"
        if (category == null || category.trim().isEmpty()) {
            categoryErrorLabel.setText("La catégorie est requise.");
            isValid = false;
        }
        // Validation de la quantité
        try {
            int quantity = Integer.parseInt(quantityText);
            if (quantity <= 0) {
                quantityErrorLabel.setText("La quantité doit être un nombre positif.");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            quantityErrorLabel.setText("Quantité invalide. Veuillez entrer un nombre valide.");
            isValid = false;
        }

        // Validation du prix
        try {
            double price = Double.parseDouble(priceText);
            if (price <= 0) {
                priceErrorLabel.setText("Le prix doit être un nombre positif.");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            priceErrorLabel.setText("Prix invalide. Veuillez entrer un nombre valide.");
            isValid = false;
        }

        // Si toutes les validations sont réussies, créer et enregistrer le produit
        if (isValid) {
            Product product = new Product(name, category, Integer.parseInt(quantityText), Double.parseDouble(priceText));
            try {
                inventoryService.addProduct(name, category, Integer.parseInt(quantityText), Double.parseDouble(priceText));

                // Enregistrer un log pour l'ajout de produit
                String username = UserSession.getInstance().getName();
                String logMessage = "Produit ajouté : " + product.getName() + ", Catégorie : " + product.getCategory();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                logService.addLog(username, logMessage, timestamp);

                // Afficher une alerte de succès
                showAlert("Succès", "Produit ajouté avec succès.", Alert.AlertType.INFORMATION);

                // Effacer les champs du formulaire après l'ajout
                nameField.clear();
                categoryField.clear();
                quantityField.clear();
                priceField.clear();
            } catch (RemoteException e) {
                e.printStackTrace();
                showAlert("Erreur", "Échec de l'ajout du produit : " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    // Annuler et revenir au tableau de bord
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
            showAlert("Erreur", "Échec du chargement du tableau de bord : " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Déconnexion de l'utilisateur
    @FXML
    public void logout(ActionEvent event) {
        String username = UserSession.getInstance().getName();
        LogoutHelper.logout(username, event);
    }

    // Méthode utilitaire pour afficher des alertes
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Méthode pour effacer les messages d'erreur
    private void clearErrorMessages() {
        nameErrorLabel.setText("");
        categoryErrorLabel.setText("");
        quantityErrorLabel.setText("");
        priceErrorLabel.setText("");
    }

    // Initialisation du contrôleur
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Optionnel : initialiser certaines données si nécessaire
    }

    // Chargement des logs dans une nouvelle interface
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
