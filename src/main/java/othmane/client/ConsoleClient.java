package othmane.client;




import othmane.InventoryService;
import othmane.Product;

import java.rmi.Naming;
import java.util.Scanner;

public class ConsoleClient {
    public static void main(String[] args) {
        try {
            InventoryService service = (InventoryService) Naming.lookup("rmi://localhost:1099/InventoryService");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("1. Add Product");
                System.out.println("2. Update Product");
                System.out.println("3. Delete Product");
                System.out.println("4. Search Products");
                System.out.println("5. Exit");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        System.out.println("Enter name, category, quantity, price:");
                        String name = scanner.next();
                        String category = scanner.next();
                        int quantity = scanner.nextInt();
                        double price = scanner.nextDouble();
                        service.addProduct(name, category, quantity, price);
                        break;
                    case 2:
                        System.out.println("Enter id, name, category, quantity, price:");
                        int id = scanner.nextInt();
                        name = scanner.next();
                        category = scanner.next();
                        quantity = scanner.nextInt();
                        price = scanner.nextDouble();
                        service.updateProduct(id, name, category, quantity, price);
                        break;
                    case 3:
                        System.out.println("Enter id to delete:");
                        id = scanner.nextInt();
                        service.deleteProduct(id);
                        break;
                    case 4:
                        System.out.println("Enter search query:");
                        String query = scanner.next();
                        for (Product product : service.searchProducts(query)) {
                            System.out.println(product);
                        }
                        break;
                    case 5:
                        System.exit(0);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

