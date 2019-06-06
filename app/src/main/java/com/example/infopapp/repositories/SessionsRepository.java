package com.example.infopapp.repositories;

import android.app.Application;

import com.example.infopapp.databases.FirebaseSessionDb;
import com.example.infopapp.databases.SessionDao;
import com.example.infopapp.databases.SessionRoomDatabase;
import com.example.infopapp.entities.Session;
import com.example.infopapp.utils.DeleteSessionAsyncTask;
import com.example.infopapp.utils.InsertSessionAsyncTask;
import com.example.infopapp.utils.UpdateSessionAsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

import static com.example.infopapp.utils.Constants.FEEDBACK_REQUEST;
import static com.example.infopapp.utils.Constants.UPDATE_SESSION_REQUEST;

public class SessionsRepository {

    private SessionDao sessionDao;
    private FirebaseSessionDb firebaseSessionDb = new FirebaseSessionDb();


    public SessionsRepository(Application application) {
        SessionRoomDatabase sessionRoomDatabase = SessionRoomDatabase.getInstance(application);
        sessionDao = sessionRoomDatabase.sessionDao();
    }

    //=======================================ROOM DATABASE QUERIES======================================
    public void insertSession(Session session) {
        new InsertSessionAsyncTask(sessionDao).execute(session);
    }

    public void updateSession(Session session) {
        new UpdateSessionAsyncTask(sessionDao).execute(session);
    }

    public void deleteSession(Session session) {
        new DeleteSessionAsyncTask(sessionDao).execute(session);
    }

    public LiveData<List<Session>> getAllSessionsFromRoomDb() {
        return sessionDao.getAllSessions();
    }


    //======================================FIREBASE DATABASE QUERIES===================================
    public void getSessionsFromFirebaseDb(String MODULE, final OnRepoSessionCallBack
            fb) {
//        LiveData<DataSnapshot> liveData = new FirebaseSessionDb(MODULE_REF.child(MODULE));
        //user a Mediator live data to map the live data with the list of sessions on a background thread
//        final MediatorLiveData<List<Session>> listMediatorLiveData = new MediatorLiveData<>();
//
//        listMediatorLiveData.addSource(liveData, new Observer<DataSnapshot>() {
//            @Override
//            public void onChanged(final DataSnapshot dataSnapshot) {
//                final List<Session> sessions = new ArrayList<>();
//                if(dataSnapshot!=null){
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                                sessions.add(snapshot.getValue(Session.class));
//                            }
//                            listMediatorLiveData.postValue(sessions);
//                            fb.getSessionsFromDb(listMediatorLiveData);
//                        }
//                    }).start();
//                }else{
//                    listMediatorLiveData.setValue(null);
//                }
//            }
//        });
        firebaseSessionDb.getAllSessionsFromFirebaseDb(MODULE, new FirebaseSessionDb.OnFirebaseSessionCallBack() {
            @Override
            public void getSessionsFromDb(List<Session> sessionsList) {
                fb.getSessionsFromDb(sessionsList);
            }
        });
    }

    public boolean insertSessionToFirebaseDb(Session session) {
        return new FirebaseSessionDb().uploadSessionToFirebase(session);
    }


    public void updateSessionInFirebaseDb(Session session, int REQUEST) {
        switch (REQUEST) {
            case FEEDBACK_REQUEST:
                firebaseSessionDb.uploadFeedbackSessionToFirebase(session);
                break;
            case UPDATE_SESSION_REQUEST:
                firebaseSessionDb.updateSessionToFirebase(session);
                break;
            default:
                firebaseSessionDb.updateSessionToFirebase(session);
                break;
        }
    }
//======================================REPOSITORY INTERFACEC CALLBACK==============================

    public interface OnRepoSessionCallBack {
        void getSessionsFromDb(List<Session> sessionsList);
    }


}
