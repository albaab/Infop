package com.example.infopapp.databases;

import android.app.Application;
import android.os.AsyncTask;

import com.example.infopapp.entities.Session;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.example.infopapp.utils.Constants.MOBILE_DEV;

@Database(entities = {Session.class}, version = 1)
public abstract class SessionRoomDatabase extends RoomDatabase {

    public abstract SessionDao sessionDao();
    private static SessionRoomDatabase instance;

    public static synchronized SessionRoomDatabase getInstance(Application application) {

        if (instance == null) {
            instance = Room.databaseBuilder(application.getApplicationContext()
                    , SessionRoomDatabase.class, "jobs_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //populate database
            new PopulateDdAsyncTask(instance).execute();
        }
    };

    private static class PopulateDdAsyncTask extends AsyncTask<Void, Void, Void> {

        private SessionDao sessionDao;

        PopulateDdAsyncTask(SessionRoomDatabase db) {
            this.sessionDao = db.sessionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            Session activitiesSession = new Session("Activities & Fragments", "April"
                    , 2, "9:00");
            activitiesSession.setSessionDescription("This course introduces you to the design and" +
                    " implementation of Android applications for mobile devices. You will develop " +
                    "an app from scratch, assuming a basic knowledge of Java, and learn how to set " +
                    "up Android Studio, work with various Activities and create simple user " +
                    "interfaces to make your apps run smoothly.");

            activitiesSession.setSessionHomework("Développer une app  qui permet á l’utilisateur de" +
                    " remplir un formulaire pour créér son CV. L’utilisateur aura aussi l’option de" +
                    " visualiser son CV et les offres d’emploi disponibles. Utiliser: \n" +
                    "Firebase Auth - Room Database - Fragments - Picasso ou Glide - RecyclerView ");

            activitiesSession.setSessionResources("www.edacy.com");

            activitiesSession.setSessionType(MOBILE_DEV);

            sessionDao.insert(activitiesSession);

            sessionDao.insert(new Session("Shared Preferences", "April"
                    , 2, "9:00"));
            sessionDao.insert(new Session("FireBase Authentification", "April"
                    , 9, "9:00"));
            sessionDao.insert(new Session("Room Database & Live Data", "April"
                    , 16, "9:00"));
            sessionDao.insert(new Session("FireBase Database", "April"
                    , 16, "9:00"));
            sessionDao.insert(new Session("Architectures MVP & MVVM", "April"
                    , 23, "9:00"));
            sessionDao.insert(new Session("Rx Java", "April"
                    , 23, "9:00"));
            sessionDao.insert(new Session("Asynchronous Tasks", "April"
                    , 30, "9:00"));
            sessionDao.insert(new Session("Recycler View", "May"
                    , 7, "9:00"));
            sessionDao.insert(new Session("Revision", "May"
                    , 14, "9:00"));
            sessionDao.insert(new Session("Test Day", "May"
                    , 21, "9:00"));

            return null;
        }
    }

}