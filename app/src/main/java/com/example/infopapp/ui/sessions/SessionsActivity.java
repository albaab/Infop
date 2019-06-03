package com.example.infopapp.ui.sessions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.graphics.Color;
import android.os.Bundle;

import com.example.infopapp.R;

import com.example.infopapp.ui.sessions.fragments.ListOfSessionsFragment;
import com.example.infopapp.ui.sessions.fragments.SelectedSessionFragment;

import static com.example.infopapp.utils.Constants.MOBILE_DEV_TITLE;

public class SessionsActivity extends AppCompatActivity implements ListOfSessionsFragment.ListOfSessionsFragmentListener {

    private SelectedSessionFragment selectedSessionFragment;

    private FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        setTitle(MOBILE_DEV_TITLE);
        setTitleColor(Color.BLACK);
        ListOfSessionsFragment listOfSessionsFragment = new ListOfSessionsFragment();
        goToFragment(listOfSessionsFragment);
        selectedSessionFragment = new SelectedSessionFragment();

    }

    private void goToFragment(Fragment fragment){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.session_activity_container,fragment);
        if(!(fragment instanceof ListOfSessionsFragment)){
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }


    @Override
    public void goToSelectedSession(Bundle bundle) {
        selectedSessionFragment.setArguments(bundle);
        goToFragment(selectedSessionFragment);

    }




}
