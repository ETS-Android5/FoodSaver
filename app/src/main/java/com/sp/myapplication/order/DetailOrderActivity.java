package com.sp.myapplication.order;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.sp.myapplication.R;
import com.sp.myapplication.cart.CartActivity;
import com.sp.myapplication.cart.CartHelper;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailOrderActivity extends AppCompatActivity {

    private CartHelper helper;
    private Cursor model = null;
    private Button AddtoCart;
    private ImageView AddBtn, SubBtn, Cart, Back;
    private EditText Qty;
    private Integer numberOrder = 1;

    public DetailOrderActivity() {}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_order);
        ImageView Foodimg = findViewById(R.id.foodimg);
        TextView Foodname = findViewById(R.id.ordernum);
        TextView Foodprice = findViewById(R.id.foodprice);
        TextView Expiry = findViewById(R.id.foodexpiry);
        TextView Desc = findViewById(R.id.desc);
        Qty = findViewById(R.id.qty);
        AddBtn = findViewById(R.id.add_but);
        SubBtn = findViewById(R.id.subtract_but);
        AddtoCart = findViewById(R.id.addtocartbut);
        Cart = findViewById(R.id.cart_icon);
        Back = findViewById(R.id.back_icon);


        helper = new CartHelper(this);
        model = helper.getAll();
        Qty.setCursorVisible(false); //no cursor till clicked

        //displays info
        Glide.with(Foodimg.getContext()).load(getIntent().getStringExtra("FIMG")).into(Foodimg);
        Foodname.setText(getIntent().getStringExtra("FNAME"));
        Foodprice.setText(getIntent().getStringExtra("FPRICE"));
        Expiry.setText(getIntent().getStringExtra("FEXPIRY"));
        Desc.setText(getIntent().getStringExtra("DESC"));

        cartButtons();

    }
    private void cartButtons()
    {
        Cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DetailOrderActivity.this, CartActivity.class);
                startActivity(i);
            }
        });
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        AddtoCart.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 String foodname = getIntent().getStringExtra("FNAME");
                 String foodprice = getIntent().getStringExtra("FPRICE");
                 numberOrder = Integer.valueOf(Qty.getText().toString());
                 model.moveToFirst();
                 //took so long to figure this out >:(
                 try{
                     if (numberOrder > 20)
                         Toast.makeText(getApplicationContext(), "Please select a lower quantity", Toast.LENGTH_SHORT).show();
                     else if (numberOrder == 0) {
                         helper.delete(foodname, foodprice, numberOrder);
                         Toast.makeText(getApplicationContext(), "Removed from Cart", Toast.LENGTH_SHORT).show();
                         finish();
                         overridePendingTransition(0, 0);
                         startActivity(new Intent(DetailOrderActivity.this, CartActivity.class));
                         overridePendingTransition(0, 0);

                     }
                     else {
                         helper.insert(foodname, foodprice, numberOrder);
                         Toast.makeText(getApplicationContext(), "Added To Cart", Toast.LENGTH_SHORT).show();
                     }
                 }
                 catch(SQLiteConstraintException ex){
                     helper.update(foodname, foodprice, numberOrder);
                     Toast.makeText(getApplicationContext(), "Cart Updated", Toast.LENGTH_SHORT).show();
                     finish();
                     overridePendingTransition(0, 0);
                     startActivity(new Intent(DetailOrderActivity.this, CartActivity.class));
                     overridePendingTransition(0, 0);
                 }
             }
         });

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder <= 20) numberOrder = numberOrder + 1;
                Qty.setText(String.valueOf(numberOrder));
            }
        });

        SubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrder > 0) {
                    numberOrder = numberOrder - 1;
                }
                Qty.setText(String.valueOf(numberOrder));
            }
        });

        Qty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Qty.setCursorVisible(true);
            }
        });
    }

}