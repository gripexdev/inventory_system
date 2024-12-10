package server.service;

import server.dao.UserDao;
import server.models.User;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class UserServiceImpl extends UnicastRemoteObject implements UserService {

    private UserDao userDao;

    public UserServiceImpl() throws RemoteException {
        userDao = new UserDao();
    }

    @Override
    public void addUser(String name, String email, String password) throws RemoteException {
        userDao.addUser(name, email, password);
    }

    @Override
    public User findUser(String email, String password) throws RemoteException {
        User user = userDao.findUser(email, password);
        return user;
    }
}
