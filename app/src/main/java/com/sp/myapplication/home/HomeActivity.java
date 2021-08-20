package com.sp.myapplication.home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

//Classes imports
import com.sp.myapplication.GPSTracker;
import com.sp.myapplication.R;
import com.sp.myapplication.SettingsActivity;
import com.sp.myapplication.status.StatusActivity;
import com.sp.myapplication.login.LoginActivity;
import com.sp.myapplication.mains.PointsActivity;
import com.sp.myapplication.mains.RecipesActivity;
import com.sp.myapplication.mains.WhyActivity;
import com.sp.myapplication.menu.Menu_About;
import com.sp.myapplication.menu.Menu_Help;
import com.sp.myapplication.menu.Menu_PreviousOrder;
import com.sp.myapplication.menu.Menu_Profile;
import com.sp.myapplication.order.OrderActivity;

import org.jetbrains.annotations.NotNull;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CardView Order, Status, Recipes, Why, Points;
    ImageView ProfilePic,FPImg, FShi;
    TextView Username, PointsVal, UserBanner, UserBanner2, UserBanner3;
    private GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // --- hooks ---
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        //--- toolbar ---
        setSupportActionBar(toolbar);
        //---navigation drawer menu ---
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
        gpsTracker = new GPSTracker(HomeActivity.this);

        //--- main buttons ---
        Order = findViewById(R.id.order);
        Status = findViewById(R.id.status);
        Recipes = findViewById(R.id.recipes);
        Why = findViewById(R.id.why);
        Points = findViewById(R.id.foodiepoints);

        View headerView = navigationView.getHeaderView(0);
        ProfilePic = headerView.findViewById(R.id.profilepic);
        Username = headerView.findViewById(R.id.username);
        UserBanner = findViewById(R.id.homeuser_phlder);
        UserBanner2 = findViewById(R.id.textView15);
        UserBanner3 = findViewById(R.id.textView16);
        moveAnim();


        PointsVal = findViewById(R.id.fpoint);
        FPImg = findViewById(R.id.foodieimg);
        FShi = findViewById(R.id.FShi);
        Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale);
        FShi.startAnimation(scaleAnimation);
        setValues();
        mainButtons();
        FPIconSetter();
    }

    private Integer setValues(){
        SharedPreferences p1 = getSharedPreferences("DETAILS", MODE_PRIVATE);
        int currentPts = p1.getInt("points", 0);
        PointsVal.setText(String.valueOf(currentPts));

        SharedPreferences p2 = getSharedPreferences("USER", MODE_PRIVATE);
        String username = p2.getString("username", "New User");
        String profilepic = p2.getString("profilepic",null);
        Integer rotation = p2.getInt("picrot", 0);
        if (username == null || username == ""){
            Username.setText("New User");
            UserBanner.setText("Hello there!");
        }
        else{
            Username.setText(username);
            UserBanner.setText("Hello there, " + username + "!");
        }
        if (profilepic == null){}
        else{
            ProfilePic.setImageBitmap(decodeBase64(profilepic));
            ProfilePic.setRotation(ProfilePic.getRotation() + 90 * rotation);
        }
        return currentPts;
    }
    public static Bitmap decodeBase64(String input) {
        byte[] decodedByte = Base64.decode(input, 0);
        return BitmapFactory
                .decodeByteArray(decodedByte, 0, decodedByte.length);
    }
    private void FPIconSetter(){
        Integer points = setValues();
        if(points < 3000) FPImg.setImageResource(R.drawable.fpbronze);
        else if(points >= 3000 && points < 6000) FPImg.setImageResource(R.drawable.fpsilver);
        else FPImg.setImageResource(R.drawable.fpgold);
    }
    @Override
    public void onBackPressed() {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_home:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.profile:
                Intent intent1 = new Intent(HomeActivity.this, Menu_Profile.class);
                startActivity(intent1);
                finish();
                break;
            case R.id.prevord:
                Intent intent2 = new Intent(HomeActivity.this, Menu_PreviousOrder.class);
                startActivity(intent2);
                finish();
                break;
            case R.id.help:
                Intent intent3 = new Intent(HomeActivity.this, Menu_Help.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.settings:
                Intent intent4 = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent4);
                finish();
                break;
            case R.id.about:
                Intent intent5 = new Intent(HomeActivity.this, Menu_About.class);
                startActivity(intent5);
                finish();
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent6 = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent6);
                finish();
                break;
        }
        return true;
    }

    public void mainButtons()
    {
        Order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
        Status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, StatusActivity.class);
                startActivity(intent);
            }
        });
        Recipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, RecipesActivity.class);
                startActivity(intent);
            }
        });
        Why.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, WhyActivity.class);
                startActivity(intent);
            }
        });
        Points.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, PointsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void moveAnim() {
        Animation img = new TranslateAnimation(-200,Animation.ABSOLUTE, Animation.ABSOLUTE, 0);
        img.setDuration(1000);
        img.setFillAfter(true);
        UserBanner.startAnimation(img);
        UserBanner2.startAnimation(img);
        UserBanner3.startAnimation(img);
    }
}