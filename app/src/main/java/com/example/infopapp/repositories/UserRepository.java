package com.example.infopapp.repositories;

import com.example.infopapp.db.FirebaseDb;
import com.example.infopapp.entities.Cohort;
import com.example.infopapp.entities.Instructor;
import com.example.infopapp.entities.Staff;
import com.example.infopapp.entities.Student;
import com.example.infopapp.entities.User;
import com.example.infopapp.ui.profile.ProfileView;

import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private FirebaseDb firebaseDb;

    public UserRepository(ProfileView profileView) {
        firebaseDb = new FirebaseDb(profileView);
    }


    public void saveStudentToFireBase(User user) {
        firebaseDb.uploadUserToFirebaseDb(user);
    }

    public void updateStudentInFireBase(User user) {
        firebaseDb.updateUserInFirebaseDb(user);
    }


    public void getAllCohorts(final UserRepoCallBackInterface repoCallBackInterface) {

        firebaseDb.getAllStudentsFromFirebase(new FirebaseDb.FirebaseDbCallBack() {
                    @Override
                    public void onCallBack(List<Student> studentList) {
                        List<Cohort> cohorts = new ArrayList<>();
                        Cohort firstCohortfound = new Cohort(studentList.get(0));

                        //set the number of the cohort
                        firstCohortfound.setCohortNumber(studentList.get(0).getCohort());

                        //add that cohort to the list of cohorts
                        cohorts.add(firstCohortfound);

                        //if the lisf of students has more than one student
                            arrangeStudentsInCohort(studentList, cohorts);
                            repoCallBackInterface.onCallBackRepo(cohorts);
                    }

                      @Override
                      public void onCallBackGetUser(User user, Student student
                              , Staff staff, Instructor instructor) {
                      }

          }
        );
    }

    public interface UserRepoCallBackInterface {
        void onCallBackRepo(List<Cohort> cohorts);
    }

    private void arrangeStudentsInCohort(List<Student> studentList, List<Cohort> cohorts) {
        // loop to store each student into its respective cohort
        for (int i = 0; i < studentList.size() - 1; i++) {

            Student std1 = studentList.get(i); // student at index i
            Student std2 = studentList.get(i + 1);// student at the next index

            if (std1.getCohort() != std2.getCohort()) {//if the adjacents students have the same cohort number

                for (int cohortIndex = 0; cohortIndex <= (cohorts.size() - 1); cohortIndex++) {

                    if (cohorts.get(cohortIndex).getCohortNumber()
                            == std2.getCohort()) {
                        cohorts.get(cohortIndex)
                                .addStudentToCohort(std2);
                        break;

                    } else if (cohortIndex == (cohorts.size() - 1) ) {
                        //create new cohort
                        Cohort nextCohort = new Cohort(std2);
                        //set the cohort number of the new cohort with cohort number stored in the student object
                        nextCohort.setCohortNumber(std2.getCohort());
                        //add cohort to the list of cohorts
                        cohorts.add(nextCohort);
                    }
                }

            } else {
                for (int cohortIndex = 0; cohortIndex <= cohorts.size() - 1; cohortIndex++) {

                    if (cohorts.get(cohortIndex).getCohortNumber()
                            == std2.getCohort()) {
                        cohorts.get(cohortIndex).addStudentToCohort(std2);
                        break;
                    } else if (cohortIndex == cohorts.size() - 1) {
                        cohorts.get(cohortIndex).addStudentToCohort(std2);
                    }
                }
            }
        }
    }

    public void deteleStudentInFirebase(Student student){
        firebaseDb.deleteEntry(student);
    }


}
