package server.service;

import server.models.Log;

import java.sql.Timestamp;
import java.util.List;

public interface LogService {

    void addLog(String user, String action, Timestamp createdAt);
    List<Log> getLogs(String query);

}
