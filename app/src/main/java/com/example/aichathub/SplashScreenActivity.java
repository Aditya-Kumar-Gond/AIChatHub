package com.example.aichathub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {
FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        ImageView logo = findViewById(R.id.logo);
        new Handler().postDelayed(() ->{
            if(auth.getCurrentUser() !=null){
                startActivity(new Intent(this,MainActivity.class));
            }else {
            startActivity(new Intent(SplashScreenActivity.this, LoginActivityActivity.class));
          }

//            Animation animation = new Animation() {
//            }
//            logo.startAnimation();
        finish();
        },1000);
    }
}