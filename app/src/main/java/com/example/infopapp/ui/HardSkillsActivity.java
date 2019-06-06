package com.example.infopapp.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.infopapp.R;
import com.example.infopapp.ui.sessions.SessionsActivity;

import static com.example.infopapp.utils.Constants.DATA_SCIENCE;
import static com.example.infopapp.utils.Constants.HARD_SKILLS;
import static com.example.infopapp.utils.Constants.MOBILE_DEV;
import static com.example.infopapp.utils.Constants.WEB_DEV;

public class HardSkillsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard_skills);
        setTitle(R.string.HARD_SKILLS);
    }

    public void goToMobileSessions(View view) {

        Intent intent = new Intent(this, SessionsActivity.class);

        switch (view.getId()) {
            case R.id.mobile_image:
                intent.putExtra(HARD_SKILLS, MOBILE_DEV);
                break;
            case R.id.web_image:
                intent.putExtra(HARD_SKILLS, WEB_DEV);
                break;
            case R.id.data_science_image:
                intent.putExtra(HARD_SKILLS, DATA_SCIENCE);
                break;
        }

        startActivity(intent);

    }
}
