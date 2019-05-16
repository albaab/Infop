package com.example.infopapp.ui.account;

import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginModel {
    private static final String TAG = "LoginModel";
    public ProgressBar progressBar;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    private LoginView loginView;

    public LoginModel(LoginView loginView) {
        this.loginView = loginView;
    }

    public boolean signIn(String email, String password) {

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

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    Log.d(TAG, "onComplete: User Registration Successful");
                    user = mAuth.getCurrentUser();
                    loginView.setLoginStatus("Registration successful");
                } else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                        loginView.setLoginStatus("User already registered");
                        Log.d(TAG, "onComplete: User already registered");

                    } else {
                        loginView.setLoginStatus("An error occured. Please try again later");
                        Log.d(TAG, "onComplete: Could not register User");
                    }

                }
            }
        });
        return true;
    }

    public boolean resetPassword(String email){
        //TODO send reset link to the email of the user with firebase

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    loginView.setLoginStatus("Reset link sent! Please follow the instructions in " +
                            "the email you provided");
                    Log.d(TAG, "onComplete: Reset link sent");
                }else{
                    loginView.setLoginStatus("An error occured! Please try again later.");
                            Log.d(TAG, "onComplete: Reset Error!"+ task.getException());
                }
            }
        });

        return true;
    }
}
