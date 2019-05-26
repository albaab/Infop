package com.example.infopapp.ui.profile;

import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import static android.view.View.GONE;
import static androidx.constraintlayout.widget.Constraints.TAG;

class ProfileModel {

    private ProfileView profileView;
    private String profileImageUrl;
    private FirebaseUser user;

    //    private Uri imageUri;

    ProfileModel(ProfileView profileView) {
        this.profileView = profileView;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
    }

    //======================================upload image to fire storage -==============================
    void uploadImageToFireBaseStorage(final Uri imageUri) {

        //get the firebase storage reference
        StorageReference mReference = FirebaseStorage.getInstance().getReference();
        final StorageReference profileStorageReference = mReference.child("Profile images/"
                + user.getEmail() + " " + System.currentTimeMillis() + ". jpg");

        //start the upload task
        UploadTask uploadTask = profileStorageReference.putFile(imageUri);
        profileView.setUploadProfilePic(false, View.VISIBLE);
        Log.d(TAG, "then: PROGRESS BAR VISIBLE -> UPLOADING PICTURE");

        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if (!task.isSuccessful()) {
                    Log.d(TAG, "then: upload task failed");
                    throw Objects.requireNonNull(task.getException());
                } else {
                    return profileStorageReference.getDownloadUrl();
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    profileImageUrl = task.getResult().toString();
                    replaceOldImage(profileImageUrl);
                } else {
                    Log.d(TAG, "onComplete: failed to upload image");
                }
            }
        });

    }

    //======================================save student profile image url =============================
    private void saveUserProfileImage(final String profileImageUrl) {

        UserProfileChangeRequest imageProfile = new UserProfileChangeRequest.Builder()
                .setPhotoUri(Uri.parse(profileImageUrl))
                .build();

        user.updateProfile(imageProfile).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()) {
                    profileView.setUploadProfilePic(true, View.GONE);
                    Log.d(TAG, "onComplete: Profile image Updated");
                } else {
                    profileView.setUploadProfilePic(false, View.GONE);
                    Log.d(TAG, "onComplete: profile not updated");
                }
            }
        });

    }

//==============================delete old profile image and store new link=========================

    private void replaceOldImage(String profileImageUrl) {

        if (user.getPhotoUrl() != null) {
            String oldImageUrl = user.getPhotoUrl().toString();
            StorageReference imageUrlRef = FirebaseStorage.getInstance()
                    .getReferenceFromUrl(oldImageUrl);
            imageUrlRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d(TAG, "onSuccess: OLD PROFILE PICTURE DELETED FROM DATABASE");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: COULD NOT DELETE OLD PROFILE PICTURE");
                }
            });
        }
        saveUserProfileImage(profileImageUrl);
    }


}
