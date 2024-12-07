package othmane;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;

public class InventoryServiceImpl extends UnicastRemoteObject implements InventoryService {
    private ProductDAO productDAO;

    protected InventoryServiceImpl() throws RemoteException {
        productDAO = new ProductDAO();
    }

    @Override
    public void addProduct(String name, String category, int quantity, double price) throws RemoteException {
        productDAO.addProduct(name, category, quantity, price);
    }

    @Override
    public void updateProduct(int id, String name, String category, int quantity, double price) throws RemoteException {
        productDAO.updateProduct(id, name, category, quantity, price);
    }

    @Override
    public void deleteProduct(int id) throws RemoteException {
        productDAO.deleteProduct(id);
    }

    @Override
    public List<Product> searchProducts(String query) throws RemoteException {
        return productDAO.searchProducts(query);
    }
}

