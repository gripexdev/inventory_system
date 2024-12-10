package client.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import server.models.Product;
import server.service.InventoryService;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class DashboardController {
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
    private Button addButton, updateButton, deleteButton, logoutButton;

    private InventoryService inventoryService;

    public DashboardController() throws MalformedURLException, NotBoundException, RemoteException {
        inventoryService = (InventoryService) Naming.lookup("rmi://localhost:1099/InventoryService");
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadProducts();
    }

    private void loadProducts() {
        try {
            productsTable.getItems().clear();
            productsTable.getItems().addAll(inventoryService.searchProducts(""));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
