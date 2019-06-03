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
import android.widget.Toast;

import com.example.infopapp.databases.FirebaseUserDb;
import com.example.infopapp.entities.Instructor;
import com.example.infopapp.entities.Staff;
import com.example.infopapp.entities.Student;
import com.example.infopapp.entities.User;
import com.example.infopapp.ui.account.LoginView;
import com.example.infopapp.R;
import com.example.infopapp.ui.account.LogPresenter;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.USERTYPE;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisInstructor;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisStaff;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisStudent;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisUser;
import static com.example.infopapp.utils.Constants.GUEST;
import static com.example.infopapp.utils.Constants.INSTRUCTOR;
import static com.example.infopapp.utils.Constants.STAFF;
import static com.example.infopapp.utils.Constants.STATUS_KEY;
import static com.example.infopapp.utils.Constants.STUDENT;


public class SignInFragment extends Fragment implements LoginView, FirebaseUserDb.FirebaseDbCallBack {

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

        if (isSuccessful){
            FirebaseUserDb.isUserInFirebaseDb(thisUser,this);
        }else{
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(),getText(R.string.login_fail_message) , Toast.LENGTH_SHORT).show();
        }

//        if (isEmailVerified) {
//            signInBundle.putBoolean(STATUS_KEY, isSuccessful);
//            progressBar.setVisibility(View.INVISIBLE);
//            mCreateAccountCallback.startHomeActivityFromSingIn(signInBundle);
//        } else {
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

    //==============================Firebase Db Interface methods===================================
    @Override
    public void onCallBack(List<Student> studentList) {
        //do nothing
    }

    @Override
    public void onCallBackGetUser(User user, Student student, Staff staff, Instructor instructor) {
        if (user != null) {
            thisUser = user;
            USERTYPE = GUEST;
            thisStudent = null;
            thisStaff = null;
            thisInstructor = null;
        } else if (student != null) {
            thisStudent = student;
            USERTYPE = STUDENT;
            thisUser = null;
            thisStaff = null;
            thisInstructor = null;
        } else if (staff != null) {
            thisStaff = staff;
            USERTYPE = STAFF;
            thisStudent = null;
            thisUser = null;
            thisInstructor = null;
        } else if (instructor != null) {
            thisInstructor = instructor;
            USERTYPE = INSTRUCTOR;
            thisStudent = null;
            thisStaff = null;
            thisUser = null;
        }
        progressBar.setVisibility(View.INVISIBLE);
        signInBundle.putBoolean(STATUS_KEY, true);
        mCreateAccountCallback.startHomeActivityFromSingIn(signInBundle);
    }


    //================================CallBack Sign in Fragment interface===========================
    public interface CallBackSignInListener {
        void switchToChooseAccountFragment();

        void startHomeActivityFromSingIn(Bundle bundle);

        void switchToResetPasswordFragment();
    }

    //==========================================On Attach==========================================//

    @Override
    public void onAttach(@NonNull Context context) {
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
        //retrieve view items of the fragment layout

        TextView createAccountLink = view.findViewById(R.id.create_account_link);
        TextView resetPasswordLink = view.findViewById(R.id.forgot_password_link);
        progressBar = view.findViewById(R.id.progressBar_sign_in);

        emailEdit = view.findViewById(R.id.email_edit_sign_in);
        passwordEdit = view.findViewById(R.id.password_edit_sign_in);

        final Button signInButton = view.findViewById(R.id.sign_in_button);


//------------------------------SIGN IN BUTTON CLICK LISTENER---------------------------------------
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
//------------------------------RESET PASSWORD LINK CLICK LISTENER----------------------------------
        resetPasswordLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateAccountCallback.switchToResetPasswordFragment();
            }
        });

//------------------------------CREATE ACCOUNT LINK CLICK LISTENER----------------------------------

        createAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateAccountCallback.switchToChooseAccountFragment();
            }
        });

    }
}
