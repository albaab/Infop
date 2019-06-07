package com.example.infopapp.ui;

import android.content.Intent;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.infopapp.R;
import com.example.infopapp.ui.account.ConnectToAccountActivity;
import com.example.infopapp.ui.home_screens.HomeActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    public static final int SPLASH_SCREEN_DURATION = 3610;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent startIntent = new Intent(SplashScreenActivity.this
                        , ConnectToAccountActivity.class);
                startActivity(startIntent);

                finish();

            }
        }, SPLASH_SCREEN_DURATION);
    }
}
