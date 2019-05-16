package com.example.infopapp.db;

import android.app.Application;
import android.os.AsyncTask;

import com.example.infopapp.R;
import com.example.infopapp.entities.Session;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Session.class}, version = 1)
public abstract class SessionRoomDatabase extends RoomDatabase {

    public abstract SessionDao sessionDao();

    public static SessionRoomDatabase instance;

    public static synchronized SessionRoomDatabase getInstance(Application application) {

        if (instance == null) {
            instance = Room.databaseBuilder(application.getApplicationContext()
                    ,SessionRoomDatabase.class,"jobs_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //populate database
            new PopulateDdAsyncTask(instance).execute();
        }
    };

    private static class PopulateDdAsyncTask extends AsyncTask<Void,Void, Void>{

        private SessionDao sessionDao;

        PopulateDdAsyncTask(SessionRoomDatabase db) {
            this.sessionDao = db.sessionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            sessionDao.insert(new Session("Activities & Fragments", "April"
                                ,2, "9:00"));
            sessionDao.insert(new Session("FireBase Authentification", "April"
                    ,9, "9:00"));
            sessionDao.insert(new Session("Room Database & Live Data", "April"
                    ,16, "9:00"));
            sessionDao.insert(new Session("FireBase Database", "April"
                    ,16, "9:00"));
            sessionDao.insert(new Session("Architectures MVP & MVVM", "April"
                    ,23, "9:00"));
            sessionDao.insert(new Session("Rx Java", "April"
                    ,23, "9:00"));
            sessionDao.insert(new Session("Asynchronous Tasks", "April"
                    ,30, "9:00"));
            sessionDao.insert(new Session("Recycler View", "May"
                    ,7, "9:00"));
            sessionDao.insert(new Session("Revision", "May"
                    ,14, "9:00"));
            sessionDao.insert(new Session("Test Day", "May"
                    ,21, "9:00"));

            return null;
        }
    }
}

