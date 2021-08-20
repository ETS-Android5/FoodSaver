package com.sp.myapplication.menu;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.sp.myapplication.home.HomeActivity;
import com.sp.myapplication.R;

public class Menu_Help extends AppCompatActivity {
    private Button Call, Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_help);
        ImageView backIcon = findViewById(R.id.back_icon);
        Call = findViewById(R.id.call);
        Email = findViewById(R.id.email);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_Help.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        Call.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                try {
                    // request permission if not granted
                    if (ActivityCompat.checkSelfPermission(Menu_Help.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Menu_Help.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                        // i suppose that the user has granted the permission
                        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:96751132"));
                        startActivity(in);
                        // if the permission is granted then ok
                    } else {
                        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:96751132"));
                        startActivity(in);
                    }
                }
                // catch the exception if I try to make a call and the permission is not granted
                catch (Exception e) {
                }
            }
        });
        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                String to= "joe@foodsaver.com";
                String subject= "FoodSaver Enquiry";
                String message= "Hello there! I need assistance with ...";

                email.putExtra(Intent.EXTRA_EMAIL, new String[]{ to});
                email.putExtra(Intent.EXTRA_SUBJECT, subject);
                email.putExtra(Intent.EXTRA_TEXT, message);
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, "Choose an Email cClient :"));
            }
        });
    }

}