package com.example.infopapp.utils;

import android.os.AsyncTask;

import com.example.infopapp.databases.SessionDao;
import com.example.infopapp.entities.Session;

public class InsertSessionAsyncTask extends AsyncTask <Session, Void, Void>{
    private SessionDao sessionDao;

    public InsertSessionAsyncTask(SessionDao sessionDao) {
        this.sessionDao = sessionDao;
    }
    @Override
        protected Void doInBackground(Session... sessions) {
        sessionDao.insert(sessions[0]);
            return null;
        }
}
