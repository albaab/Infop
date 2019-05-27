package com.example.infopapp.ui.profile;

import android.net.Uri;

import com.example.infopapp.entities.User;
import com.example.infopapp.repositories.UserRepository;

class ProfilePresenter {

    private UserRepository userRepository;
    private ProfileModel profileModel;

    ProfilePresenter(ProfileView profileView) {
        profileModel = new ProfileModel(profileView);
        userRepository = new UserRepository(profileView);
    }

    void uploadImage(Uri imageUri) {
        profileModel.uploadImageToFireBaseStorage(imageUri);
    }

    void uploadDatabaseInfo(User user) {
        userRepository.saveStudentToFireBase(user);
    }

    void updateDbStudentInfo(User user) {
        userRepository.updateStudentInFireBase(user);
    }


}
