package com.example.infopapp.models;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ProgressBar;

import com.example.infopapp.views.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginModel {
    private static final String TAG = "LoginModel Tag";
    public ProgressBar progressBar;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private LoginView loginView;

    public LoginModel(LoginView loginView) {
        this.loginView = loginView;
    }

    public boolean signIn(String email, String password) {

        //sign in with firebase
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: Signed in");
                    loginView.setLoginStatus("Logged in");

                    user = mAuth.getCurrentUser();
                } else {
                    Log.d(TAG, "onComplete: Failed to Sign in", task.getException());
                    loginView.setLoginStatus("Failed to log in");
                }
            }
        });

        return true;
    }

    public boolean signUp(String email, String password) {


        // sign up with firebase auth

        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: User Registration Successful");
                    user = mAuth.getCurrentUser();
                    loginView.setLoginStatus("Registration successful");
                    return;
                } else {
                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        loginView.setLoginStatus("User already registered");
                    } else {
                        loginView.setLoginStatus("An error occured. Please try again later");
                    }

                    Log.d(TAG, "onComplete: Could not register User");
                }
            }
        });
        return true;
    }
}
