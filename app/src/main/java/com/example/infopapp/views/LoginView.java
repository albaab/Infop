package com.example.infopapp.views;

public interface LoginView {
    void setLoginStatus(String message);
    void setPasswordError(String passwordError);
    void setEmailError(String emailError);
    void setConfirmError(String confirmError);

}
