package server;

import server.service.InventoryService;
import server.service.InventoryServiceImpl;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main(String[] args) {
        try{
            LocateRegistry.createRegistry(1099);
            System.out.println("RMI Registry started...");

            InventoryService service = new InventoryServiceImpl();
            Naming.rebind("rmi://localhost:1099/InventoryService", service);
            System.out.println("InventoryService is bound and running...");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}