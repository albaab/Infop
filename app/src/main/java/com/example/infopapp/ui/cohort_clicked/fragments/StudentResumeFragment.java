package com.example.infopapp.ui.cohort_clicked.fragments;

import android.content.Context;
import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.utils.Constants.STUDENT_RESUME;


public class StudentResumeFragment extends Fragment implements DashboardView {

    private static final int READ_REQUEST = 707;
    //=======================================ATTRIBUTESS============================================
    public PDFView resumePdfView;
    public ProgressBar progressBar;


    private DashboardPresenter dashboardPresenter;

    private static final int RESUME_REQUEST_CODE = 101;

//    private OnFragmentInteractionListener mListener;

    public StudentResumeFragment() {
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
        Bundle bundle = getArguments();

        resumePdfView = view.findViewById(R.id.resume_pdf_view);
        progressBar = view.findViewById(R.id.loading_resume_progressbar);
        TextView noResumeTv = view.findViewById(R.id.no_resume_tv);
        FloatingActionButton uploadButton = view.findViewById(R.id.upload_resume_button);

        dashboardPresenter = new DashboardPresenter(this);
        if(bundle!= null){
            noResumeTv.setVisibility(View.GONE);
            uploadButton.setVisibility(View.GONE);
            dashboardPresenter.downloadResume(this,bundle.getString(STUDENT_RESUME));
        }else{
            Toast.makeText(getActivity(), getText(R.string.error_message), Toast.LENGTH_SHORT).show();
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

    @Override
    public void setUploadResumeStatus(boolean isSuccessful) {
        //do nothing
    }

//======================================FRAGMENT INTERFACE LISTENER=================================
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }



}
