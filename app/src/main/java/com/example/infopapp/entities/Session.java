package com.example.infopapp.entities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sessions_table")
public class Session implements Parcelable {

    //=======================================ATTRIBUTES=================================================
    @PrimaryKey
    @NonNull
    private String id = "";
    @ColumnInfo(name = "session_title")
    private String sessionTitle;

    @ColumnInfo(name = "session_type")
    private String sessionType;

    @ColumnInfo(name = "session_module")
    private String sessionModule;

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

    @ColumnInfo(name = "session_resources")
    private String sessionResources;

    @ColumnInfo(name = "session_feedbacks")
    private Boolean isFeedbackComplete;

    @ColumnInfo(name = "student_feedback")
    private String sessionstudentFeedbacks;

    //=======================================CONSTRUCTORS===============================================
    public Session() {


    }

    public Session(String sessionTitle,
                   String sessionMonth, int sessionDay, String sessionHour) {
        this.sessionTitle = sessionTitle;
        this.sessionMonth = sessionMonth;
        this.sessionDay = sessionDay;
        this.sessionHour = sessionHour;

    }


    public Session(String sessionTitle, String sessionType, String sessionInstructor,
                   String sessionDescription, String sessionDate, String sessionHomework
            , String sessionResources, Boolean isFeedbackComplete) {
        this.sessionTitle = sessionTitle;
        this.sessionType = sessionType;
        this.sessionInstructor = sessionInstructor;
        this.sessionDescription = sessionDescription;
        this.sessionMonth = sessionDate;
        this.sessionHomework = sessionHomework;
        this.isFeedbackComplete = isFeedbackComplete;
        this.sessionResources = sessionResources;
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    protected Session(Parcel in) {
        id = Objects.requireNonNull(in.readString());
        sessionTitle = in.readString();
        sessionType = in.readString();
        sessionModule = in.readString();
        sessionInstructor = in.readString();
        sessionDescription = in.readString();
        sessionMonth = in.readString();
        sessionDay = in.readInt();
        sessionHour = in.readString();
        sessionHomework = in.readString();
        sessionResources = in.readString();
        byte tmpIsFeedbackComplete = in.readByte();
        isFeedbackComplete = tmpIsFeedbackComplete == 0 ? null : tmpIsFeedbackComplete == 1;
    }

    public static final Creator<Session> CREATOR = new Creator<Session>() {
        @Override
        public Session createFromParcel(Parcel in) {
            return new Session(in);
        }

        @Override
        public Session[] newArray(int size) {
            return new Session[size];
        }
    };

    public String getSessionResources() {
        return sessionResources;
    }


    //========================================SETTERS AND GETTERS=======================================


    public String getSessionstudentFeedbacks() {
        return sessionstudentFeedbacks;
    }

    public void setSessionstudentFeedbacks(String sessionstudentFeedbacks) {
        this.sessionstudentFeedbacks = sessionstudentFeedbacks;
    }

    public void setSessionResources(String sessionResources) {
        this.sessionResources = sessionResources;
    }

    public String getSessionModule() {
        return sessionModule;
    }

    public void setSessionModule(String sessionModule) {
        this.sessionModule = sessionModule;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSessionTitle() {
        return sessionTitle;
    }

    public void setSessionTitle(String sessionTitle) {
        this.sessionTitle = sessionTitle;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
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

    public Boolean getIsFeedbackComplete() {
        return isFeedbackComplete;
    }

    public void setIsFeedbackComplete(Boolean isFeedbackComplete) {
        this.isFeedbackComplete = isFeedbackComplete;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(sessionTitle);
        dest.writeString(sessionType);
        dest.writeString(sessionModule);
        dest.writeString(sessionInstructor);
        dest.writeString(sessionDescription);
        dest.writeString(sessionMonth);
        dest.writeInt(sessionDay);
        dest.writeString(sessionHour);
        dest.writeString(sessionHomework);
        dest.writeString(sessionResources);
        dest.writeByte((byte) (isFeedbackComplete == null ? 0 : isFeedbackComplete ? 1 : 2));
    }
}