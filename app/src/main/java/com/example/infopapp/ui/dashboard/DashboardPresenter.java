package com.example.infopapp.ui.dashboard;

import android.net.Uri;

import com.example.infopapp.ui.cohort_clicked.fragments.StudentResumeFragment;
import com.example.infopapp.ui.dashboard.fragments.thisStudentResumeFragment;

import androidx.fragment.app.Fragment;

public class DashboardPresenter {
//======================================ATTRIBUTES=================================================
    private DashboardModel dashboardModel;

//======================================CONSTRUCTOR=================================================
    public DashboardPresenter(DashboardView dashboardView) {
        dashboardModel = new DashboardModel(dashboardView);
    }

//======================================PRESENTER METHODS===========================================
    public void uploadResume(Uri resumeUri, thisStudentResumeFragment thisStudentResumeFragment){
        dashboardModel.uploadResumeTofirebaseStorage(resumeUri, thisStudentResumeFragment);
    }

    public void downloadThisStudentResume(thisStudentResumeFragment fragment, String resumeUrl){
        dashboardModel.downloadResumeFromThisUrl(fragment,resumeUrl);
    }
    public void downloadResume(StudentResumeFragment fragment, String resumeUrl){
        dashboardModel.downloadResumeFromUrl(fragment,resumeUrl);
    }



}
