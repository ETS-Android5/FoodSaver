package com.sp.myapplication.mains;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sp.myapplication.R;
import com.sp.myapplication.home.HomeActivity;

import java.util.Random;

public class PointsActivity extends AppCompatActivity {
    ImageView backIcon, closeIcon, infoButton, FPimg;
    CardView Info;
    Button Transparent, Redeem1, Redeem2, Redeem3;
    TextView Fpoints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points);

        backIcon = findViewById(R.id.back_icon);
        infoButton = findViewById(R.id.info_btn);
        closeIcon = findViewById(R.id.closeBtn);
        Info = findViewById(R.id.fp_info);
        Transparent = findViewById(R.id.transparent);
        Fpoints =findViewById(R.id.fpoint);
        FPimg = findViewById(R.id.foodieimg);
        insertPoints();
        Info.setVisibility(View.GONE);
        Transparent.setVisibility(View.GONE);
        Redeem1 = findViewById(R.id.redeemBtn1);
        Redeem2 = findViewById(R.id.redeemBtn2);
        Redeem3 = findViewById(R.id.redeemBtn3);
        FPIconSetter();

        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PointsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info.setVisibility(View.GONE);
                Transparent.setVisibility(View.GONE);
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Info.setVisibility(View.VISIBLE);
                Transparent.setVisibility(View.VISIBLE);
            }
        });

        Redeem1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPoints(500))
                {
                    showAlertDialog1(v);
                } else {
                    Toast.makeText(getApplicationContext(),"Insufficient points. Get more first!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Redeem2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPoints(1100))
                {
                    showAlertDialog2(v);
                } else {
                    Toast.makeText(getApplicationContext(),"Insufficient points. Get more first!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Redeem3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkPoints(2500))
                {
                    showAlertDialog1(v);
                } else {
                    Toast.makeText(getApplicationContext(),"Insufficient points. Get more first!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void insertPoints()
    {
        SharedPreferences p = getSharedPreferences("DETAILS", MODE_PRIVATE);
        int currentPts = p.getInt("points", 0);
        Fpoints.setText(String.valueOf(currentPts));
    }

    private boolean checkPoints(Integer voucherpoints)
    {
        SharedPreferences p = getSharedPreferences("DETAILS", MODE_PRIVATE);
        int currentPts = p.getInt("points", 0);
        Integer remainPts = currentPts - voucherpoints;
        if(remainPts < 0)
        {
            return false;
        }
        else {
            SharedPreferences.Editor editor = getSharedPreferences("DETAILS", MODE_PRIVATE).edit();
            editor.putInt("points", remainPts);
            editor.apply();
            return true;
        }
    }

    public void showAlertDialog1(View v){
        int leftLimit = 65; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Voucher Code:");
        alert.setMessage(generatedString);
        alert.setPositiveButton("Yay, Ok!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        alert.create().show();
    }

    public void showAlertDialog2(View v){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Food from the Heart Donation:");
        alert.setMessage("A donation has been made to Food from the Heart under your account name. Thank you!");
        alert.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);
            }
        });
        alert.create().show();
    }

    private void FPIconSetter(){
        Integer points = Integer.valueOf((String) Fpoints.getText());
        if(points < 3000) FPimg.setImageResource(R.drawable.fpbronze);
        else if(points >= 3000 && points < 6000) FPimg.setImageResource(R.drawable.fpsilver);
        else FPimg.setImageResource(R.drawable.fpgold);
    }
}