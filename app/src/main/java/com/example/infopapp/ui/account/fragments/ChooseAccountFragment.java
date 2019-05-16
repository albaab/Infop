package com.example.infopapp.ui.account.fragments;


import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.infopapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseAccountFragment extends Fragment {

    private SignInFragment signInFragment = new SignInFragment();
    private CallBackChooseAccount mCallBack;
    Bundle bundleChooseAccount = new Bundle();

    public ChooseAccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallBack = (CallBackChooseAccount) context;
    }

    public interface CallBackChooseAccount {
        void switchToSignUpFragment(Bundle bundle);
}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_account, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView talentImage = view.findViewById(R.id.talent_image);
        ImageView staffImage = view.findViewById(R.id.staff_image);
        ImageView instructorImage = view.findViewById(R.id.instructor_image);
        ImageView otherImage = view.findViewById(R.id.other_image);

        talentImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundleChooseAccount.putString("key","Student");
                mCallBack.switchToSignUpFragment(bundleChooseAccount);
            }
        });
        staffImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundleChooseAccount.putString("key","Staff");
                mCallBack.switchToSignUpFragment(bundleChooseAccount);
            }
        });
        instructorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundleChooseAccount.putString("key","Instructor");
                mCallBack.switchToSignUpFragment(bundleChooseAccount);
            }
        });
        otherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundleChooseAccount.putString("key","Other");
                mCallBack.switchToSignUpFragment(bundleChooseAccount);
            }
        });
    }
}
