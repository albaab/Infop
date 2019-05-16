package com.example.infopapp.ui.account;

import android.content.Intent;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.ui.account.fragments.ChooseAccountFragment;
import com.example.infopapp.ui.account.fragments.ResetPasswordFragment;
import com.example.infopapp.ui.account.fragments.SignUpFragment;
import com.example.infopapp.ui.account.fragments.SignInFragment;
import com.example.infopapp.ui.home_screens.HomeActivity;

public class MainActivity extends AppCompatActivity implements
        ChooseAccountFragment.CallBackChooseAccount,
        SignInFragment.CallBackSignInListener, SignUpFragment.CallBackCreateAccount,
        ResetPasswordFragment.ResetPasswordFragmentListener {

    //=====================================private attributes======================================//
    private Bundle mainBundle = new Bundle();
    //fragments
    private ChooseAccountFragment chooseAccountFragment = new ChooseAccountFragment();
    private SignInFragment signInFragment;
    private SignUpFragment signUpFragment;
    private ResetPasswordFragment resetPasswordFragment;
    //fragment manager
    private FragmentManager manager = getSupportFragmentManager();

    //presenters

    //=====================================On create method=======================================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        signInFragment = new SignInFragment();
        signUpFragment = new SignUpFragment();
        resetPasswordFragment = new ResetPasswordFragment();

        switchToFragment(signInFragment);

    }

    protected void switchToFragment(Fragment fragment){
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
          ||fragment instanceof ResetPasswordFragment ) {
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
    public void startHomeActivityFromSingIn(Bundle bundle) {
        String messageSignInSuccess = bundle.getString("Sign_in_success_message");
//        String userEmail = bundle.getString("new_user_email");
//        String userPassword = bundle.getString("new_user_password");
        if (messageSignInSuccess != null){
            Toast.makeText(this, messageSignInSuccess, Toast.LENGTH_SHORT).show();
            bundle.clear();

            startActivity( new Intent(this, HomeActivity.class));
        }
    }

    @Override
    public void switchToResetPasswordFragment() {
        switchToFragment(resetPasswordFragment);
    }
    //=====================================send User info methods====================================//

    @Override
    public void startHomeActivityFromSignUp(Bundle bundle) {

        String messageSuccess = bundle.getString("Registration_message");
//        // email and password attributes
//        String userEmail = bundle.getString("new_user_email");
//        String userPassword = bundle.getString("new_user_password");
        if (messageSuccess != (null)){
            Toast.makeText(this, messageSuccess, Toast.LENGTH_SHORT).show();
            bundle.clear();
            startActivity(new Intent(this, HomeActivity.class));
        }

    }


    @Override
    public void goBackToSignInFragment() {
        switchToFragment(signInFragment);
    }
}
