package com.example.infopapp.repositories;

import com.example.infopapp.databases.FirebaseDbCallback;
import com.example.infopapp.databases.FirebaseUserDb;
import com.example.infopapp.entities.Cohort;
import com.example.infopapp.entities.Instructor;
import com.example.infopapp.entities.Staff;
import com.example.infopapp.entities.Student;
import com.example.infopapp.entities.User;
import com.example.infopapp.ui.profile.ProfileView;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {

//====================================PRIVATE ATTRIBUTES============================================
    private FirebaseUserDb firebaseUserDb;

//========================================CONSTRUCTORS==============================================
    public UserRepository(ProfileView profileView) {
        firebaseUserDb = new FirebaseUserDb(profileView);
    }

    public UserRepository(){
        //empty constructor
    }

//    public void saveStudentToFireBase(User user) {
//        firebaseUserDb.uploadUserToFirebaseDb(user);
//    }

//=====================================REPOSITORY METHODS ==========================================

    public void updateStudentInFireBase(User user) {
        firebaseUserDb.updateUserInFirebaseDb(user);
    }

    public void getAllCohorts(final UserRepoCallBackInterface repoCallBackInterface) {

        firebaseUserDb.getAllStudentsFromFirebase(new FirebaseDbCallback() {
                    @Override
                    public void onCallBack(List<Student> studentList) {

                        if(studentList.size() > 0){
                            List<Cohort> cohorts = new ArrayList<>();
                            Cohort firstCohortfound = new Cohort(studentList.get(0));

                            //set the number of the cohort
                            firstCohortfound.setCohortNumber(studentList.get(0).getCohort());

                            //add that cohort to the list of cohorts
                            cohorts.add(firstCohortfound);

                            arrangeStudentsInCohort(studentList, cohorts);
                            repoCallBackInterface.onCallBackRepo(cohorts);
                        }

                    }

                      @Override
                      public void onCallBackGetUser(User user, Student student
                              , Staff staff, Instructor instructor) {
                      }

          }
        );
    }

    public void deteleStudentInFirebase(Student student){
        firebaseUserDb.deleteEntry(student);
    }

    private void arrangeStudentsInCohort(List<Student> studentList, List<Cohort> cohorts) {
        // loop to store each student into its respective cohort
        for (int i = 0; i < studentList.size() - 1; i++) {

            Student std1 = studentList.get(i); // student at index i
            Student std2 = studentList.get(i + 1);// student at the next index

            if (std1.getCohort() != std2.getCohort()) {//if the adjacents students have the same cohort number

                for (int cohortIndex = 0; cohortIndex <= (cohorts.size() - 1); cohortIndex++) {

                    if (cohorts.get(cohortIndex).getCohortNumber() == std2.getCohort()) {
                        cohorts.get(cohortIndex).addStudentToCohort(std2);
                        break;

                    } else if (cohortIndex == (cohorts.size() - 1) ) {
                        //create new cohort
                        Cohort nextCohort = new Cohort(std2);
                        //set the cohort number of the new cohort with cohort number stored in the student object
                        nextCohort.setCohortNumber(std2.getCohort());
                        //add cohort to the list of cohorts
                        cohorts.add(nextCohort);
                        break;
                    }
                }

            } else {
                for (int cohortIndex = 0; cohortIndex <= cohorts.size() - 1; cohortIndex++) {

                    if (cohorts.get(cohortIndex).getCohortNumber()
                            == std2.getCohort()) {
                        cohorts.get(cohortIndex).addStudentToCohort(std2);
                        break;
                    } else if (cohortIndex == cohorts.size() - 1) {
                        cohorts.get(i).addStudentToCohort(std2);
                        break;
                    }
                }
            }
        }
    }


//=================================interface callback ==============================================

    public interface UserRepoCallBackInterface {
        void onCallBackRepo(List<Cohort> cohorts);
    }



}
