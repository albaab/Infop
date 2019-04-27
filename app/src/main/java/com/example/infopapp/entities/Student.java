package com.example.infopapp.utils;

public class Student extends User {
    private String userType, cohort;

    public Student(String firstName, String lastName, String email, String password, String userType) {
        super(firstName, lastName, email, password);
        this.userType = userType;
    }
    public Student (String firstName, String lastName, String cohort){
        super(firstName, lastName);
        this.cohort = cohort;
    }
}
