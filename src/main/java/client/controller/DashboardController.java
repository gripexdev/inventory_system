package client.controller;

import client.util.LogoutHelper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import server.auth.UserSession;
import server.models.Product;
import server.service.InventoryService;
import server.service.LogService;
import server.service.LogServiceImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;

public class DashboardController {
    // Déclaration des colonnes de la table pour afficher les produits
    @FXML
    public TableColumn idColumn;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TableColumn<Product, String> nameColumn;
    @FXML
    private TableColumn<Product, String> categoryColumn;
    @FXML
    private TableColumn<Product, Integer> quantityColumn;
    @FXML
    private TableColumn<Product, Double> priceColumn;
    @FXML
    private TextField searchField; // Champ de recherche
    @FXML
    private Button addButton, updateButton, deleteButton, logoutButton; // Boutons d'action

    @FXML
    private TableColumn<Product, Void> actionColumn; // Colonne pour les actions (mise à jour et suppression)

    @FXML
    public Button logsButton; // Bouton pour afficher les logs

    private InventoryService inventoryService; // Service pour gérer l'inventaire
    private LogService logService; // Service pour gérer les logs

    // Constructeur pour initialiser les services via RMI
    public DashboardController() throws MalformedURLException, NotBoundException, RemoteException {
        inventoryService = (InventoryService) Naming.lookup("rmi://localhost:1099/InventoryService");
        logService = new LogServiceImpl();
    }

    @FXML
    public void initialize() {
        // Initialisation des colonnes de la table avec les données des produits
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Ajout de boutons d'action (mise à jour et suppression) pour chaque produit
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete"); // Bouton de suppression
            private final Button updateButton = new Button("Update"); // Bouton de mise à jour

            {
                // Style et fonctionnalité pour le bouton de suppression
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 5;");
                deleteButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    onDeleteProduct(product); // Action de suppression
                });

                // Style et fonctionnalité pour le bouton de mise à jour
                updateButton.setStyle("-fx-background-color: blue; -fx-text-fill: white; -fx-background-radius: 5;");
                updateButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    goToUpdateProduct(product); // Navigation vers la page de mise à jour
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Ajout des boutons dans une disposition horizontale
                    setGraphic(new HBox(5, deleteButton, updateButton));
                }
            }
        });

        loadProducts(); // Chargement initial des produits
    }

    // Fonction pour charger les produits dans la table
    private void loadProducts() {
        try {
            productsTable.getItems().clear();
            productsTable.getItems().addAll(inventoryService.searchProducts(""));

            // Ajout d'un log pour l'affichage des produits
            logService.addLog(UserSession.getInstance().getName(), "Affichage de la liste des produits", new Timestamp(System.currentTimeMillis()));

        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // Fonction pour afficher les logs
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

    // Fonction pour déconnecter l'utilisateur
    @FXML
    public void logout(ActionEvent event) {
        String username = UserSession.getInstance().getName();
        LogoutHelper.logout(username, event);
    }

    // Fonction pour effectuer une recherche en temps réel
    @FXML
    private void onSearchKeyReleased() {
        String query = searchField.getText();
        try {
            productsTable.getItems().clear();
            productsTable.getItems().addAll(inventoryService.searchProducts(query));

            // Ajout d'un log pour la recherche
            logService.addLog(UserSession.getInstance().getName(), "Recherche: " + query, new Timestamp(System.currentTimeMillis()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    // Fonction pour naviguer vers la page d'ajout de produit
    @FXML
    private void goToAddProduct(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/ressources/addProduct.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Fonction pour supprimer un produit
    @FXML
    private void onDeleteProduct(Product product) {
        if (product == null) return;

        // Affichage d'une boîte de dialogue de confirmation
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer Produit");
        alert.setHeaderText(null);
        alert.setContentText("Veuillez Confirmer la suppression du Produit avec le nom: " + product.getName());

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                inventoryService.deleteProduct(product.getId());
                logService.addLog(UserSession.getInstance().getName(),
                        "Produit Supprimé: " + product.getName(),
                        new Timestamp(System.currentTimeMillis()));
                loadProducts(); // Rafraîchissement de la liste des produits
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    // Fonction pour naviguer vers la page de mise à jour d'un produit
    @FXML
    private void goToUpdateProduct(Product product) {
        try {
            // Chargement de la page de mise à jour
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/client/ressources/updateProduct.fxml"));
            Parent root = loader.load();

            // Transmission des données du produit à mettre à jour
            UpdateProductController updateProductController = loader.getController();
            updateProductController.setProductDetails(product);

            // Navigation vers la page de mise à jour
            Stage stage = (Stage) productsTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
