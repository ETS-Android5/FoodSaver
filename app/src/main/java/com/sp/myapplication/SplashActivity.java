package com.sp.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sp.myapplication.login.LoginActivity;
import com.sp.myapplication.mains.WhyActivity;

import org.w3c.dom.Text;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIMEOUT = 2000;
    private TextView Splash_Text;
    private ImageView Splash_Icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Splash_Text = findViewById(R.id.splash_foodsaver);
        Splash_Icon = findViewById(R.id.splash_fs);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
        Splash_Icon.startAnimation(scaleAnimation);
        Splash_Text.startAnimation(scaleAnimation);

        MediaPlayer jingle = MediaPlayer.create(SplashActivity.this,R.raw.splash_jingle);
        jingle.start();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                Intent homeIntent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(homeIntent);
                finish();
                jingle.stop();
            }
        }, SPLASH_TIMEOUT);
    }
}