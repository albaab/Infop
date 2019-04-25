package com.example.infopapp.presenters;

import android.util.Patterns;
import android.widget.ProgressBar;

import com.example.infopapp.models.LoginModel;
import com.example.infopapp.views.LoginView;

public class LogPresenter {

    private static final int PASSWORD_LENGTH_MIN = 6;
    private LoginView loginView;
    private LoginModel loginModel;
//    public ProgressBar logProgressbar;


    public LogPresenter(LoginView loginView) {
        this.loginView = loginView;
//        logProgressbar = loginModel.progressBar;
        loginModel = new LoginModel(loginView);
    }
    //====================================sign in user method======================================//

    public boolean signUserIn(String email, String password) {

        //======================================verify user info syntax=============================//
        if (email.isEmpty()) {
            loginView.setEmailError("Please enter an e-mail");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginView.setEmailError("Invalid e-mail syntax");
            return false;
        }
        if (password.isEmpty()) {
            loginView.setPasswordError("Please enter a password");
            return false;
        } else if (password.length() < PASSWORD_LENGTH_MIN) {
            loginView.setPasswordError("Password should be at least 6 characters long");
            return false;
        }

        return loginModel.signIn(email, password);
    }

    //=======================================register User method===================================//
    public boolean registerUser(String email, String password, String confirmPassword) {

        if (email.isEmpty()) {
            loginView.setEmailError("Please enter an e-mail");
            return false;

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginView.setEmailError("Invalid e-mail syntax");
            return false;

        }
        if (password.isEmpty()) {
            loginView.setPasswordError("Please enter a password");
            return false;
        } else if (password.length() < PASSWORD_LENGTH_MIN) {
            loginView.setPasswordError("Password should be at least 6 characters long");
            return false;
        } else if (!confirmPassword.equals(password)) {
            loginView.setConfirmError("Mismatch! Please confirm your password");
            return false;
        }
//
//        if(loginModel.signUp(email,password) == true){
//            loginView.setLoginStatus("Registration successful");
//            return ;
////
//        }else{
//            loginView.setLoginStatus("An error occured. Please Try again later");
//            return;
//        }
        return loginModel.signUp(email, password);
    }


}
