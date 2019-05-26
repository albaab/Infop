package com.example.infopapp.ui.account.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.ui.account.LogPresenter;
import com.example.infopapp.ui.account.LoginView;
import com.example.infopapp.ui.account.ConnectToAccountActivity;

public class ResetPasswordFragment extends Fragment implements LoginView {


    //===========================================Private Attributes=====================================
    private EditText emailEditReset;
    private LogPresenter logPresenter;
    private ProgressBar progressBar;
    private Context mContext;

    //===========================================constructor==========================================
    public ResetPasswordFragment() {
        // Required empty public constructor
    }

//===========================================onCreate==========================================

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //===========================================onCreateView==========================================
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.reset_password_fragment, container, false);
    }

    //=========================================onViewCreated============================================
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        emailEditReset = view.findViewById(R.id.email_edit_reset);
        Button resetButton = view.findViewById(R.id.reset_button);
        progressBar = view.findViewById(R.id.progressBar_reset);
        logPresenter = new LogPresenter(this);

//======================================reset Button pressed========================================

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (logPresenter.resetPassword(emailEditReset.getText().toString().trim())) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }
        });
    }

//==========================================onAttach================================================

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
//        if (context instanceof ResetPasswordFragmentListener) {
//            mListener = (ResetPasswordFragmentListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement ResetPasswordFragmentListener");
//        }
    }

    //==========================================onDetach================================================
    @Override
    public void onDetach() {
        super.onDetach();
    }

    //===================================== LoginView Methods==========================================
    @Override
    public void setLoginStatus(boolean isSuccessful, boolean isEmailVerified) {
        progressBar.setVisibility(View.INVISIBLE);
        String resetMessageSuccess = getActivity().getResources().getString(R.string.reset_password_message);
        String resetMessageFail = getActivity().getResources().getString(R.string.error_message);

        if (isSuccessful) {
            Toast.makeText(mContext, resetMessageSuccess, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getContext(), ConnectToAccountActivity.class));
        } else {
            Toast.makeText(mContext, resetMessageFail, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void setEmailError(String emailError) {
        emailEditReset.setError(emailError);
        emailEditReset.requestFocus();
    }

    //================================unused LoginView Methods==========================================
    @Override
    public void setPasswordError(String passwordError) {
    }

    @Override
    public void setSignUpStatus(boolean myboolean, boolean myBoolean) {
        //DO NOTHING
    }

    @Override
    public void setConfirmError(String confirmError) {
    }

}
