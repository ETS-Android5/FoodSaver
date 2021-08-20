package com.sp.myapplication.order;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.View;


import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sp.myapplication.R;
import com.sp.myapplication.cart.CartActivity;
import com.sp.myapplication.home.HomeActivity;

import java.util.ArrayList;


public class OrderActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList;
    private EditText Search;
    private ImageView SearchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Search = findViewById(R.id.search);
        SearchBtn = findViewById(R.id.searchBtn);
        recyclerViewCategory();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); //set hidden keyboard until clicked (EditText)
        SearchFunc();
        ImageView backIcon = findViewById(R.id.back_icon);
        ImageView cartIcon = findViewById(R.id.cart_icon);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        cartIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.categories);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> categoryList = new ArrayList<>();
        categoryList.add(new CategoryDomain("FRUITS & VEGETABLES"));
        categoryList.add(new CategoryDomain("FROZEN FOOD"));
        categoryList.add(new CategoryDomain("INSTANT FOOD"));
        categoryList.add(new CategoryDomain("CONDIMENTS"));

        adapter = new CategoryAdapter(categoryList);
        recyclerViewCategoryList.setAdapter(adapter);
        recyclerViewCategoryList.addOnItemTouchListener(new recycleclick(getApplicationContext(), recyclerViewCategoryList, new recycleclick.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                CategoryDomain cat = categoryList.get(position);
                Intent intent = new Intent(getBaseContext(), SearchOrderActivity.class);
                switch (cat.getTitle()) {
                    case "FRUITS & VEGETABLES":
                    {
                        intent.putExtra("CATID", "1");
                        startActivity(intent);
                        break;
                    }
                    case "FROZEN FOOD":
                    {
                        intent.putExtra("CATID", "2");
                        startActivity(intent);
                        break;
                    }
                    case "INSTANT FOOD":
                    {
                        intent.putExtra("CATID", "3");
                        startActivity(intent);
                        break;
                    }
                    case "CONDIMENTS":
                    {
                        intent.putExtra("CATID", "4");
                        startActivity(intent);
                        break;
                    }

                }
            }

            @Override
            public void onLongClick(View view, int position) {}
        }));
    }

    private void SearchFunc(){
        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchEntry =Search.getText().toString();
                Intent intent = new Intent(OrderActivity.this, SearchOrderActivity.class);
                intent.putExtra("SEARCHENTRY",searchEntry);
                startActivity(intent);
            }
        });
    }
}

