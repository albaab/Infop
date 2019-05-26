package com.example.infopapp.db;

import android.util.Log;

import com.example.infopapp.entities.Cohort;
import com.example.infopapp.entities.Instructor;
import com.example.infopapp.entities.Staff;
import com.example.infopapp.entities.Student;
import com.example.infopapp.entities.User;
import com.example.infopapp.ui.profile.ProfileView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisUser;
import static com.example.infopapp.utils.StringConstants.GUEST;
import static com.example.infopapp.utils.StringConstants.INSTRUCTOR;
import static com.example.infopapp.utils.StringConstants.STAFF;
import static com.example.infopapp.utils.StringConstants.STUDENT;

public class FirebaseDb {

//====================================PRIVATE ATTRIBUTES============================================

    private ProfileView profileView;
    private static DatabaseReference listOfStudentsDatabaseReference = FirebaseDatabase
            .getInstance().getReference();
    private static DatabaseReference userTypeReference;

    private static List<Student> students = new ArrayList<>();
    private List<Cohort> cohorts = new ArrayList<>();
//===================================PUBLIC CONSTRUCTOR=============================================

    public FirebaseDb(ProfileView profileView) {
        this.profileView = profileView;
    }


//=====================================FIREBASE DATABASE METHODS====================================

    //    ---------------------------------Save user to database------------------------------------
    public void uploadUserToFirebaseDb(User user) {

        if (user.getUserType() != null) {

            userTypeReference = listOfStudentsDatabaseReference
                    .child(user.getUserType());
            String currentUserId = userTypeReference.push().getKey();
            user.setId(currentUserId);
            userTypeReference.child(user.getId())
                    .setValue(user, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            profileView.setUploadUserToDbStatus(true);
                            Log.d(TAG, "ThisUser: STUDENT ADDED TO FIRE BASE DATABASE");
                        }
                    });
        } else {
            profileView.setUploadUserToDbStatus(false);
        }
    }

    //    ---------------------------------Update user in database----------------------------------
    public void updateUserInFirebaseDb(User user) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        userTypeReference = listOfStudentsDatabaseReference.child(user.getUserType());
        userTypeReference.child(user.getId())
                .setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        profileView.setUploadUserToDbStatus(true);
                        Log.d(TAG, "onComplete: STUDENT UPDATED TO FIRE BASE DATABASE");
                    }
                });
    }

    //--------------------------------------is user in database?----------------------------------------
    public static void isUserInFirebaseDb() {

        Log.d(TAG, "isUserInFirebaseDb: START SEARCHING IN THE DATABASE");
        isUserInStudentFirebaseDb();
        isUserInStaffFirebaseDb();
        isUserInInstructorFirebaseDb();
        isUserInGuestFirebaseDb();
    }

    //---------------------------------get all students in firebase---------------------------------

    public void getAllStudentsFromFirebase(final FirebaseDbCallBack firebaseDbCallBack) {
//        userTypeReference = listOfStudentsDatabaseReference.child(STUDENT);
        listOfStudentsDatabaseReference.child(STUDENT)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d(TAG, "onDataChange: START GET ALL STUDENTS FROM DATABASE");
                        for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                            Student talent = studentSnapshot.getValue(Student.class);
                            students.add(talent);
                        }
                        firebaseDbCallBack.onCallBack(students);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

//    public List<Cohort> getCohorts() {
//
//        Log.d(TAG, "getCohorts: ARRANGE THE STUDENTS FOUND IN RESPECTIVE COHORTS");
//        //store the first student in a cohort
//
//        getAllStudentsFromFirebase(new FirebaseDbCallBack() {
//            @Override
//            public void onCallBack(List<Student> studentList) {
//                students = studentList;
//                Cohort firstCohortfound = new Cohort(students.get(0));
//
//                //set the number of the cohort
//                firstCohortfound.setCohortNumber(students.get(0).getCohort());
//
//                //add that cohort to the list of cohorts
//                cohorts.add(firstCohortfound);
//
//                // loop to store each student into its respective cohort
//                for (int i = 0; i < students.size() - 1; i++) {
//
//                    Student std1 = students.get(i); // student at index i
//                    Student std2 = students.get(i + 1);// student at the next index
//
//                    if (std1.getCohort() != std2.getCohort()) {
//
//                        for (int cohortIndex = 0; cohortIndex <= cohorts.size() - 1; cohortIndex++) {
//
//                            if (cohorts.get(cohortIndex).getCohortNumber()
//                                    == std2.getCohort()) {
//                                cohorts.get(cohortIndex)
//                                        .addStudentToCohort(std2);
//                                break;
//
//                            } else if (cohortIndex == cohorts.size() - 1) {
//                                //create new cohort
//                                Cohort nextCohort = new Cohort(std2);
//                                //set the cohort number of the new cohort with cohort number stored in the student object
//                                nextCohort.setCohortNumber(std2.getCohort());
//                                //add cohort to the list of cohorts
//                                cohorts.add(nextCohort);
//                            }
//                        }
//
//                    } else {
//                        for (int cohortIndex = 0; cohortIndex <= cohorts.size() - 1; cohortIndex++) {
//
//                            if (cohorts.get(cohortIndex).getCohortNumber()
//                                    == std2.getCohort()) {
//                                cohorts.get(cohortIndex)
//                                        .addStudentToCohort(std2);
//                                break;
//
//                            } else if (cohortIndex == cohorts.size() - 1) {
//                                cohorts.get(i).addStudentToCohort(std2);
//                            }
//                        }
//                    }
//
//                }
//            }
//        });
//
//
//        return cohorts;
//    }


//======================================PRIVATE METHODS=============================================
    private static void isUserInStudentFirebaseDb() {

        Log.d(TAG, "isUserInStudentFirebaseDb: START SEARCHING IN STUDENT DATABASE");

        userTypeReference = listOfStudentsDatabaseReference.child(STUDENT);
        userTypeReference
                .child(thisUser.getId())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d(TAG, "START: IS USER IN STAFF DATABASE");
                if (dataSnapshot.exists()) {
                    thisUser = dataSnapshot.getValue(Student.class);
                    Log.d(TAG, "START FIREBASE: STAFF IN DATABASE " + thisUser +
                            " " + thisUser.getUserType());

                } else {
                    Log.d(TAG, "START: FIREBASE STAFF NOT IN DATABASE");
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "START: FIREBASE ERROR DURING VERIFICATION");
                //todo handle database entry check error
            }
        });
    }

    private static void isUserInStaffFirebaseDb() {

        Log.d(TAG, "isUserInStudentFirebaseDb: START SEARCHING IN STAFF DATABASE");

        userTypeReference = listOfStudentsDatabaseReference.child(STAFF);
        userTypeReference
                .child(thisUser.getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Log.d(TAG, "START: IS USER IN STAFF DATABASE");

                        if (dataSnapshot.exists()) {
                            thisUser = dataSnapshot.getValue(Staff.class);
                            Log.d(TAG, "START FIREBASE: STAFF IN DATABASE " + thisUser +
                                    " " + thisUser.getUserType());

                        } else {
                            Log.d(TAG, "START: FIREBASE STAFF NOT IN DATABASE");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "START: FIREBASE ERROR DURING VERIFICATION");
                        //todo handle database entry check error
                    }
                });
    }

    private static void isUserInInstructorFirebaseDb() {
        Log.d(TAG, "isUserInStudentFirebaseDb: START SEARCHING IN INSTRUCTOR DATABASE");

        userTypeReference = listOfStudentsDatabaseReference.child(INSTRUCTOR);
        userTypeReference
                .child(thisUser.getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            thisUser = dataSnapshot.getValue(Instructor.class);
                            Log.d(TAG, "START FIREBASE: INSTRUCTOR IN DATABASE ");
                        } else {
                            Log.d(TAG, "START: FIREBASE INSTRUCTOR NOT IN DATABASE");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "START: FIREBASE ERROR DURING VERIFICATION");
                        //todo handle database entry check error
                    }
                });
    }

    private static void isUserInGuestFirebaseDb() {
        Log.d(TAG, "isUserInStudentFirebaseDb: START SEARCHING IN GUEST DATABASE");

        userTypeReference = listOfStudentsDatabaseReference.child(GUEST);

        userTypeReference
                .child(thisUser.getId())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            thisUser = dataSnapshot.getValue(User.class);
                            Log.d(TAG, "START FIREBASE: GUEST IN DATABASE ");
                        } else {
                            Log.d(TAG, "START: FIREBASE GUEST NOT IN DATABASE");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d(TAG, "START: FIREBASE ERROR DURING VERIFICATION");
                        //todo handle database entry check error
                    }
                });
    }

    public interface FirebaseDbCallBack{
        void onCallBack(List<Student> studentList);
    }
}

