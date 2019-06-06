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

    //==============================ROOM DATABASE QUERIES===========================================
    public void insertRoomEntry(Session session){
        repo.insertSession(session);
    }
    public void updateRoomEntry(Session session ){
        repo.updateSession(session);
    }
    public void deleteRoomEntry(Session session ){
        repo.deleteSession(session);
    }

    public LiveData<List<Session>> getAllSessionsFromRoomDatabase(){
        return repo.getAllSessionsFromRoomDb();
    }

    //==============================FIREBASE DATABASE QUERIES=======================================
    public void getAllSessionsFromFirebase(String MODULE, final OnSessionModelCallBack os){
         repo.getSessionsFromFirebaseDb(MODULE, new SessionsRepository.OnRepoSessionCallBack() {
            @Override
            public void getSessionsFromDb(List<Session> sessionsList) {
                os.getSessionList(sessionsList);
            }
        });
    }
    public void uploadSessiontoFirbaseDb(Session session){
        repo.insertSessionToFirebaseDb(session);
    }
    public void updateSessiontoFirebaseDb(Session session,int REQUEST){
         repo.updateSessionInFirebaseDb(session,REQUEST);
    }
    public interface OnSessionModelCallBack{
        void getSessionList(List<Session> sessions);
    }
}
