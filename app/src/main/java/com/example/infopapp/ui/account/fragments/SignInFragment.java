package com.example.infopapp.ui.account.fragments;


import android.content.Context;
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
import android.widget.TextView;

import com.example.infopapp.ui.account.LoginView;
import com.example.infopapp.R;
import com.example.infopapp.ui.account.LogPresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends Fragment implements LoginView {

    //======================================Attributes=============================================//

    private EditText emailEdit, passwordEdit;
    private String emailSignIn, passwordSignIn;
    private ProgressBar progressBar;
    private LogPresenter logPresenter;
    private Bundle signInBundle = new Bundle();
    public CallBackSignInListener mCreateAccountCallback;


    //=======================================constructor(s)========================================//
    public SignInFragment() {
        // Required empty public constructor
    }

    //========================================LoginView methods====================================//

    @Override
    public void setLoginStatus(String message) {
        signInBundle.putString("Sign_in_success_message", message);
        progressBar.setVisibility(View.INVISIBLE);
        mCreateAccountCallback.startHomeActivityFromSingIn(signInBundle);
    }

    @Override
    public void setPasswordError(String passwordError) {
        passwordEdit.setError(passwordError);
        passwordEdit.requestFocus();
    }

    @Override
    public void setEmailError(String emailError) {
        emailEdit.setError(emailError);
        emailEdit.requestFocus();
    }

    @Override
    public void setConfirmError(String confirmError) {
        // do nothing
    }

    //=========================================CallBack interface==================================//
    public interface CallBackSignInListener {
        void switchToChooseAccountFragment();
        void startHomeActivityFromSingIn(Bundle bundle);
        void switchToResetPasswordFragment();
    }

    //==========================================On Attach==========================================//

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof CallBackSignInListener){
            mCreateAccountCallback = (CallBackSignInListener) context;
        }else{
            throw new RuntimeException(context.toString()
                    + " must implement SignInListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCreateAccountCallback = null;
    }
    //=====================================On create===============================================//

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);

    }

    //==========================================On View Created========================================//
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //retrieve bundle from createAccountFragment
        logPresenter = new LogPresenter(this);

        //retrieve view items of the fragment layout

        TextView createAccountLink = view.findViewById(R.id.create_account_link);
        TextView resetPasswordLink = view.findViewById(R.id.forgot_password_link);
        progressBar = view.findViewById(R.id.progressBar_sign_in);

        emailEdit = view.findViewById(R.id.email_edit_sign_in);
        passwordEdit = view.findViewById(R.id.password_edit_sign_in);

        final Button signInButton = view.findViewById(R.id.sign_in_button);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                emailSignIn = emailEdit.getText().toString().trim();
                passwordSignIn = passwordEdit.getText().toString().trim();
                signInBundle.putString("user_email", emailSignIn);
                signInBundle.putString("user_password", passwordSignIn);

                if (logPresenter.signUserIn(emailSignIn, passwordSignIn)) {
                    progressBar.setVisibility(View.VISIBLE);
                    emailEdit.setText("");
                    passwordEdit.setText("");
                }


            }
        });

        resetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateAccountCallback.switchToResetPasswordFragment();
            }
        });



        createAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateAccountCallback.switchToChooseAccountFragment();
            }
        });

    }
}
