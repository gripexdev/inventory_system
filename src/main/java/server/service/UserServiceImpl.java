package server.service;

import server.dao.UserDao;
import server.models.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    private UserDao userDao;

    // Constructeur pour initialiser le UserDao
    public UserServiceImpl() throws RemoteException {
        userDao = new UserDao();  // Création de l'objet UserDao pour l'accès à la base de données
    }

    // Implémentation de la méthode pour ajouter un utilisateur
    @Override
    public void addUser(String name, String email, String password) throws RemoteException {
        userDao.addUser(name, email, password);  // Appel à la méthode d'ajout dans UserDao
    }

    // Implémentation de la méthode pour trouver un utilisateur par email et mot de passe
    @Override
    public User findUser(String email, String password) throws RemoteException {
        User user = userDao.findUser(email, password);  // Recherche de l'utilisateur dans la base de données
        return user;  // Retourne l'utilisateur trouvé
    }
}
