package com.example.infopapp.repositories;

import android.app.Application;

import com.example.infopapp.databases.FirebasePostDb;
import com.example.infopapp.databases.FirebaseSessionDb;
import com.example.infopapp.databases.SessionDao;
import com.example.infopapp.databases.SessionRoomDatabase;
import com.example.infopapp.entities.PostItem;
import com.example.infopapp.entities.Session;
import com.example.infopapp.utils.DeleteSessionAsyncTask;
import com.example.infopapp.utils.InsertSessionAsyncTask;
import com.example.infopapp.utils.UpdateSessionAsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

import static com.example.infopapp.utils.Constants.FEEDBACK_REQUEST;
import static com.example.infopapp.utils.Constants.UPDATE_SESSION_REQUEST;

public class PostRepository {

    private FirebasePostDb firebasePostDb = new FirebasePostDb();


    public PostRepository() {
    }

    //======================================FIREBASE DATABASE QUERIES===================================
    public void getPostFromFirebaseDb(String CHANNEL, final OnPostRepoCallBack
            fb) {
        firebasePostDb.getAllPostFromFirebaseDb(CHANNEL, new FirebasePostDb.OnFirebasePostCallBack() {
            @Override
            public void getPostsFromDb(List<PostItem> postItems) {
                fb.getPostFromDb(postItems);
            }
        });
    }

    public void insertPostItemToFirebaseDb(PostItem postItem) {
        firebasePostDb.uploadPostToFirebase(postItem);
    }


    public interface OnPostRepoCallBack {
        void getPostFromDb(List<PostItem> postItems);
    }


}
