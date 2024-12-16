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

    public Button manageProductsButton; // Bouton pour gérer les produits
    public Button logoutButton; // Bouton pour se déconnecter
    public Button settingsButton; // Bouton pour accéder aux paramètres
    public TextField searchField; // Champ de texte pour rechercher des logs
    public Button addProductButton; // Bouton pour ajouter un produit
    @FXML
    private Button logsButton; // Bouton pour afficher les logs

    @FXML
    private TableView<Log> logsTable; // Table pour afficher les logs

    @FXML
    private TableColumn<Log, Integer> logIdColumn; // Colonne pour l'identifiant du log

    @FXML
    private TableColumn<Log, String> logUserColumn; // Colonne pour le nom de l'utilisateur du log

    @FXML
    private TableColumn<Log, String> logActionColumn; // Colonne pour l'action enregistrée dans le log

    @FXML
    private TableColumn<Log, Timestamp> logCreatedAtColumn; // Colonne pour la date et l'heure du log

    private LogService logService; // Service pour gérer les logs

    /**
     * Constructeur pour initialiser le service des logs.
     */
    public LogController() throws MalformedURLException, NotBoundException, RemoteException {
        logService = new LogServiceImpl();
    }

    /**
     * Méthode appelée automatiquement lors de l'initialisation du contrôleur.
     * Configure les colonnes de la table et charge les logs.
     */
    @FXML
    public void initialize() {
        logIdColumn.setCellValueFactory(new PropertyValueFactory<>("id")); // Associe l'identifiant du log à la colonne
        logUserColumn.setCellValueFactory(new PropertyValueFactory<>("user")); // Associe l'utilisateur du log à la colonne
        logActionColumn.setCellValueFactory(new PropertyValueFactory<>("action")); // Associe l'action du log à la colonne
        logCreatedAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt")); // Associe la date/heure à la colonne

        loadLogs(); // Charge les données des logs dans la table
    }

    /**
     * Charge les logs depuis le service et les affiche dans la table.
     */
    @FXML
    private void loadLogs() {
        logsTable.getItems().clear(); // Vide la table avant de la recharger
        logsTable.getItems().addAll(logService.getLogs("")); // Ajoute tous les logs récupérés
        logService.addLog(UserSession.getInstance().getName(), "Affichage de la liste des logs", new Timestamp(System.currentTimeMillis())); // Enregistre une action de log
    }

    /**
     * Charge la page des produits (dashboard).
     *
     * @param event Action déclenchée par un bouton
     * @throws IOException En cas d'erreur lors du chargement de la vue
     */
    @FXML
    private void loadProducts(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/ressources/dashboard.fxml")); // Charge la vue du tableau de bord
        Parent root = fxmlLoader.load();

        DashboardController dashboardController = fxmlLoader.getController(); // Récupère le contrôleur du tableau de bord

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setFullScreen(false); // Désactive le plein écran
        stage.setResizable(false); // Empêche le redimensionnement de la fenêtre
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Déconnecte l'utilisateur actuel et redirige vers la page de connexion.
     *
     * @param event Action déclenchée par un bouton
     */
    @FXML
    public void logout(ActionEvent event) {
        try {
            // Récupère le nom de l'utilisateur en session
            String username = UserSession.getInstance().getName();
            UserSession.getInstance().setName(null);

            // Ajoute un log de déconnexion
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            logService.addLog(username, "Utilisateur déconnecté", timestamp);

            // Charge la vue de la page de connexion
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/ressources/login.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setFullScreen(false); // Désactive le plein écran
            stage.setResizable(false); // Empêche le redimensionnement de la fenêtre
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Affiche l'erreur dans la console
        }
    }
}
