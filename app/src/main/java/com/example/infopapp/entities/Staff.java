package com.example.infopapp.entities;

import static com.example.infopapp.utils.Constants.STAFF;

public class Staff extends User {
    private String jobTitle;
    public Staff() {
        setUserType(STAFF);
    }

//===========================================getters================================================
    public String getJobTitle() {
        return jobTitle;
    }

//===========================================setters================================================
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;}
}
