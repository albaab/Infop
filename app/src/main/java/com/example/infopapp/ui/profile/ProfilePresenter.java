package com.example.infopapp.ui.profile;

import android.net.Uri;

import com.example.infopapp.entities.User;
import com.example.infopapp.repositories.UserRepository;

class ProfilePresenter {


//=====================================PRIVATE ATTRIBUTES===========================================
    private UserRepository userRepository;
    private ProfileModel profileModel;


//=====================================CONSTRUCTOR==================================================

    ProfilePresenter(ProfileView profileView) {
        profileModel = new ProfileModel(profileView);
        userRepository = new UserRepository(profileView);
    }

//=====================================PRESENTER METHODS============================================

    //---------------------------UPLOAD PROFILE IMAGE TO FIREBASE STORAGE---------------------------
    void uploadImage(Uri imageUri) {
        profileModel.uploadImageToFireBaseStorage(imageUri);
    }

    //-------------------------UPDATE THIS USER'S INFO IN FIREBASE DATABASE-------------------------
    void updateDbUserInfo(User user) {
        userRepository.updateStudentInFireBase(user);
    }


}
