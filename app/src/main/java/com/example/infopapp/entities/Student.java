package com.example.infopapp.entities;

import androidx.annotation.NonNull;

import static com.example.infopapp.utils.Constants.STUDENT;

public class Student extends User {

//==========================================ATTRIBUTES==============================================
    private int cohort = 0;
    private String specialization;
    private String resumeUrl;

//==========================================CONSTRUCTORS============================================
    // empty constructor
    public Student(){
        setUserType(STUDENT);
    }

    public Student(String firstName, String lastName) {
        super(firstName, lastName);
        setUserType(STUDENT);
    }

    //==========================================getters=============================================
    public int getCohort() {
        return cohort;
    }

    public String getResumeUrl() {
        return resumeUrl;
    }

    public String getSpecialization() {
        return specialization;
    }

    //===========================================setters============================================

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public void setCohort(int cohort) {
        this.cohort = cohort;
    }

    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }

    @NonNull
    @Override
    public String toString() {
        return getFirstName()+ " " + getLastName();
    }
}
