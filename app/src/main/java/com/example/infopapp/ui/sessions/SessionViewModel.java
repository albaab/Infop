package com.example.infopapp.ui.sessions;

import android.app.Application;

import com.example.infopapp.entities.Session;
import com.example.infopapp.repositories.SessionsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class SessionViewModel extends AndroidViewModel {
    private SessionsRepository repo;

    public SessionViewModel(@NonNull Application application) {
        super(application);
        repo = new SessionsRepository(application);
    }
    public void insertEntry(Session session){
        repo.insertSession(session);
    }
    public void updateEntry(Session session ){
        repo.updateSession(session);
    }
    public void deleteEntry(Session session ){
        repo.deleteSession(session);
    }

    public LiveData<List<Session>> getAllJobsFromDatabase (){
        return repo.getAllSessions();
    }
}
