package com.example.infopapp.ui.account;

import androidx.annotation.NonNull;


import android.annotation.TargetApi;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisUser;


class LoginModel {

//======================================ATTRIBUTES==================================================
    private static final String TAG = "LoginModel";
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private LoginView loginView;
//======================================CONSTRUCTOR==================================================
    LoginModel(LoginView loginView) {
        this.loginView = loginView;
    }

//======================================LOGIN MODEL METHODS==================================================
    boolean signIn(final String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    thisUser.setId(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());
                    thisUser.setUserType(mAuth.getCurrentUser().getDisplayName());
                    Log.d(TAG, "onComplete: START LOGIN SUCCESSUL, USER AUTHENTIFIED " + thisUser.getId());
                    loginView.setLoginStatus(true, mAuth.getCurrentUser().isEmailVerified());
                } else {
                    Log.d(TAG, "onComplete: Failed to Sign in", task.getException());
                    loginView.setLoginStatus(false, false);
                }
            }
        });

        return true;
    }

    boolean signUp(final String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    thisUser.setEmail(email);
                    thisUser.setId(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

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
