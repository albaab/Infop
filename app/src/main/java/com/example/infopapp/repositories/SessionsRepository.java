package com.example.infopapp.repositories;

import android.app.Application;

import com.example.infopapp.databases.SessionDao;
import com.example.infopapp.databases.SessionRoomDatabase;
import com.example.infopapp.entities.Session;
import com.example.infopapp.utils.DeleteSessionAsyncTask;
import com.example.infopapp.utils.InsertSessionAsyncTask;
import com.example.infopapp.utils.UpdateSessionAsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class SessionsRepository {

    private SessionDao sessionDao;

    public SessionsRepository(Application application){
        SessionRoomDatabase sessionRoomDatabase = SessionRoomDatabase.getInstance(application);
        sessionDao = sessionRoomDatabase.sessionDao();
    }

    public void insertSession(Session session){
        new InsertSessionAsyncTask(sessionDao).execute(session);
    }

    public void updateSession(Session session){
        new UpdateSessionAsyncTask(sessionDao).execute(session);
    }

    public void deleteSession(Session session){
        new DeleteSessionAsyncTask(sessionDao).execute(session);
    }

    public LiveData<List<Session>> getAllSessions(){return sessionDao.getAllSessions();}




}
