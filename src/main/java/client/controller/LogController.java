package client.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import server.auth.UserSession;
import server.models.Log;
import server.service.LogService;
import server.service.LogServiceImpl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;

public class LogController {

    public Button manageProductsButton;
    public Button logoutButton;
    public Button settingsButton;
    public TextField searchField;
    public Button addProductButton;
    @FXML
    private Button logsButton;

    @FXML
    private TableView<Log> logsTable;

    @FXML
    private TableColumn<Log, Integer> logIdColumn;

    @FXML
    private TableColumn<Log, String> logUserColumn;

    @FXML
    private TableColumn<Log, String> logActionColumn;

    @FXML
    private TableColumn<Log, Timestamp> logCreatedAtColumn;


    private LogService logService;

    public LogController() throws MalformedURLException, NotBoundException, RemoteException {
        logService = new LogServiceImpl();
    }

    @FXML
    public void initialize() {
        logIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        logUserColumn.setCellValueFactory(new PropertyValueFactory<>("user"));
        logActionColumn.setCellValueFactory(new PropertyValueFactory<>("action"));
        logCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

        loadLogs();
    }

    @FXML
    private void loadLogs() {
        logsTable.getItems().clear();
        logsTable.getItems().addAll(logService.getLogs(""));
        logService.addLog(UserSession.getInstance().getName(), "Affichage de la liste des logs", new Timestamp(System.currentTimeMillis()));
    }


    @FXML
    private void loadProducts(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/ressources/dashboard.fxml"));
        Parent root = fxmlLoader.load();

        DashboardController dashboardController = fxmlLoader.getController();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setFullScreen(false);
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void logout(ActionEvent event) {
        try {
            String username = UserSession.getInstance().getName();
            UserSession.getInstance().setName(null);

            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logService.addLog(username, "User logged out", timestamp);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/ressources/login.fxml"));
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
    }
}
