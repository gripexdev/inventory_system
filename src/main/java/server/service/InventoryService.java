package server.service;

import server.models.Product;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InventoryService extends Remote {

    // Méthode pour ajouter un produit
    void addProduct(String name, String category, int quantity, double price) throws RemoteException;

    // Méthode pour mettre à jour un produit
    void updateProduct(int id, String name, String category, int quantity, double price) throws RemoteException;

    // Méthode pour supprimer un produit
    void deleteProduct(int id) throws RemoteException;

    // Méthode pour rechercher des produits
    List<Product> searchProducts(String query) throws RemoteException;
}
