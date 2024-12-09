package server.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote {
    void addUser(String name, String email, String password) throws RemoteException;
}
