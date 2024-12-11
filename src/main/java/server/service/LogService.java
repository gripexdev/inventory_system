package server.service;

import java.sql.Timestamp;

public interface LogService {

    void addLog(String user, String action, Timestamp createdAt);
}
