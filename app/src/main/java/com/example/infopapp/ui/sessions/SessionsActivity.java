package com.example.infopapp.ui.sessions;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

import com.example.infopapp.R;
import com.example.infopapp.entities.Session;
import com.example.infopapp.ui.sessions.fragments.ListOfSessionsFragment;
import com.example.infopapp.ui.sessions.fragments.SelectedSessionFragment;

public class SessionsActivity extends AppCompatActivity implements ListOfSessionsFragment.ListOfSessionsFragmentListener {

    private SelectedSessionFragment selectedSessionFragment;

    private FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);
        setTitle("MOBILE DEVELOPMENT");
        ListOfSessionsFragment listOfSessionsFragment = new ListOfSessionsFragment();
        goToFragment(listOfSessionsFragment);
        selectedSessionFragment = new SelectedSessionFragment();

    }

    private void goToFragment(Fragment fragment){
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.session_activity_container,fragment);
        transaction.commit();
    }


    @Override
    public void goToSelectedSession(Bundle bundle) {
        // TODO go to selected Session Fragment
        selectedSessionFragment.setArguments(bundle);
        goToFragment(selectedSessionFragment);

    }




}
