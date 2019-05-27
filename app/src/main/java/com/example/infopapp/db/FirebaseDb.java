package com.example.infopapp.db;

import android.util.Log;

import com.example.infopapp.entities.Instructor;
import com.example.infopapp.entities.Staff;
import com.example.infopapp.entities.Student;
import com.example.infopapp.entities.User;
import com.example.infopapp.ui.profile.ProfileView;
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
//    private List<Cohort> cohorts = new ArrayList<>();
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
//            String currentUserId = userTypeReference.push().getKey();

//            user.setId(currentUserId);
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
    public static void isUserInFirebaseDb(final User user, final FirebaseDbCallBack fbCallback) {

        Log.d(TAG, "isUserInFirebaseDb: START SEARCHING IN DATABASE");

        userTypeReference = listOfStudentsDatabaseReference.child(user.getUserType());
        userTypeReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                            Log.d(TAG, "onDataChange: START SEARCHING IN RESPECTIVE DATABASE" + snapshot.getKey() +
                                    " " + user.getId());
                            if(snapshot.getKey().equals(user.getId())){
                                Log.d(TAG, "onDataChange: START " + snapshot.getKey());
                                switch (user.getUserType()) {
                                    case STUDENT:
                                        Student student = snapshot.getValue(Student.class);
                                    Log.d(TAG, "START USER IN STUDENT DATABASE "+ student.getUserType());
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
                                        fbCallback.onCallBackGetUser(user1,null,null,null);
                                    default:
                                        fbCallback.onCallBackGetUser(new User(),null,null,null);
                                        break;
                                }
                            }else{

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


    //==========================================Firebase CallBack Interface=============================
    public interface FirebaseDbCallBack {
        void onCallBack(List<Student> studentList);
        void onCallBackGetUser(User user, Student student, Staff staff, Instructor instructor);
    }
}
