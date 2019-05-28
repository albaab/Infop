package com.example.infopapp.ui.home_screens;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.infopapp.db.FirebaseDb;
import com.example.infopapp.entities.Cohort;
import com.example.infopapp.entities.Session;
import com.example.infopapp.entities.User;
import com.example.infopapp.repositories.SessionsRepository;
import com.example.infopapp.repositories.UserRepository;
import com.example.infopapp.ui.home_screens.fragments.portfolios.PortoFolioAdapter;
import com.example.infopapp.ui.profile.ProfileView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

public class HomeViewModel extends AndroidViewModel implements ProfileView {

    private SessionsRepository repository;
    private UserRepository userRepo;


    HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new SessionsRepository(application);
        userRepo = new UserRepository(this);
    }

    public void insertSessionData(Session session) {
        repository.insertSession(session);
    }

    public void updateSessionData(Session session) {
        repository.updateSession(session);
    }

    public void deleteSessionData(Session session) {
        repository.deleteSession(session);
    }

    public LiveData<List<Session>> getAllSessionsData() {
        return repository.getAllSessions();
    }

    public void getAllCohortsDataVm(final ProgressBar progressBar, final HomeViewModelCallbackListener listener) {
        progressBar.setVisibility(View.VISIBLE);
        userRepo.getAllCohorts(new UserRepository.UserRepoCallBackInterface() {
            @Override
            public void onCallBackRepo(List<Cohort> cohorts) {
                progressBar.setVisibility(View.INVISIBLE);
                listener.OnHomeViewModelCallback(cohorts);
            }
        });
    }


    @Override
    public void setUploadProfilePic(boolean myBoolean, int visibility) {

    }

    @Override
    public void setUpdateUserInFirebaseDbStatus(boolean myBoolean) {

    }

    public interface HomeViewModelCallbackListener{
        void OnHomeViewModelCallback(List<Cohort> cohorts);
    }

}
