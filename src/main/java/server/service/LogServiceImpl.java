package server.service;

import server.dao.LogDao;

import java.sql.Timestamp;

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
}
