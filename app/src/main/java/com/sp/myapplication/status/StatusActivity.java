package com.sp.myapplication.status;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.sp.myapplication.MapsActivity;
import com.sp.myapplication.R;
import com.sp.myapplication.home.HomeActivity;

import java.util.ArrayList;

public class StatusActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "FoodSaver";
    private static final int NOTIFICATION_ID = 001;
    private static final String TAG = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recview;
    Integer completeStatus = 0;
    TextView text1, text2, textphlder;
    ImageView Img1, Img2;
    Button viewDir;
    private PrevOrderAdapter adapter = null;
    ArrayList<String> foodname;
    ArrayList<Integer> qty;
    String total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        ImageView backIcon = findViewById(R.id.back_icon);
        viewDir = findViewById(R.id.dir_but);
        text1 = findViewById(R.id.textView3);
        text2 = findViewById(R.id.textView4);
        textphlder = findViewById(R.id.textphlder);
        recview = findViewById(R.id.recview);

        text1.setVisibility(View.GONE);
        text2.setVisibility(View.GONE);
        viewDir.setVisibility(View.GONE);
        textphlder.setText("You have no active orders");

        Img1 = findViewById(R.id.status_img1);
        Img2 = findViewById(R.id.status_img2);
        moveAnim1();
        foodname = new ArrayList<>();
        qty = new ArrayList<>();
        storeDatainArray();

        getOrderStatus();
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        viewDir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatusActivity.this, MapsActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private Integer getOrderStatus() {
        SharedPreferences prefs = getSharedPreferences("DETAILS", MODE_PRIVATE);
        int orderNo = prefs.getInt("ordernum", 0);
        String order = String.valueOf(orderNo);
    DocumentReference docRef = db.collection("orders").document(order);
    docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onEvent(@Nullable DocumentSnapshot snapshot,
                            @Nullable FirebaseFirestoreException e) {
            if (e != null) {
                Log.w(TAG, "Listen failed.", e);
                return;
            }

            if (snapshot != null && snapshot.exists()) {
                Log.d(TAG, "Current data: " + snapshot.getData());
                Integer status = Math.toIntExact(snapshot.getLong("completion"));
                if(status == 0){
                    text1.setVisibility(View.VISIBLE);
                    text2.setVisibility(View.VISIBLE);
                    viewDir.setVisibility(View.VISIBLE);

                    textphlder.setText("Your order has been placed successfully!");
                }
                else if(status == 1) {

                    text1.setVisibility(View.GONE);
                    text2.setVisibility(View.GONE);
                    viewDir.setVisibility(View.GONE);
                    textphlder.setText("You have no active orders");
                    createNotificationChannel();
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(StatusActivity.this,CHANNEL_ID)
                            .setSmallIcon(R.drawable.status_icon_back)
                            .setContentTitle("FoodSaver Order")
                            .setContentText("You have no outstanding orders. Enjoy!")
                            .setPriority(NotificationCompat.PRIORITY_HIGH);
                    NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(StatusActivity.this);
                    notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                }

            }
            else {
                Log.d(TAG, "Current data: null");
            }
        }
    });

        return completeStatus;
    }

    private void storeDatainArray() {
        SharedPreferences prefs = getSharedPreferences("DETAILS", MODE_PRIVATE);
        int orderNo = prefs.getInt("ordernum", 0);
        String order = String.valueOf(orderNo);
        DocumentReference docRef = db.collection("orders").document(order);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d(TAG, "Current data: " + snapshot.getData());
                    Integer status = Math.toIntExact(snapshot.getLong("completion"));
                    if(status == 0) {
                        foodname  = (ArrayList<String>) snapshot.get("foodname");
                        qty  = (ArrayList<Integer>) snapshot.get("qty");
                        adapter = new PrevOrderAdapter(StatusActivity.this, foodname, qty);
                        RecyclerViewstuff();
                        recview.setVisibility(View.VISIBLE);
                    } else{
                        recview.setVisibility(View.GONE);
                    };
                }
                else {
                    Log.d(TAG, "Current data: null");
                }
            }
        });
    }

    private void RecyclerViewstuff(){
        recview.setAdapter(adapter);
        recview.setLayoutManager(new LinearLayoutManager(StatusActivity.this));
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void moveAnim1() {
        Animation img = new TranslateAnimation(500,Animation.ABSOLUTE, Animation.ABSOLUTE, 0);
        img.setDuration(1000);
        img.setFillAfter(true);
        Img1.startAnimation(img);
        Img2.startAnimation(img);
    }

}