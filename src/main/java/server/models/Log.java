package server.models;

import java.util.Date;

public class Log {

    private int id;
    private String user;
    private String action;
    private Date createdAt;

    // Constructeur avec paramètres
    public Log(int id, String user, String action, Date createdAt) {
        this.id = id;
        this.user = user;
        this.action = action;
        this.createdAt = createdAt;
    }

    // Constructeur par défaut
    public Log() {
    }

    // Getter pour l'id
    public int getId() {
        return id;
    }

    // Setter pour l'id
    public void setId(int id) {
        this.id = id;
    }

    // Getter pour l'utilisateur
    public String getUser() {
        return user;
    }

    // Setter pour l'utilisateur
    public void setUser(String user) {
        this.user = user;
    }

    // Getter pour l'action
    public String getAction() {
        return action;
    }

    // Setter pour l'action
    public void setAction(String action) {
        this.action = action;
    }

    // Getter pour la date de création
    public Date getCreatedAt() {
        return createdAt;
    }

    // Setter pour la date de création
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Méthode toString pour afficher les détails du log
    @Override
    public String toString() {
        return "Log{" +
                "id=" + id +
                ", user='" + user + '\'' +
                ", action='" + action + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
