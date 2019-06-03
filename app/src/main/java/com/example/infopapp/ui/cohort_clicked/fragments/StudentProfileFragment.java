package com.example.infopapp.ui.cohort_clicked.fragments;


import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.entities.Student;
import com.example.infopapp.utils.RoundPicasso;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentProfileFragment extends Fragment {


//==========================================ATTRIBUTES==============================================

    private Student student = new Student();
    private OnStudentProfileListener mListener;
//==========================================CONSTRUCTOR=============================================
    public StudentProfileFragment() {
        // Required empty public constructor
    }
//==========================================ON ATTACH METHOD========================================
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnStudentProfileListener){
            mListener = (OnStudentProfileListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement OnStudentProfileListener");
        }
    }

//==========================================ON DETACH METHOD========================================
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

//==========================================ON CREATE VIEW METHOD===================================

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_profile, container, false);
    }

//==========================================ON VIEW CREATED METHOD==================================

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView studentImageView = view.findViewById(R.id.student_profile_image);
        TextView studentFullNameTv = view.findViewById(R.id.student_name_tv);
        TextView studentDisplayNameTv = view.findViewById(R.id.student_display_tv);
        TextView studentEmailTv = view.findViewById(R.id.student_email_tv);
        final TextView studentCohortNumberTv = view.findViewById(R.id.student_cohort_num_tv);
        TextView studentSpecializationTv = view.findViewById(R.id.student_specialization_tv);
        TextView studentPhoneNumber = view.findViewById(R.id.student_phone_number_tv);
        Button viewResumeButton = view.findViewById(R.id.view_resume_button);
        final ProgressBar loadingPicProgressBar = view.findViewById(R.id.loading_progress_bar);

        if (student.getProfileImageurl() != null) {
            loadingPicProgressBar.setVisibility(View.VISIBLE);
            Picasso.with(getContext())
                    .load(student.getProfileImageurl())
                    .resize(1444, 1444)
                    .centerCrop()
                    .transform(new RoundPicasso(studentImageView.getMaxHeight(), 0))
                    .into(studentImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            loadingPicProgressBar.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            //todo handle error
                        }
                    });
        }


        String fullName = student.getFirstName() + " " + student.getLastName();

        studentFullNameTv.setText(fullName);
        studentDisplayNameTv.setText(student.getDisplayName());
        studentEmailTv.setText(student.getEmail());
        studentCohortNumberTv.setText(String.valueOf(student.getCohort()));
        studentSpecializationTv.setText(student.getSpecialization());
        studentPhoneNumber.setText(student.getPhone());

        viewResumeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                if (student.getResumeUrl() != null) {
                    mListener.sendResumeUrlToFragment(student.getResumeUrl());
                }else{
                    Toast.makeText(getActivity(), getText(R.string.no_resume), Toast.LENGTH_SHORT).show();
                }

            }

        });

    }

//==========================================ON FRAGMENT LISTENER====================================
public interface OnStudentProfileListener{
    void sendResumeUrlToFragment(String url);
}
//==========================================UTILITY METHOD========================================

    public void setStudent(Student student) {
        this.student = student;
    }



}
