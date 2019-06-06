package com.example.infopapp.ui.profile;

import android.net.Uri;

public interface ProfileView {
    void setUploadProfilePic(boolean myBoolean, int visibility);
    void setUpdateUserInFirebaseDbStatus(boolean myBoolean);
    void setUploadUserInFirebaseDbStatus(boolean myBoolean);
}
