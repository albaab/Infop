package com.example.infopapp.ui.dashboard;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.example.infopapp.databases.FirebaseUserDb;
import com.example.infopapp.ui.cohort_clicked.fragments.StudentResumeFragment;
import com.example.infopapp.ui.dashboard.fragments.thisStudentResumeFragment;
import com.example.infopapp.utils.DownloadPdfAsyncTask;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.lang.ref.WeakReference;
import java.util.Objects;

import androidx.annotation.NonNull;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisStudent;

class DashboardModel {

//======================================ATTRIBUTES==================================================
    private String resumeUrl;
    private FirebaseUserDb firebaseUserDb;

//======================================CONSTRUCTOR=================================================
    DashboardModel(DashboardView dashboardView) {
        firebaseUserDb = new FirebaseUserDb(dashboardView);
    }
//======================================MODEL METHODS===============================================
    void uploadResumeTofirebaseStorage(final Uri resumeUri, thisStudentResumeFragment thisStudentResumeFragment) {

        WeakReference<thisStudentResumeFragment> resumeFragmentWeakRef =
                new WeakReference<>(thisStudentResumeFragment);
        final thisStudentResumeFragment thisStudentResumeFragment1 =resumeFragmentWeakRef.get();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        StorageReference resumeStorageRef = FirebaseStorage.getInstance().getReference("Students CV");
        final StorageReference userResumeRef = resumeStorageRef
                .child((user != null ? user.getEmail() : null) + " " + "CV" + System.currentTimeMillis() + ".pdf");
        UploadTask uploadTask = userResumeRef.putFile(resumeUri);

        thisStudentResumeFragment1.progressBar.setVisibility(View.VISIBLE);

        uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @TargetApi(Build.VERSION_CODES.KITKAT)
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                if (!task.isSuccessful()) {
                    throw Objects.requireNonNull(task.getException());
                } else {
                    Log.d(TAG, "then: DEBUG -> DOWNLOAD URL READY");
                    return userResumeRef.getDownloadUrl();
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    thisStudentResumeFragment1.progressBar.setVisibility(View.GONE);
                    resumeUrl = task.getResult().toString();
                    thisStudent.setResumeUrl(resumeUrl);
                    firebaseUserDb.updateStudentResume(thisStudent);
                    //todo update the current student info in firebase database
                } else {
                    Log.d(TAG, "onComplete: DEBUG -> COULD NOT GET DOWNLOAD URL");
                }
            }
        });

    }

    void downloadResumeFromThisUrl(thisStudentResumeFragment fragment, String resumeUrl) {
        new DownloadPdfAsyncTask(fragment).execute(resumeUrl);
    }

    void downloadResumeFromUrl(StudentResumeFragment fragment, String resumeUrl) {
        new DownloadPdfAsyncTask(fragment).execute(resumeUrl);
    }


}
