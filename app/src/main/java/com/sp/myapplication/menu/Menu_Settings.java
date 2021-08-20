package com.sp.myapplication.menu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sp.myapplication.home.HomeActivity;
import com.sp.myapplication.R;

public class Menu_Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_settings);
        ImageView backIcon = findViewById(R.id.back_icon);

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Settings.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}