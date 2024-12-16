package server.models;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private String category;
    private int quantity;
    private double price;

    // Constructeur par défaut
    public Product() {
    }

    // Constructeur avec paramètres
    public Product(int id, String name, String category, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    // Constructeur sans l'id (lors de la création d'un nouveau produit)
    public Product(String name, String category, int quantity, double price) {
        this.name = name;
        this.category = category;
        this.quantity = quantity;
        this.price = price;
    }

    // Getter pour l'id
    public int getId() {
        return id;
    }

    // Setter pour l'id
    public void setId(int id) {
        this.id = id;
    }

    // Getter pour le nom du produit
    public String getName() {
        return name;
    }

    // Setter pour le nom du produit
    public void setName(String name) {
        this.name = name;
    }

    // Getter pour la catégorie
    public String getCategory() {
        return category;
    }

    // Setter pour la catégorie
    public void setCategory(String category) {
        this.category = category;
    }

    // Getter pour la quantité
    public int getQuantity() {
        return quantity;
    }

    // Setter pour la quantité
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter pour le prix
    public double getPrice() {
        return price;
    }

    // Setter pour le prix
    public void setPrice(double price) {
        this.price = price;
    }

    // Méthode toString pour afficher les détails du produit
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
