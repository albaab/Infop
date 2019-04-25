package com.example.infopapp.activities.fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.infopapp.R;
import com.example.infopapp.presenters.LogPresenter;
import com.example.infopapp.views.LoginView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import java.io.FileInputStream;

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
    public CallBackSignIn mCreateAccountCallback;


    //=======================================constructor(s)========================================//
    public SignInFragment() {
        // Required empty public constructor
    }

    //========================================LoginView methods====================================//

    @Override
    public void setLoginStatus(String message) {
        signInBundle.putString("Sign_in_success_message", message);
        progressBar.setVisibility(View.INVISIBLE);
        mCreateAccountCallback.sendUserInfoSignIn(signInBundle);
    }

    @Override
    public void setPasswordError(String passwordError) {
        passwordEdit.setError(passwordError);
        passwordEdit.requestFocus();
        return;
    }

    @Override
    public void setEmailError(String emailError) {
        emailEdit.setError(emailError);
        emailEdit.requestFocus();
        return;
    }

    @Override
    public void setConfirmError(String confirmError) {
        // do nothing
    }

    //=========================================CallBack interface==================================//
    public interface CallBackSignIn {
        void switchToCreateAccountFragment(Bundle bundle);

        void sendUserInfoSignIn(Bundle bundle);
    }

    //==========================================On Attach==========================================//

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        FirebaseApp.initializeApp(context);
        mCreateAccountCallback = (CallBackSignIn) context;
    }

    //=====================================On create===============================================//

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);

    }

    //==========================================On View Created========================================//
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //retrieve bundle from createAccountFragment
        signInBundle = getArguments();
        logPresenter = new LogPresenter(this);

        //retrieve view items of the fragment layout

        ImageView singInImage = view.findViewById(R.id.signIn_image);
        TextView signInTextView = view.findViewById(R.id.sign_in_text_view);
        TextView createAccountLink = view.findViewById(R.id.create_account_link);
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

                if (logPresenter.signUserIn(emailSignIn, passwordSignIn) == true) {
                    progressBar.setVisibility(View.VISIBLE);
                    emailEdit.setText("");
                    passwordEdit.setText("");
                }


            }
        });


        createAccountLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCreateAccountCallback.switchToCreateAccountFragment(signInBundle);
            }
        });


        Drawable talentDrawable = getResources().getDrawable(R.drawable.talent);
        Drawable staffDrawable = getResources().getDrawable(R.drawable.staff_edacy);
        Drawable instructorDrawable = getResources().getDrawable(R.drawable.instructor);
        Drawable otherDrawable = getResources().getDrawable(R.drawable.ic_instructor);
//============================================adapt the layout ====================================//
        if (signInBundle.getString("key").equals("Student")) {
            singInImage.setImageDrawable(talentDrawable);
            signInTextView.setText("Student");
            return;
        }
        if (signInBundle.getString("key").equals("Staff")) {
            singInImage.setImageDrawable(staffDrawable);
            signInTextView.setText("Staff");
            return;
        }
        if (signInBundle.getString("key").equals("Instructor")) {
            singInImage.setImageDrawable(instructorDrawable);
            signInTextView.setText("Instructor");
            return;
        }
        if (signInBundle.getString("key").equals("Other")) {
            singInImage.setImageDrawable(otherDrawable);
            signInTextView.setText("Other");
        }
    }
}
