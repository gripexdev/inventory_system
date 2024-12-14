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
    private TextField searchField;
    @FXML
    private Button addButton, updateButton, deleteButton, logoutButton;

    @FXML
    private TableColumn<Product, Void> actionColumn;


    @FXML
    public Button logsButton;

    private InventoryService inventoryService;

    private LogService logService;

    public DashboardController() throws MalformedURLException, NotBoundException, RemoteException {
        inventoryService = (InventoryService) Naming.lookup("rmi://localhost:1099/InventoryService");
        logService = new LogServiceImpl();
    }

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // add the Delete button for every product
        actionColumn.setCellFactory(col -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-background-radius: 5;");
                deleteButton.setOnAction(event -> {
                    Product product = getTableView().getItems().get(getIndex());
                    onDeleteProduct(product); // Call delete method
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });


        loadProducts();
    }

    private void loadProducts() {
        try {
            productsTable.getItems().clear();
            productsTable.getItems().addAll(inventoryService.searchProducts(""));

            logService.addLog(UserSession.getInstance().getName(), "Affichage de la liste des produits", new Timestamp(System.currentTimeMillis()));

        } catch (RemoteException e) {
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
//        logController.initialize();
    }



    @FXML
    public void logout(ActionEvent event) {
        String username = UserSession.getInstance().getName();
        LogoutHelper.logout(username, event);
    }

    @FXML
    private void onSearchKeyReleased() {
        String query = searchField.getText();
        try {
            productsTable.getItems().clear();
            productsTable.getItems().addAll(inventoryService.searchProducts(query));

            // Log the search action
            logService.addLog(UserSession.getInstance().getName(), "Search performed: " + query, new Timestamp(System.currentTimeMillis()));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

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

    @FXML
    private void onDeleteProduct(Product product) {
        if (product == null) return;

        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Product");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete product with name: " + product.getName() + "?");

        if (alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try {
                inventoryService.deleteProduct(product.getId());
                logService.addLog(UserSession.getInstance().getName(),
                        "Deleted product: " + product.getName(),
                        new Timestamp(System.currentTimeMillis()));
                loadProducts(); // Refresh product list
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }


}
