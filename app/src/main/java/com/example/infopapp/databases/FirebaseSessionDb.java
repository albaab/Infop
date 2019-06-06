package com.example.infopapp.databases;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.example.infopapp.entities.Session;
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

public class FirebaseSessionDb {
    private final static DatabaseReference MODULE_REF = FirebaseDatabase
            .getInstance().getReference("Modules");


//==========================================ATTRIBUTES==============================================

    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public FirebaseSessionDb() {
    }// empty constructor

//=======================================DATABASE METHODS===========================================
    //---------------------------------UPLOAD A SESSION TO FIREBASE---------------------------------
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean uploadSessionToFirebase(Session session) {

        if (session.getSessionModule() != null) {
            DatabaseReference SESSION_MODULE_REF = MODULE_REF.child(session.getSessionModule());
            DatabaseReference SESSION_TYPE_REF = SESSION_MODULE_REF.child(session.getSessionType());

            session.setId(Objects.requireNonNull(SESSION_TYPE_REF.push().getKey()));

            SESSION_TYPE_REF
                    .child(session.getId())
                    .setValue(session, new DatabaseReference.CompletionListener() {
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
    //---------------------------------UPDATE A SESSION TO FIREBASE---------------------------------
    public void updateSessionToFirebase(Session session){

        if (session.getSessionModule() != null) {
            DatabaseReference SESSION_MODULE_REF = MODULE_REF.child(session.getSessionModule());
            DatabaseReference SESSION_TYPE_REF = SESSION_MODULE_REF.child(session.getSessionType());
            DatabaseReference SESSION_REF = SESSION_TYPE_REF.child(session.getId());
            SESSION_REF
                    .setValue(session, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            //todo callback the view of the ui
                        }
                    });

        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public void uploadFeedbackSessionToFirebase(Session session){

        if (session.getSessionModule() != null) {
            DatabaseReference SESSION_MODULE_REF = MODULE_REF.child(session.getSessionModule());
            DatabaseReference SESSION_TYPE_REF = SESSION_MODULE_REF.child(session.getSessionType());
            DatabaseReference FEEDBACK_REF = SESSION_TYPE_REF
                    .child(session.getId())
                    .child(SESSION_FEEDBACK)
                    .child(user.getUid());

            FEEDBACK_REF
                    .setValue(session.getSessionstudentFeedbacks(),
                            new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            //todo callback the view of the ui
                        }
                    });
        }
    }
    //---------------------------------GET ALL SESSIONS FROM FIREBASE-------------------------------
    public void getAllSessionsFromFirebaseDb(String SESSION_TYPE,
                                             final OnFirebaseSessionCallBack fbCallback){
        DatabaseReference SESSION_MODULE_REF = MODULE_REF.child(HARD_SKILLS);
        DatabaseReference SESSION_TYPE_REF = SESSION_MODULE_REF.child(SESSION_TYPE);

         class sessionValueEventListener implements ValueEventListener {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//            setValue(dataSnapshot);
                List<Session> sessions = new ArrayList<>();

                Log.d(TAG, "onDataChange: DEBUG -> GETTING ALL THE ");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    sessions.add(snapshot.getValue(Session.class));
                }
                fbCallback.getSessionsFromDb(sessions);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: DEBUG -> CANNOT ACCESS THE DATA");
            }
        }
       sessionValueEventListener listener = new sessionValueEventListener();
        SESSION_TYPE_REF.addValueEventListener(listener);
    }


//=======================================INTERFACE CALLBACK=========================================
    public interface OnFirebaseSessionCallBack {
        void getSessionsFromDb(List<Session> sessionsList);
    }


}
