package com.btp.chealth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.btp.chealth.R;
import com.google.firebase.auth.FirebaseAuth;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            Intent i;
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                i = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                i = new Intent(SplashActivity.this, LoginActivity.class);
            }
            startActivity(i);
            finish();
        }, 1500);
    }
}
