package server.service;

import server.models.Log;

import java.sql.Timestamp;
import java.util.List;

public interface LogService {

    // Méthode pour ajouter un log
    void addLog(String user, String action, Timestamp createdAt);

    // Méthode pour obtenir une liste de logs en fonction d'une requête
    List<Log> getLogs(String query);
}
