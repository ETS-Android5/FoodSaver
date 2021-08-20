package com.sp.myapplication.menu;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.firebase.firestore.Query;

import com.sp.myapplication.home.HomeActivity;
import com.sp.myapplication.R;


public class Menu_PreviousOrder extends AppCompatActivity {
    private static final String TAG = "";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private RecyclerView recview;
    private MenuPrevOrderAdapter adapter = null;
    private CollectionReference foodref = db.collection("orders");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_previous_order);

        ImageView backIcon = findViewById(R.id.back_icon);

        Query query = foodref.orderBy("orderid", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Order> options = new FirestoreRecyclerOptions.Builder<Order>()
                .setQuery(query, Order.class)
                .build();

        adapter = new MenuPrevOrderAdapter(options);
        adapter.startListening();

        recview = findViewById(R.id.prevordrec);
        recview.setHasFixedSize(true);
        recview.setLayoutManager(new LinearLayoutManager(this));
        recview.setAdapter(adapter);


        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu_PreviousOrder.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}