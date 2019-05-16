package com.example.infopapp.ui.account;

public interface LoginView {
    void setLoginStatus(String message);
    void setPasswordError(String passwordError);
    void setEmailError(String emailError);
    void setConfirmError(String confirmError);

}
