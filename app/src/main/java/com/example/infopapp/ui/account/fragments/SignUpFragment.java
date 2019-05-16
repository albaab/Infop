package com.example.infopapp.ui.account.fragments;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.infopapp.ui.account.LoginView;
import com.example.infopapp.R;
import com.example.infopapp.ui.account.LogPresenter;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment implements LoginView {

    private EditText emailEdit, passwordEdit, confirmPasswordEdit;

    private ProgressBar progressBar;

    private String email, password, confirmPassword;

    private Bundle bundleCreateAccount = new Bundle();
    public CallBackCreateAccount callBackCreateAccount;

    private LogPresenter logPresenter;

    //============================================ public constructor================================//
    public SignUpFragment() {
        // Required empty public constructor
    }

//=====================================loginView methods==========================================//
    @Override
    public void setLoginStatus(String message) {
        bundleCreateAccount.putString("Registration_message", message);
        progressBar.setVisibility(View.INVISIBLE);
        emailEdit.setText("");
        passwordEdit.setText("");
        confirmPasswordEdit.setText("");
        callBackCreateAccount.startHomeActivityFromSignUp(bundleCreateAccount);

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
        confirmPasswordEdit.setError(confirmError);
        confirmPasswordEdit.requestFocus();
        confirmPasswordEdit.setText("");
    }

//============================================CallBack interface===================================//
    public interface CallBackCreateAccount {
        void startHomeActivityFromSignUp(Bundle bundle);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callBackCreateAccount = (CallBackCreateAccount) context;
    }

//===================================== on Create View method======================================//
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

//===================================== OnViewCreated method======================================//
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        logPresenter = new LogPresenter(this);

        //retrieve bundle info from signIn interface
        bundleCreateAccount = getArguments();

        //retrieve View Items from the fragment layout
        //===================================== attributes======================================//
        ImageView signUpImage = view.findViewById(R.id.sign_in_image);
        TextView signUpTextView = view.findViewById(R.id.sign_up_text_view);
        emailEdit = view.findViewById(R.id.email_edit_sign_in);
        passwordEdit = view.findViewById(R.id.password_edit);
        confirmPasswordEdit = view.findViewById(R.id.confirm_password_edit);

        emailEdit.clearFocus();
        passwordEdit.clearFocus();
        confirmPasswordEdit.clearFocus();

        Button signUpButton = view.findViewById(R.id.sign_up_button);
        progressBar = view.findViewById(R.id.progressBar_sign_up);

    //=============================Sign up Button Pressed==========================================//
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailEdit.getText().toString().trim();
                password = passwordEdit.getText().toString().trim();
                confirmPassword = confirmPasswordEdit.getText().toString().trim();

//                bundleCreateAccount.putString("new_user_email", email);
//                bundleCreateAccount.putString("new_user_password", password);

                if(logPresenter.registerUser(email, password, confirmPassword)){
                    progressBar.setVisibility(View.VISIBLE);
                }

            }
        });

        //===================================drawables================================================//
        Drawable talentDrawable = getResources().getDrawable(R.drawable.talent);
        Drawable staffDrawable = getResources().getDrawable(R.drawable.staff_edacy);
        Drawable instructorDrawable = getResources().getDrawable(R.drawable.ic_instructor);
        Drawable otherDrawable = getResources().getDrawable(R.drawable.ic_person);

        //=======================Adapt the sign Up Interface=======================================//
        if (bundleCreateAccount.getString("key").equals("Student")) {
            signUpImage.setImageDrawable(talentDrawable);
            signUpTextView.setText("Student");
            return;
        }
        if (bundleCreateAccount.getString("key").equals("Staff")) {
            signUpImage.setImageDrawable(staffDrawable);
            signUpTextView.setText("Staff");
            return;
        }
        if (bundleCreateAccount.getString("key").equals("Instructor")) {
            signUpImage.setImageDrawable(instructorDrawable);
            signUpTextView.setText("Instructor");
            return;
        }
        if (bundleCreateAccount.getString("key").equals("Other")) {
            signUpImage.setImageDrawable(otherDrawable);
            signUpTextView.setText("Other");
        }


    }

}