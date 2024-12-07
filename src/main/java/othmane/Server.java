package othmane;


import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {
    public static void main(String[] args) {
        try {
            // Start RMI registry
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI Registry started...");

            // Create and bind the service
            InventoryService service = new InventoryServiceImpl();
            Naming.rebind("rmi://localhost:1099/InventoryService", service);
            System.out.println("InventoryService is bound and running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


