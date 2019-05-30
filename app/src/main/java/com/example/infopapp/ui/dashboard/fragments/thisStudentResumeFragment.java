package com.example.infopapp.ui.dashboard.fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.ui.dashboard.DashboardPresenter;
import com.example.infopapp.ui.dashboard.DashboardView;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisStudent;


public class thisStudentResumeFragment extends Fragment implements DashboardView {

    private static final int READ_REQUEST = 707;
//=======================================ATTRIBUTESS============================================
    public PDFView resumePdfView;
    public ProgressBar progressBar;


    private DashboardPresenter dashboardPresenter;

    private static final int RESUME_REQUEST_CODE = 101;

//    private OnFragmentInteractionListener mListener;

    public thisStudentResumeFragment() {
        // Required empty public constructor
    }


//=======================================ON CREATE VIEW=============================================
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_resume, container, false);
    }
//=======================================ON VIEW CREATED============================================
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: DEBUG -> RESUME FRAGMENT CREATED");
        resumePdfView = view.findViewById(R.id.resume_pdf_view);
        progressBar = view.findViewById(R.id.loading_resume_progressbar);
        TextView noResumeTv = view.findViewById(R.id.no_resume_tv);
        FloatingActionButton uploadButton = view.findViewById(R.id.upload_resume_button);

        dashboardPresenter = new DashboardPresenter(this);

        if(thisStudent != null && thisStudent.getResumeUrl() != null){
            noResumeTv.setVisibility(View.GONE);
            dashboardPresenter.downloadThisStudentResume(this,thisStudent.getResumeUrl());
        }else{
            noResumeTv.setVisibility(View.VISIBLE);
        }

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ContextCompat.checkSelfPermission(getActivity()
                        , Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED){
                    chooseResumePDF();
                }else{
                    ActivityCompat.requestPermissions(getActivity()
                            , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE
                                    ,Manifest.permission.WRITE_EXTERNAL_STORAGE},READ_REQUEST);
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == READ_REQUEST && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            chooseResumePDF();
        }else{
            Toast.makeText(getActivity(), getText(R.string.grant_access), Toast.LENGTH_SHORT).show();
        }
    }

//====================================ON ACTIVITY RESULT============================================
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == RESUME_REQUEST_CODE && resultCode == RESULT_OK && data != null
                && data.getData() != null){

            //upload the resume selected to firebase reference storage
            dashboardPresenter.uploadResume(data.getData(), this);

            Log.d(TAG, "onActivityResult: DEBUG : PDF FILE SELECTED");
        }
    }

//===============================DASHBOARDVIEW INTERFACE METHODS====================================
    @Override
    public void setUploadResumeStatus(boolean isSuccessful) {
        //TODO handle the status of the upload
        if(isSuccessful){
            Toast.makeText(getActivity(), getText(R.string.upload_success), Toast.LENGTH_SHORT).show();
            dashboardPresenter.downloadThisStudentResume(this,thisStudent.getResumeUrl());
        }
    }


//==========================================ON ATTACH===============================================
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

//==========================================ON DETACH===============================================
    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

//======================================FRAGMENT INTERFACE LISTENER=================================
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }


//======================================private utility methods=====================================

    private void chooseResumePDF(){
        Intent choooseResumeIntent = new Intent();
        choooseResumeIntent.setType("application/pdf");
        choooseResumeIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult
                (Intent.createChooser
                        (choooseResumeIntent,getText(R.string.choose_resume)),RESUME_REQUEST_CODE);
    }

}
