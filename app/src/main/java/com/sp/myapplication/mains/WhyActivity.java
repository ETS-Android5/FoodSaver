package com.sp.myapplication.mains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sp.myapplication.R;
import com.sp.myapplication.home.HomeActivity;
import com.sp.myapplication.menu.Menu_PreviousOrder;

public class WhyActivity extends AppCompatActivity {
MediaPlayer music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_why);
        ImageView backIcon = findViewById(R.id.back_icon);

        music = MediaPlayer.create(WhyActivity.this,R.raw.why_music);
        music.start();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WhyActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
                music.stop();
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        //Pause your player
        music.pause();
        //do more stuff
    }

    @Override
    public void onResume() {
        super.onResume();
        //Pause your player
        music.start();
        //do more stuff
    }
}