package com.sp.myapplication.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.sp.myapplication.R;
import com.sp.myapplication.cart.CartActivity;


public class SearchOrderActivity extends AppCompatActivity {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference foodref = db.collection("foodstock");


    private itemAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_order);


        ImageView backIcon = findViewById(R.id.back_icon);
        ImageView cartIcon = findViewById(R.id.cart_icon);
        SearchFunc();
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchOrderActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpRecyclerView()
    {
        Query query;
        switch(getIntent().getStringExtra("CATID"))
        {
            case "1":
            {
                query = foodref.whereEqualTo("type", "1").orderBy("foodname", Query.Direction.ASCENDING);
                break;
            }
            case "2":
            {
                query = foodref.whereEqualTo("type", "2").orderBy("foodname", Query.Direction.ASCENDING);
                break;
            }
            case "3":
            {
                query = foodref.whereEqualTo("type", "3").orderBy("foodname", Query.Direction.ASCENDING);
                break;
            }
            case "4":
            {
                query = foodref.whereEqualTo("type", "4").orderBy("foodname", Query.Direction.ASCENDING);
                break;
            }
            default:
            {
                query = foodref.orderBy("foodname", Query.Direction.ASCENDING);
                break;
            }
        }

        FirestoreRecyclerOptions<Item> options = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query, Item.class)
                .build();

        adapter = new itemAdapter(options);
        adapter.startListening();

        RecyclerView foodRecview = findViewById(R.id.foodlist);
        foodRecview.setHasFixedSize(true);
        foodRecview.setLayoutManager(new LinearLayoutManager(this));
        foodRecview.setAdapter(adapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        foodRecview.setLayoutManager(gridLayoutManager);

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

    public void SearchFunc(){
        String searchentry = getIntent().getStringExtra("SEARCHENTRY");
        if(searchentry==null) setUpRecyclerView();
        else {
            Object result = searchentry.toLowerCase(). replaceAll("\\s+","");
            //Toast.makeText(getApplicationContext(), searchentry,Toast.LENGTH_SHORT).show();
            Query query = foodref.whereEqualTo("s", result).orderBy("foodname", Query.Direction.ASCENDING);
            FirestoreRecyclerOptions<Item> options = new FirestoreRecyclerOptions.Builder<Item>()
                    .setQuery(query, Item.class)
                    .build();

            adapter = new itemAdapter(options);
            adapter.startListening();

            RecyclerView foodRecview = findViewById(R.id.foodlist);
            foodRecview.setHasFixedSize(true);
            foodRecview.setLayoutManager(new LinearLayoutManager(this));
            foodRecview.setAdapter(adapter);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
            foodRecview.setLayoutManager(gridLayoutManager);
        }
//        SearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String searchEntry =Search.getText().toString();
//                Intent intent = new Intent(OrderActivity.this, SearchOrderActivity.class);
//                intent.putExtra("SEARCHENTRY",searchEntry);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}