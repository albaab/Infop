package com.example.infopapp.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "sessions_table")
public class Session {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "session_title")
    private String sessionTitle;

    @ColumnInfo(name = "session_type")
    private int sessionType;

    @ColumnInfo(name = "session_instructor")
    private String sessionInstructor;

    @ColumnInfo(name = "session_description")
    private String sessionDescription;

    @ColumnInfo(name = "session_month")
    private String sessionMonth;

    @ColumnInfo(name = "session_day")
    private int sessionDay;

    @ColumnInfo(name = "session_hour")
    private String sessionHour;

    @ColumnInfo(name = "session_homework")
    private String sessionHomework;

    @ColumnInfo(name = "session_feedback")
    private Boolean sessionFeedback;

    public Session(){

    }
    public Session(String sessionTitle,
                   String sessionMonth, int sessionDay, String sessionHour){
        this.sessionTitle = sessionTitle;
        this.sessionMonth = sessionMonth;
        this.sessionDay = sessionDay;
        this.sessionHour = sessionHour;

    }


    public Session(String sessionTitle, int sessionType, String sessionInstructor,
                   String sessionDescription, String sessionDate, String sessionHomework,
                   Boolean sessionFeedback) {
        this.sessionTitle = sessionTitle;
        this.sessionType = sessionType;
        this.sessionInstructor = sessionInstructor;
        this.sessionDescription = sessionDescription;
        this.sessionMonth = sessionDate;
        this.sessionHomework = sessionHomework;
        this.sessionFeedback = sessionFeedback;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public int getSessionType() {
        return sessionType;
    }

    public void setSessionType(int sessionType) {
        this.sessionType = sessionType;
    }

    public String getSessionInstructor() {
        return sessionInstructor;
    }

    public void setSessionInstructor(String sessionInstructor) {
        this.sessionInstructor = sessionInstructor;
    }

    public String getSessionDescription() {
        return sessionDescription;
    }

    public void setSessionDescription(String sessionDescription) {
        this.sessionDescription = sessionDescription;
    }

    public String getSessionMonth() {
        return sessionMonth;
    }

    public void setSessionMonth(String sessionMonth) {
        this.sessionMonth = sessionMonth;
    }

    public int getSessionDay() {
        return sessionDay;
    }

    public void setSessionDay(int sessionDay) {
        this.sessionDay = sessionDay;
    }

    public String getSessionHour() {
        return sessionHour;
    }

    public void setSessionHour(String sessionHour) {
        this.sessionHour = sessionHour;
    }

    public String getSessionHomework() {
        return sessionHomework;
    }

    public void setSessionHomework(String sessionHomework) {
        this.sessionHomework = sessionHomework;
    }

    public Boolean getSessionFeedback() {
        return sessionFeedback;
    }

    public void setSessionFeedback(Boolean sessionFeedback) {
        this.sessionFeedback = sessionFeedback;
    }
}