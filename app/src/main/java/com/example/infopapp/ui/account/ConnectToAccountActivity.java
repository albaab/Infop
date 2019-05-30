package com.example.infopapp.ui.account;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.db.FirebaseDb;
import com.example.infopapp.entities.Instructor;
import com.example.infopapp.entities.Staff;
import com.example.infopapp.entities.Student;
import com.example.infopapp.entities.User;
import com.example.infopapp.ui.account.fragments.ChooseAccountFragment;
import com.example.infopapp.ui.account.fragments.ResetPasswordFragment;
import com.example.infopapp.ui.account.fragments.SignUpFragment;
import com.example.infopapp.ui.account.fragments.SignInFragment;
import com.example.infopapp.ui.home_screens.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import static com.example.infopapp.utils.StringConstants.GUEST;
import static com.example.infopapp.utils.StringConstants.INSTRUCTOR;
import static com.example.infopapp.utils.StringConstants.STAFF;
import static com.example.infopapp.utils.StringConstants.STATUS_KEY;
import static com.example.infopapp.utils.StringConstants.STUDENT;

public class ConnectToAccountActivity extends AppCompatActivity implements
        ChooseAccountFragment.CallBackChooseAccount,
        SignInFragment.CallBackSignInListener, FirebaseDb.FirebaseDbCallBack {

    private static final String TAG = "Signing in";
    //=====================================private attributes======================================//
    //fragments
    private ChooseAccountFragment chooseAccountFragment = new ChooseAccountFragment();
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;
    private ResetPasswordFragment resetPasswordFragment;
    //fragment manager
    private FragmentManager manager = getSupportFragmentManager();

    //static user attribute
    public static User thisUser;
    public static Student thisStudent;
    public static Staff thisStaff;
    public static Instructor thisInstructor;
    public static String USERTYPE = "";

    //=====================================On create method=======================================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: START ON CREATE METHOD");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        thisUser = new User();
        thisStudent = new Student();
        thisStaff = new Staff();
        thisInstructor = new Instructor();

        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
        resetPasswordFragment = new ResetPasswordFragment();

        switchToFragment(signInFragment);

    }

    protected void switchToFragment(Fragment fragment) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_activity, fragment);
        transaction.commit();
    }

    //=====================================Return button events======================================//
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            getPreviousFragment();
        }
        return true;
    }

    public void getPreviousFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_activity);
        if (fragment instanceof ChooseAccountFragment
                || fragment instanceof ResetPasswordFragment) {
            switchToFragment(signInFragment);
        }
        if (fragment instanceof SignUpFragment) {
            switchToFragment(chooseAccountFragment);
        }
    }

//=====================================Switching from fragment to fragment methods==============//
    @Override
    public void switchToSignUpFragment(Bundle bundle) {
        signUpFragment.setArguments(bundle);
        switchToFragment(signUpFragment);
    }


    @Override
    public void switchToChooseAccountFragment() {
        switchToFragment(chooseAccountFragment);
    }


    @Override
    public void switchToResetPasswordFragment() {
        switchToFragment(resetPasswordFragment);
    }

//=====================================Sign In and Sign up methods==================================
    @Override
    public void startHomeActivityFromSingIn(Bundle bundle) {

        Log.d(TAG, "startHomeActivityFromSingIn: START SIGNING IN");
        boolean isSuccessful = bundle.getBoolean(STATUS_KEY);
        String signInSuccessMessage = this.getResources().getString(R.string.login_success_message);
        String signInFailMessage = this.getResources().getString(R.string.login_fail_message);

        if (isSuccessful) {
            Toast.makeText(this, signInSuccessMessage, Toast.LENGTH_SHORT).show();
            Intent signInIntent = new Intent(this, HomeActivity.class);
            Log.d(TAG, "START GO TO HOME ACTIVITY FROM SIGN IN: "
                    + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
            startActivity(signInIntent);
            finish();
        } else {
            Toast.makeText(this, signInFailMessage, Toast.LENGTH_SHORT).show();
        }
        bundle.clear();
    }

    //=====================================On start method==========================================
    @Override
    protected void onStart() {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        Log.d(TAG, "onStart: START THE CONNECT TO ACCOUNT ACTIVITY");
        super.onStart();
        if (fbUser != null) {
            thisUser.setId(fbUser.getUid());
            thisUser.setUserType(fbUser.getDisplayName());
            USERTYPE = fbUser.getDisplayName();
            FirebaseDb.isUserInFirebaseDb(thisUser,this);
            startActivity(new Intent(this, HomeActivity.class));
            finish();
        }
    }

    @Override
    public void onCallBack(List<Student> studentList) {

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
    }
}
