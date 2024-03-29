package com.example.aichathub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ImageView logo = findViewById(R.id.logo);
        new Handler().postDelayed(() ->{
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
//            Animation animation = new Animation() {
//            }
//            logo.startAnimation();
        finish();
        },1000);
    }
}