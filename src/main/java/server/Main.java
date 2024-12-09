package server;

import server.service.InventoryService;
import server.service.InventoryServiceImpl;
import server.service.UserService;
import server.service.UserServiceImpl;

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

            UserService userService = new UserServiceImpl();
            Naming.rebind("rmi://localhost:1099/UserService", userService);
            System.out.println("UserService is bound and running...");


            UserService lookupUserService = (UserService) Naming.lookup("rmi://localhost:1099/UserService");
            String name = "omar";
            String email = "omar@omar.com";
            String password = "omaromar";
            lookupUserService.addUser(name, email, password);
            System.out.println("Users cleared & User Created !");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}