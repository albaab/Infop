package com.example.infopapp.ui.account;

import androidx.annotation.NonNull;


import android.util.Log;

import com.example.infopapp.db.FirebaseDb;
import com.example.infopapp.ui.profile.ProfileView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.UserProfileChangeRequest;

import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisUser;


class LoginModel {
    private static final String TAG = "LoginModel";
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LoginView loginView;

    LoginModel(LoginView loginView) {
        this.loginView = loginView;
    }

    boolean signIn(final String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    thisUser.setId(mAuth.getCurrentUser().getUid());
                    FirebaseDb.isUserInFirebaseDb();
                    Log.d(TAG, "onComplete: START LOGIN SUCCESSUL, USER AUTHENTIFIED " + thisUser.getId());
                    loginView.setLoginStatus(true, mAuth.getCurrentUser().isEmailVerified());
                } else {
                    Log.d(TAG, "onComplete: Failed to Sign in", task.getException());
                    loginView.setLoginStatus(false, mAuth.getCurrentUser().isEmailVerified());
                }
            }
        });

        return true;
    }

    boolean signUp(final String email, String password)   {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    thisUser.setEmail(email);
                    thisUser.setId(mAuth.getCurrentUser().getUid());
                    UserProfileChangeRequest userType = new UserProfileChangeRequest.Builder()
                            .setDisplayName(thisUser.getUserType())
                            .build();

                    mAuth.getCurrentUser().updateProfile(userType);

                    loginView.setSignUpStatus(true, false);
                    Log.d(TAG, "onComplete: User Registration Successful ");

//                    FirebaseAuth.getInstance()
//                            .getCurrentUser()
//                            .sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                        }
//                    });
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        loginView.setSignUpStatus(false, true);
                        Log.d(TAG, "onComplete: User already registered");

                    } else {
                        loginView.setSignUpStatus(false, false);
                        Log.d(TAG, "onComplete: Could not register User");
                    }
                }
            }
        });
        return true;
    }

    boolean resetPassword(String email) {

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    loginView.setLoginStatus(true, true);
                    Log.d(TAG, "onComplete: Reset link sent");
                } else {
                    loginView.setLoginStatus(false, true);
                    Log.d(TAG, "onComplete: Reset Error!" + task.getException());
                }
            }
        });

        return true;
    }

}
