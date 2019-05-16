package com.example.infopapp.ui.home_screens;

import android.app.Application;

import com.example.infopapp.entities.Session;
import com.example.infopapp.repositories.SessionsRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HomeViewModel extends AndroidViewModel {

    private SessionsRepository repository;


    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new SessionsRepository(application);
    }

    void insertSessionData(Session session){repository.insertSession(session);}
    void updateSessionData(Session session){repository.updateSession(session);}
    void deleteSessionData(Session session){repository.deleteSession(session);}
    LiveData<List<Session>> getAllSessionsData(){return repository.getAllSessions();}
}
