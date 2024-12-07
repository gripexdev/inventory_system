package othmane;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface InventoryService extends Remote {
    void addProduct(String name, String category, int quantity, double price) throws RemoteException;
    void updateProduct(int id, String name, String category, int quantity, double price) throws RemoteException;
    void deleteProduct(int id) throws RemoteException;
    List<Product> searchProducts(String query) throws RemoteException;
}

