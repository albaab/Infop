package com.example.infopapp.Login;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.infopapp.R;

public class SplashScreenActivity extends AppCompatActivity {

    public static final int SPLASH_SCREEN_DURATION = 3610;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent startIntent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(startIntent);
                finish();
            }
        }, SPLASH_SCREEN_DURATION);
    }
}
