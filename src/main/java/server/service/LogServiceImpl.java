package server.service;

import server.dao.LogDao;
import server.models.Log;

import java.sql.Timestamp;
import java.util.List;

public class LogServiceImpl implements LogService{

    private LogDao logDao;

    public LogServiceImpl() {
        logDao = new LogDao();
    }

    @Override
    public void addLog(String user, String action, Timestamp createdAt) {
        try{
            logDao.addLog(user, action, createdAt);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Log> getLogs(String query) {
        List<Log> logs = null;
        try{
            logs = logDao.getLogs(query);
        }catch (Exception e){
            e.printStackTrace();
        }
        return logs;
    }

}
