package com.example.infopapp.ui.account.fragments;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infopapp.db.FirebaseDb;
import com.example.infopapp.entities.Instructor;
import com.example.infopapp.entities.Staff;
import com.example.infopapp.entities.Student;
import com.example.infopapp.entities.User;
import com.example.infopapp.ui.account.LoginView;
import com.example.infopapp.R;
import com.example.infopapp.ui.account.LogPresenter;
import com.example.infopapp.ui.home_screens.HomeActivity;
import com.example.infopapp.ui.profile.ProfileView;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.example.infopapp.ui.account.ConnectToAccountActivity.thisUser;
import static com.example.infopapp.utils.StringConstants.KEY;
import static com.example.infopapp.utils.StringConstants.INSTRUCTOR;
import static com.example.infopapp.utils.StringConstants.INSTRUCTOR_TOKEN;
import static com.example.infopapp.utils.StringConstants.GUEST;
import static com.example.infopapp.utils.StringConstants.STAFF;
import static com.example.infopapp.utils.StringConstants.STAFF_TOKEN;
import static com.example.infopapp.utils.StringConstants.STUDENT;
import static com.example.infopapp.utils.StringConstants.STUDENT_TOKEN;


public class SignUpFragment extends Fragment implements LoginView, ProfileView {

    private EditText emailEdit, passwordEdit, confirmPasswordEdit, userTokenEdit;

    private ProgressBar progressBar;

    private String email, password, confirmPassword, userToken;

    private Bundle bundleCreateAccount = new Bundle();

//    private CallBackCreateAccount callBackCreateAccount;

    private LogPresenter logPresenter;

    private String tokenMessage;
    private String successMessage;
    private String userAlreadyRegisteredMessage;
    private String signUpFailMessage;

    private FirebaseDb firebaseDb;


    //============================================ public constructor================================//
    public SignUpFragment() {
        // Required empty public constructor
    }

    //=====================================loginView methods==========================================//
    @Override
    public void setLoginStatus(boolean isSuccessful, boolean isEmailVerified) {
        //do nothing
    }

    @Override
    public void setSignUpStatus(boolean isSuccessful, boolean isUserAlreadyRegistered) {

        progressBar.setVisibility(View.INVISIBLE);

        if (isSuccessful) {

            Toast.makeText(getActivity(), successMessage, Toast.LENGTH_SHORT).show();
            emailEdit.setText("");
            passwordEdit.setText("");
            confirmPasswordEdit.setText("");
            userTokenEdit.setText("");

            firebaseDb.uploadUserToFirebaseDb(thisUser);

            Intent connectIntent = new Intent(getActivity(), HomeActivity.class);
            connectIntent.putExtra(KEY, thisUser.getUserType());
            startActivity(connectIntent);
            getActivity().finish();


        } else if (isUserAlreadyRegistered) {
            Toast.makeText(getActivity(), userAlreadyRegisteredMessage, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), signUpFailMessage, Toast.LENGTH_SHORT).show();
        }

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
//    public interface CallBackCreateAccount {
//        void startHomeActivityFromSignUp(Bundle bundle);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof CallBackCreateAccount) {
//            callBackCreateAccount = (CallBackCreateAccount) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnSignUpInteractionListener");
//        }
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
        Log.d(TAG, "onViewCreated: START SIGN UP SUCCESSFUL");
        super.onViewCreated(view, savedInstanceState);
        logPresenter = new LogPresenter(this);
        firebaseDb = new FirebaseDb(this);
        successMessage = (String) getActivity().getResources().getText(R.string.success);
        userAlreadyRegisteredMessage = getActivity().getResources()
                .getString(R.string.user_already_registered);
        signUpFailMessage = getActivity().getResources().getString(R.string.error_message);
        tokenMessage = (String) getActivity().getResources().getText(R.string.token_message);

        //retrieve bundle info from signIn interface
        bundleCreateAccount = getArguments();

        //retrieve View Items from the fragment layout
        //===================================== attributes======================================//
        ImageView signUpImage = view.findViewById(R.id.sign_in_image);
        TextView signUpTextView = view.findViewById(R.id.sign_up_text_view);
        emailEdit = view.findViewById(R.id.email_edit_sign_in);
        passwordEdit = view.findViewById(R.id.password_edit);
        confirmPasswordEdit = view.findViewById(R.id.confirm_password_edit);
        userTokenEdit = view.findViewById(R.id.user_token_edit);

        emailEdit.clearFocus();
        passwordEdit.clearFocus();
        confirmPasswordEdit.clearFocus();
        userTokenEdit.clearFocus();

        Button signUpButton = view.findViewById(R.id.sign_up_button);
        progressBar = view.findViewById(R.id.progressBar_sign_up);

        //=============================Sign up Button Pressed==========================================//
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailEdit.getText().toString().trim();
                password = passwordEdit.getText().toString().trim();
                confirmPassword = confirmPasswordEdit.getText().toString().trim();
                userToken = userTokenEdit.getText().toString().trim();

                if (doesUserhaveAccess(bundleCreateAccount.getString(KEY), userToken)) {
                    getUserType(userToken);
                    if (logPresenter.registerUser(email, password, confirmPassword)) {
                        progressBar.setVisibility(View.VISIBLE);
                    }
                } else {
                    userTokenEdit.setError(tokenMessage);
                    userTokenEdit.requestFocus();
                }
            }
        });

        //===================================drawables================================================//
        Drawable talentDrawable = getResources().getDrawable(R.drawable.talent);
        Drawable staffDrawable = getResources().getDrawable(R.drawable.staff_edacy);
        Drawable instructorDrawable = getResources().getDrawable(R.drawable.ic_instructor);
        Drawable otherDrawable = getResources().getDrawable(R.drawable.ic_person);

        //=======================Adapt the sign Up Interface=======================================//
        if (bundleCreateAccount.getString(KEY).equals(STUDENT)) {
            signUpImage.setImageDrawable(talentDrawable);
            signUpTextView.setText(R.string.student);
            return;
        }
        if (bundleCreateAccount.getString(KEY).equals(STAFF)) {
            signUpImage.setImageDrawable(staffDrawable);
            signUpTextView.setText(R.string.staff);
            return;
        }
        if (bundleCreateAccount.getString(KEY).equals(INSTRUCTOR)) {
            signUpImage.setImageDrawable(instructorDrawable);
            signUpTextView.setText(R.string.instructor);
            return;
        }
        if (bundleCreateAccount.getString(KEY).equals(GUEST)) {
            signUpImage.setImageDrawable(otherDrawable);
            signUpTextView.setText(R.string.guest);
            userTokenEdit.setVisibility(View.GONE);
        }
    }
//===================================UTILITY METHODS================================================

    //---------------------------------does user have access method --------------------------------
    private boolean doesUserhaveAccess(String key, String token) {
        if (!TextUtils.isEmpty(token)) {
            switch (key) {
                case STUDENT:
                    return (token.equals(STUDENT_TOKEN));
                case INSTRUCTOR:
                    return (token.equals(INSTRUCTOR_TOKEN));
                case STAFF:
                    return (token.equals(STAFF_TOKEN));
                case GUEST:
                    return true;
                default:
                    return false;
            }
        } else {
            userTokenEdit.setError(tokenMessage);
            userTokenEdit.requestFocus();
            return false;
        }
    }

    //---------------------------------get the User type with the token-----------------------------
    private void getUserType(String token) {
        if (!TextUtils.isEmpty(token)) {
            switch (token) {
                case STUDENT_TOKEN:
                    thisUser = new Student();
                    break;
                case INSTRUCTOR_TOKEN:
                    thisUser = new Instructor();
                    break;
                case STAFF_TOKEN:
                    thisUser = new Staff();
//                    FirebaseDb.getAllStudentsFromFirebase();
                    break;
               default:
                   thisUser = new User();
                   thisUser.setUserType(GUEST);
                   break;
            }
        } else {
            thisUser = null;
        }
    }

    @Override
    public void setUploadProfilePic(boolean myBoolean, int visibility) {

    }

    @Override
    public void setUploadUserToDbStatus(boolean myBoolean) {

    }
}