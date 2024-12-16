package server;

import server.models.User;
import server.service.InventoryService;
import server.service.InventoryServiceImpl;
import server.service.UserService;
import server.service.UserServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main(String[] args) {
        try {
            // Création du registre RMI sur le port 1099
            LocateRegistry.createRegistry(1099);
            System.out.println("Registre RMI démarré...");

            // Création et enregistrement du service InventoryService dans le registre RMI
            InventoryService service = new InventoryServiceImpl();
            Naming.rebind("rmi://localhost:1099/InventoryService", service);
            System.out.println("InventoryService est lié et en cours d'exécution...");

            // Création et enregistrement du service UserService dans le registre RMI
            UserService userService = new UserServiceImpl();
            Naming.rebind("rmi://localhost:1099/UserService", userService);
            System.out.println("UserService est lié et en cours d'exécution...");

            // Recherche du service UserService dans le registre RMI
            UserService lookupUserService = (UserService) Naming.lookup("rmi://localhost:1099/UserService");

            // Test : ajout d'un utilisateur via le service UserService
            String name = "omar";
            String email = "omar@omar.com";
            String password = "omaromar";
            lookupUserService.addUser(name, email, password);  // Ajout de l'utilisateur
            System.out.println("Utilisateurs supprimés & Utilisateur créé !");
        } catch (Exception e) {
            // En cas d'erreur, affichage de la pile d'erreurs
            e.printStackTrace();
        }
    }
}
