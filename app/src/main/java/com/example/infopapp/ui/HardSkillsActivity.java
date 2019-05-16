package com.example.infopapp.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.infopapp.R;
import com.example.infopapp.ui.sessions.SessionsActivity;

public class HardSkillsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_skills);
        setTitle(R.string.HARD_SKILLS);
    }

    public void goToMobileSessions(View view) {
        Intent intent = new Intent(this, SessionsActivity.class);
        startActivity(intent);
    }
}
