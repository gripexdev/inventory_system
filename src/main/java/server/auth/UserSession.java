package server.auth;

public class UserSession {
    // Instance unique de la classe UserSession
    private static UserSession instance;

    // Nom de l'utilisateur connecté
    private String name;

    // Constructeur privé pour empêcher la création d'instances à l'extérieur de la classe
    private UserSession(){}

    // Méthode pour obtenir l'instance unique de UserSession
    public static UserSession getInstance(){
        if(instance == null){
            instance = new UserSession();
        }
        return instance;
    }

    // Getter pour le nom de l'utilisateur
    public String getName() {
        return name;
    }

    // Setter pour définir le nom de l'utilisateur
    public void setName(String name) {
        this.name = name;
    }
}
