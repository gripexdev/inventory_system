package server.models;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String name;
    private String email;
    private String password;

    // Constructeur avec paramètres
    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Constructeur par défaut
    public User() {

    }

    // Getter pour l'id
    public int getId() {
        return id;
    }

    // Setter pour l'id
    public void setId(int id) {
        this.id = id;
    }

    // Getter pour le nom
    public String getName() {
        return name;
    }

    // Setter pour le nom
    public void setName(String name) {
        this.name = name;
    }

    // Getter pour l'email
    public String getEmail() {
        return email;
    }

    // Setter pour l'email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter pour le mot de passe
    public String getPassword() {
        return password;
    }

    // Setter pour le mot de passe
    public void setPassword(String password) {
        this.password = password;
    }

    // Méthode toString pour afficher les détails de l'utilisateur
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
