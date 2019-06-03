package com.example.infopapp.databases;

import com.example.infopapp.entities.Session;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SessionDao {
    @Insert
    void insert(Session session);

    @Update
    void update(Session session);

    @Delete
    void delete(Session session);

    @Query("SELECT * FROM sessions_table ORDER BY id ASC")
    LiveData<List<Session>> getAllSessions();

}
