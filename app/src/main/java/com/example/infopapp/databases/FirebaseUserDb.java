package com.example.infopapp.databases;

import android.util.Log;

import com.example.infopapp.entities.Instructor;
import com.example.infopapp.entities.Staff;
import com.example.infopapp.entities.Student;
import com.example.infopapp.entities.User;
import com.example.infopapp.ui.dashboard.DashboardView;
import com.example.infopapp.ui.profile.ProfileView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import static com.example.infopapp.utils.Constants.GUEST;
import static com.example.infopapp.utils.Constants.INSTRUCTOR;
import static com.example.infopapp.utils.Constants.STAFF;
import static com.example.infopapp.utils.Constants.STUDENT;

public class FirebaseUserDb {

//====================================PRIVATE ATTRIBUTES============================================

    private ProfileView profileView;
    private DashboardView dashboardView;
    private static DatabaseReference listOfStudentsDatabaseReference = FirebaseDatabase
            .getInstance().getReference();
    private static DatabaseReference userTypeReference;

    private static List<Student> students;


//===================================PUBLIC CONSTRUCTOR=============================================
    public FirebaseUserDb(ProfileView profileView) {
        this.profileView = profileView;
    }
    public FirebaseUserDb(DashboardView dashboardView){
        this.dashboardView = dashboardView;
    }


//=====================================FIREBASE DATABASE METHODS====================================

    //    ---------------------------------Save user to database------------------------------------
    public void uploadUserToFirebaseDb(User user) {

        if (user.getUserType() != null) {

            userTypeReference = listOfStudentsDatabaseReference
                    .child(user.getUserType());
//            String currentUserId = userTypeReference.push().getKey();

//            user.setId(currentUserId);
            userTypeReference.child(user.getId())
                    .setValue(user, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                            profileView.setUpdateUserInFirebaseDbStatus(true);
                            Log.d(TAG, "ThisUser: STUDENT ADDED TO FIRE BASE DATABASE");
                        }
                    });
        } else {
            profileView.setUpdateUserInFirebaseDbStatus(false);
        }
    }



    //    ---------------------------------Update user in database----------------------------------
    public void updateUserInFirebaseDb(User user) {
        userTypeReference = listOfStudentsDatabaseReference.child(user.getUserType());
        userTypeReference.child(user.getId())
                .setValue(user, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        profileView.setUpdateUserInFirebaseDbStatus(true);
                        Log.d(TAG, "onComplete: STUDENT UPDATED TO FIRE BASE DATABASE");
                    }
                });
    }



    //--------------------------------------update student resume method----------------------------
    public void updateStudentResume(Student student){
        userTypeReference = listOfStudentsDatabaseReference.child(STUDENT);
        userTypeReference.child(student.getId())
                .setValue(student, new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        dashboardView.setUploadResumeStatus(true);
                        Log.d(TAG, "onComplete: STUDENT UPDATED TO FIRE BASE DATABASE");
                    }
                });
    }



    //--------------------------------------is user in database?------------------------------------
    public static void isUserInFirebaseDb(final User user, final FirebaseDbCallBack fbCallback) {

        Log.d(TAG, "isUserInFirebaseDb: START SEARCHING IN DATABASE");

        userTypeReference = listOfStudentsDatabaseReference.child(user.getUserType());
        userTypeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: START SEARCHING IN RESPECTIVE DATABASE" + snapshot.getKey() +
                            " " + user.getId());
                    if (snapshot.getKey().equals(user.getId())) {
                        Log.d(TAG, "onDataChange: START " + snapshot.getKey());
                        switch (user.getUserType()) {
                            case STUDENT:
                                Student student = snapshot.getValue(Student.class);
                                Log.d(TAG, "START USER IN STUDENT DATABASE " + student.getUserType());
                                fbCallback.onCallBackGetUser(null, student, null, null);
                                break;
                            case STAFF:
                                Staff staff = snapshot.getValue(Staff.class);
                                fbCallback.onCallBackGetUser(null, null, staff, null);
                                break;
                            case INSTRUCTOR:
                                Instructor instructor = snapshot.getValue(Instructor.class);
                                fbCallback.onCallBackGetUser(null, null, null, instructor);
                                break;
                            case GUEST:
                                User user1 = snapshot.getValue(User.class);
                                fbCallback.onCallBackGetUser(user1, null, null, null);
                            default:
                                fbCallback.onCallBackGetUser(new User(), null, null, null);
                                break;
                        }
                    } else {

                        Log.d(TAG, "onDataChange: START USER ID AND SNAPSHOT KEY DO NOT MATCH");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "START: FIREBASE ERROR DURING VERIFICATION");
                //todo handle database entry check error
            }
        });
    }



    //---------------------------------get all students in firebase---------------------------------
    public void getAllStudentsFromFirebase(final FirebaseDbCallBack firebaseDbCallBack) {
//        userTypeReference = listOfStudentsDatabaseReference.child(STUDENT);
        listOfStudentsDatabaseReference.child(STUDENT)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        students = new ArrayList<>();
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



    //---------------------------------delete a student in firebase---------------------------------
    public void deleteEntry(Student student) {
        if (student.getUserType() != null) {

            userTypeReference = listOfStudentsDatabaseReference
                    .child(student.getUserType());
//            String currentUserId = userTypeReference.push().getKey();

//            user.setId(currentUserId);
            userTypeReference.child(student.getId())
                    .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Log.d(TAG, "onComplete: START STUDENT REMOVED FROM DATABASE");
                    if (task.isSuccessful()) {
                        profileView.setUpdateUserInFirebaseDbStatus(true);
                    }else {
                        profileView.setUpdateUserInFirebaseDbStatus(false);
                    }

                }
            });
        } else {
            profileView.setUpdateUserInFirebaseDbStatus(false);
        }
    }






    //==========================================Firebase CallBack Interface=============================
    public interface FirebaseDbCallBack {
        void onCallBack(List<Student> studentList);

        void onCallBackGetUser(User user, Student student, Staff staff, Instructor instructor);
    }
}

