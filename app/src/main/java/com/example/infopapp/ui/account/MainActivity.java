package com.example.infopapp.Login;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.Login.fragments.ChooseAccountFragment;
import com.example.infopapp.Login.fragments.CreateAccountFragment;
import com.example.infopapp.Login.fragments.SignInFragment;

public class MainActivity extends AppCompatActivity implements
        ChooseAccountFragment.CallBackChooseAccount,
        SignInFragment.CallBackSignIn, CreateAccountFragment.CallBackCreateAccount {

    //=====================================private attributes======================================//
    private Bundle mainBundle = new Bundle();
    //fragments
    private ChooseAccountFragment chooseAccountFragment = new ChooseAccountFragment();
    private SignInFragment signInFragment = new SignInFragment();
    private CreateAccountFragment createAccountFragment = new CreateAccountFragment();
    private Fragment fragment;
    //fragment manager
    private FragmentManager manager = getSupportFragmentManager();

    //presenters

    //=====================================On create method=======================================//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.main_activity, chooseAccountFragment);
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
        fragment = getSupportFragmentManager().findFragmentById(R.id.main_activity);
        if (fragment instanceof SignInFragment) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_activity, chooseAccountFragment);
            transaction.commit();
        }
        if (fragment instanceof CreateAccountFragment) {
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.main_activity, signInFragment);
            transaction.commit();
        }

    }

    //=====================================Switching from fragment to fragment methods==============//

    @Override
    public void switchToSignInFragment(Bundle bundle) {
        signInFragment.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_activity, signInFragment);
        transaction.commit();
    }


    @Override
    public void switchToCreateAccountFragment(Bundle bundle) {
        createAccountFragment.setArguments(bundle);
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_activity, createAccountFragment);
        transaction.commit();
    }

    @Override
    public void sendUserInfoSignIn(Bundle bundle) {
        mainBundle = bundle;
        String messageSignInSuccess = mainBundle.getString("Sign_in_success_message");
        String userEmail = mainBundle.getString("new_user_email");
        String userPassword = mainBundle.getString("new_user_password");
        if (messageSignInSuccess != null){
            Toast.makeText(this, messageSignInSuccess, Toast.LENGTH_SHORT).show();
            mainBundle.clear();
        }
    }
    //=====================================send User info methods====================================//

    @Override
    public void sendUserInfoSignUp(Bundle bundle) {
        mainBundle = bundle;
        String messageSuccess = mainBundle.getString("Registration_message");

        // email and password attributes
        String userEmail = mainBundle.getString("new_user_email");
        String userPassword = mainBundle.getString("new_user_password");
        if (messageSuccess != (null)){
            Toast.makeText(this, messageSuccess, Toast.LENGTH_SHORT).show();
            mainBundle.clear();
        }

    }


}
