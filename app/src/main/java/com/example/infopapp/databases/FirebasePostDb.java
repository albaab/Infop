package com.example.infopapp.databases;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.example.infopapp.entities.PostItem;
import com.example.infopapp.entities.Session;
import com.example.infopapp.ui.home_screens.fragments.home_fragment.PostAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.utils.Constants.HARD_SKILLS;
import static com.example.infopapp.utils.Constants.SESSION_FEEDBACK;

public class FirebasePostDb {
    private final static DatabaseReference EDACY_POSTS = FirebaseDatabase
            .getInstance().getReference("EDACY_POSTS");


//==========================================ATTRIBUTES==============================================

    public FirebasePostDb() {
    }// empty constructor

//=======================================DATABASE METHODS===========================================
    //---------------------------------UPLOAD A SESSION TO FIREBASE---------------------------------
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean uploadPostToFirebase(PostItem postItem) {

        if (postItem.getPost() != null) {
            DatabaseReference CHANNEL_REF = EDACY_POSTS.child(postItem.getChannel());


            CHANNEL_REF
                    .child(CHANNEL_REF.push().getKey())
                    .setValue(postItem, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            //todo callback the view of the ui
                        }
                    });

            return true;
        } else {
            //todo callback the view of the ui
            return false;
        }

    }

    //---------------------------------GET ALL POSTS FROM FIREBASE-------------------------------
    public void getAllPostFromFirebaseDb(String CHANNEL,
                                             final OnFirebasePostCallBack fbCallback){
        DatabaseReference CHANNEL_REF = EDACY_POSTS.child(CHANNEL);

         class sessionValueEventListener implements ValueEventListener {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            setValue(dataSnapshot);
                List<PostItem> postItems = new ArrayList<>();

                Log.d(TAG, "onDataChange: DEBUG -> GETTING ALL THE ");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    postItems.add(snapshot.getValue(PostItem.class));
                }
                fbCallback.getPostsFromDb(postItems);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: DEBUG -> CANNOT ACCESS THE DATA");
            }
        }
       sessionValueEventListener listener = new sessionValueEventListener();
        CHANNEL_REF.addValueEventListener(listener);
    }


//=======================================INTERFACE CALLBACK=========================================
    public interface OnFirebasePostCallBack {
        void getPostsFromDb(List<PostItem> postItems);
    }


}
