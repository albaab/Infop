package com.example.infopapp.ui.account.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.infopapp.db.FirebaseDb;
import com.example.infopapp.ui.account.LoginView;
import com.example.infopapp.R;
import com.example.infopapp.ui.account.LogPresenter;
import com.example.infopapp.ui.profile.ProfileView;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.utils.StringConstants.STATUS_KEY;


public class SignInFragment extends Fragment implements LoginView, ProfileView {

//======================================Attributes=============================================//

    private EditText emailEdit, passwordEdit;
    private String emailSignIn, passwordSignIn;
    private ProgressBar progressBar;
    private LogPresenter logPresenter;
    private Bundle signInBundle = new Bundle();
    private CallBackSignInListener mCreateAccountCallback;

    //=======================================constructor(s)========================================//
    public SignInFragment() {
        // Required empty public constructor
    }

    //========================================LoginView methods====================================//

    @Override
    public void setLoginStatus(boolean isSuccessful, boolean isEmailVerified) {
        Log.d(TAG, "setLoginStatus: START LOGIN SUCCESSFUL");

//        FirebaseDb firebaseDb = new FirebaseDb(this);
//        firebaseDb.isUserInFirebaseDb();
        progressBar.setVisibility(View.INVISIBLE);
        signInBundle.putBoolean(STATUS_KEY, isSuccessful);
        progressBar.setVisibility(View.INVISIBLE);
        mCreateAccountCallback.startHomeActivityFromSingIn(signInBundle);

//        if (isEmailVerified) {
//            signInBundle.putBoolean(STATUS_KEY, isSuccessful);
//            progressBar.setVisibility(View.INVISIBLE);
//            mCreateAccountCallback.startHomeActivityFromSingIn(signInBundle);
//        } else {
//            Toast.makeText(getActivity(), verifyEmailMessage, Toast.LENGTH_SHORT).show();
//        }
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

    //    /-----------------------------------unused login view methods ----------------------------
    @Override
    public void setSignUpStatus(boolean isSuccessful, boolean isUserAlready) {
        //DO NOTHING
    }

    @Override
    public void setConfirmError(String confirmError) {
        //DO NOTHING
    }

    //-------------------------------------unused profile view method-------------------------------
    @Override
    public void setUploadProfilePic(boolean myBoolean, int visibility) {

    }

    @Override
    public void setUploadUserToDbStatus(boolean myBoolean) {

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
        if (context instanceof CallBackSignInListener) {
            mCreateAccountCallback = (CallBackSignInListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement SignInListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
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
        String verifyEmailMessage = (String) getActivity().getResources().getText(R.string.verify_email);
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

                if (logPresenter.signUserIn(emailSignIn, passwordSignIn)) {
                    progressBar.setVisibility(View.VISIBLE);
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
