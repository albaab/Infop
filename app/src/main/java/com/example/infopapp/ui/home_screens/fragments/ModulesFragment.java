package com.example.infopapp.ui.home_screens.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.infopapp.R;
import com.example.infopapp.ui.HardSkillsActivity;


public class ModulesFragment extends Fragment {

//=======================================CONSTRUCTOR================================================
    public ModulesFragment() {
        // Required empty public constructor
    }


//=======================================ON CREATE VIEW=============================================
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_modules, container, false);
    }


//=======================================ON VIEW CREATED============================================
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView hardSkills, softSkills, employability;
        hardSkills = view.findViewById(R.id.hard_skills_image);
        softSkills = view.findViewById(R.id.soft_skills_image);
        employability = view.findViewById(R.id.employability_image);

        hardSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HardSkillsActivity.class);
                startActivity(intent);
            }
        });
        softSkills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), SoftSkillsActivity.class);
//                startActivity(intent);
            }
        });
        employability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), EmployabilityActivity.class);
//                startActivity(intent);
            }
        });

    }


}
