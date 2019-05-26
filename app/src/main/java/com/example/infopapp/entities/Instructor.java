package com.example.infopapp.entities;

import static com.example.infopapp.utils.StringConstants.INSTRUCTOR;

public class Instructor extends Staff {
    public Instructor() {
        setJobTitle(INSTRUCTOR);
        setUserType(INSTRUCTOR);
    }
}
