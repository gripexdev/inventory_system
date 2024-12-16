package server.service;

import server.models.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {

    // Méthode pour ajouter un utilisateur
    void addUser(String name, String email, String password) throws RemoteException;

    // Méthode pour trouver un utilisateur par email et mot de passe
    User findUser(String email, String password) throws RemoteException;
}
