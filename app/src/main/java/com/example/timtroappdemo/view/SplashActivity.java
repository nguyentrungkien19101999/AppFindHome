package com.example.timtroappdemo.view;

import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.timtroappdemo.Constant.GlobalFuntion;
import com.example.timtroappdemo.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getSupportActionBar().hide();

        goToMainActivity();
    }

    private void goToMainActivity() {
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                GlobalFuntion.startActivity(SplashActivity.this, LogInActivity.class);
                finish();
            }
        }, 3000);
    }
}
