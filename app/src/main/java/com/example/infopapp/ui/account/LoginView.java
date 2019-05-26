package com.example.infopapp.ui.account;

public interface LoginView {
    void setLoginStatus(boolean isSuccessful, boolean isEmailVerified);
    void setSignUpStatus(boolean isSuccessful, boolean isUserAlreadyRegistered);
    void setPasswordError(String passwordError);
    void setEmailError(String emailError);
    void setConfirmError(String confirmError);

}
