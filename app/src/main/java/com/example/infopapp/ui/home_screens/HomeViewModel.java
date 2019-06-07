package com.example.infopapp.ui.home_screens;

import android.app.Application;
import android.view.View;
import android.widget.ProgressBar;

import com.example.infopapp.entities.Cohort;
import com.example.infopapp.entities.PostItem;
import com.example.infopapp.entities.Session;
import com.example.infopapp.repositories.PostRepository;
import com.example.infopapp.repositories.SessionsRepository;
import com.example.infopapp.repositories.UserRepository;
import com.example.infopapp.ui.profile.ProfileView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class HomeViewModel extends AndroidViewModel implements ProfileView {


//==================================PRIVATE ATTRIBUTES==============================================
    private SessionsRepository repository;
    private UserRepository userRepo;
    private PostRepository postRepo;

//==================================CONSTRUCTOR=====================================================
    HomeViewModel(@NonNull Application application) {
        super(application);
        repository = new SessionsRepository(application);
        userRepo = new UserRepository(this);
        postRepo = new PostRepository();
    }


//==================================ROOM DATABASE METHOD=============================================
//    public void insertSessionData(Session session) {
//        repository.insertSession(session);
//    }
//
//    public void updateSessionData(Session session) {
//        repository.updateSession(session);
//    }
//
//    public void deleteSessionData(Session session) {
//        repository.deleteSession(session);
//    }
//
//    public LiveData<List<Session>> getAllSessionsData() {
//        return repository.getAllSessionsFromRoomDb();
//    }

//==================================FIREBASE DATABASE METHOD=============================================

    //------------------------------GET ALL COHORTS IN FIREBASE-------------------------------------
    public void getAllCohortsDataVm(final ProgressBar progressBar, final HomeViewModelCallbackListener listener) {
        progressBar.setVisibility(View.VISIBLE);
        userRepo.getAllCohorts(new UserRepository.UserRepoCallBackInterface() {
            @Override
            public void onCallBackRepo(List<Cohort> cohorts) {
                progressBar.setVisibility(View.INVISIBLE);
                listener.getListOfcohorts(cohorts);
            }
        });
    }

    //--------------------------GET ALL POSTS FOR THIS CHANNEL METHOD----------------------------------
    public void getAllPostsForTHisChannel(String CHANNEL, final HomeViewModelCallbackListener mlistener){
        postRepo.getPostFromFirebaseDb(CHANNEL, new PostRepository.OnPostRepoCallBack() {
            @Override
            public void getPostFromDb(List<PostItem> postItems) {
                mlistener.getListOfPosts(postItems);
            }
        });
    }

    //-------------------------UPLOAD A POST IN FIREBASE DATABASE-----------------------------------
    public void uploadPostToChannel(PostItem postItem){
        postRepo.insertPostItemToFirebaseDb(postItem);
    }


//=========================== HOME VIEW MODEL INTERFACE CALLBACK====================================


    public interface HomeViewModelCallbackListener{
        void getListOfcohorts(List<Cohort> cohorts);
        void getListOfPosts(List<PostItem> postItems);
    }


//===================================== INTERFACE VIEW METHODS======================================

    @Override
    public void setUploadProfilePic(boolean myBoolean, int visibility) {

    }

    @Override
    public void setUpdateUserInFirebaseDbStatus(boolean myBoolean) {

    }

    @Override
    public void setUploadUserInFirebaseDbStatus(boolean myBoolean) {

    }
}
