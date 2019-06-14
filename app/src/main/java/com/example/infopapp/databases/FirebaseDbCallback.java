package com.example.infopapp.databases;

import com.example.infopapp.entities.Instructor;
import com.example.infopapp.entities.Staff;
import com.example.infopapp.entities.Student;
import com.example.infopapp.entities.User;

import java.util.List;

public interface FirebaseDbCallback {
    void onCallBack(List<Student> studentList);
    void onCallBackGetUser(User user, Student student, Staff staff, Instructor instructor);
}
