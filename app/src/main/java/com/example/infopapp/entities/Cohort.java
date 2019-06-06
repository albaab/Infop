package com.example.infopapp.entities;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Cohort {



//========================================ATTRIBUTES================================================

    private int cohortNumber;
    private List<Student> studentList = new ArrayList<>();


//========================================CONSTRUCTORS==============================================
    public Cohort() {
    }

    public Cohort (Student student){
        studentList.add(student);
    }

    public Cohort(int cohortNumber, List<Student> studentList) {
        this.cohortNumber = cohortNumber;
        this.studentList = studentList;
    }




//========================================setters===================================================
    public void setCohortNumber(int cohortNumber) {
        this.cohortNumber = cohortNumber;
    }
//    public void setStudentList(List<Student> studentList) {
//        this.studentList = studentList;
//    }

//========================================getters===================================================
    public int getCohortNumber() {
        return cohortNumber;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

//========================================toString==================================================

    @NonNull
    @Override
    public String toString() {
        return "Cohort " + String.valueOf(cohortNumber);
    }
//===============================ADD STUDENT TO THE COHORT METHOD===================================

    public void addStudentToCohort(Student student){
        studentList.add(student);
    }

}
