package server.service;

import server.dao.LogDao;
import server.models.Log;

import java.sql.Timestamp;
import java.util.List;

public class LogServiceImpl implements LogService {

    private LogDao logDao;

    // Constructeur qui initialise l'objet LogDao
    public LogServiceImpl() {
        logDao = new LogDao();
    }

    // Méthode pour ajouter un log
    @Override
    public void addLog(String user, String action, Timestamp createdAt) {
        try {
            // Appel de la méthode pour ajouter un log dans la base de données
            logDao.addLog(user, action, createdAt);
        } catch (Exception e) {
            // Affichage de l'exception en cas d'erreur
            e.printStackTrace();
        }
    }

    // Méthode pour obtenir les logs en fonction d'une requête
    @Override
    public List<Log> getLogs(String query) {
        List<Log> logs = null;
        try {
            // Appel de la méthode pour récupérer les logs de la base de données
            logs = logDao.getLogs(query);
        } catch (Exception e) {
            // Affichage de l'exception en cas d'erreur
            e.printStackTrace();
        }
        return logs;
    }

}
