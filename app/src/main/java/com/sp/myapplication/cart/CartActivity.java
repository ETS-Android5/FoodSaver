package com.sp.myapplication.cart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;

import com.google.firebase.firestore.FirebaseFirestore;
import com.sp.myapplication.R;
import com.sp.myapplication.status.StatusActivity;
import com.sp.myapplication.order.DetailOrderActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CartActivity extends AppCompatActivity implements CartAdapter.OnItemList {
    private static final String CHANNEL_ID = "FoodSaver2";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Cursor model = null;
    private RecyclerView recview;
    private CartAdapter adapter = null;
    private CartHelper helper = null;
    private TextView pointsT, totalcostT;
    private Button cfmBtn;
    ArrayList<String> foodname, foodprice;
    ArrayList<Integer> qty,id;
    private Double TotalCost = 0.0;
    private String points, tcost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        totalcostT = findViewById(R.id.totphlder);
        pointsT = findViewById(R.id.ptsphlder);
        cfmBtn = findViewById(R.id.cfmorder_but);
        helper = new CartHelper(this);
        id = new ArrayList<>();
        foodname = new ArrayList<>();
        qty = new ArrayList<>();
        foodprice = new ArrayList<>();
        recview = findViewById(R.id.cartRecview);

        model = helper.getAll();
        storeDataInArrays();
        calculate();
        adapter = new CartAdapter(CartActivity.this,id,foodname,qty,foodprice, this);
        RecyclerViewstuff();

        ImageView closeIcon = findViewById(R.id.close_icon);
        closeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cfmBtn.setOnClickListener(new View.OnClickListener(){
            private static final int NOTIFICATION_ID = 002;

            @Override
        public void onClick(View v) {
            if(model.getCount() ==  0) {
                Toast.makeText(getApplicationContext(), "Cart is empty", Toast.LENGTH_SHORT).show();
            }else{
                SharedPreferences prefs = getSharedPreferences("DETAILS", MODE_PRIVATE);
                int orderNo = prefs.getInt("ordernum", 0);
                int currentPts = prefs.getInt("points", 0);
                currentPts += Integer.valueOf(points);
                orderNo++;
                String order = String.valueOf(orderNo);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                String currentDateandTime = sdf.format(new Date());
                Map<String, Object> details = new HashMap<>();
                details.put("id",id);
                details.put("foodname",foodname);
                details.put("qty",qty);
                details.put("date", currentDateandTime);
                details.put("total", tcost);
                details.put("points", points);
                details.put("completion", 0);
                details.put("orderid", orderNo);
                db.collection("orders").document(order).set(details);


                SharedPreferences.Editor editor = getSharedPreferences("DETAILS", MODE_PRIVATE).edit();
                editor.putInt("ordernum", orderNo);
                editor.putInt("points", currentPts);
                editor.apply();

                helper.deleteAll();

                createNotificationChannel();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(CartActivity.this,CHANNEL_ID)
                        .setSmallIcon(R.drawable.status_icon_back)
                        .setContentTitle("FoodSaver Order")
                        .setContentText("You may collect your order by today!")
                        .setPriority(NotificationCompat.PRIORITY_HIGH);
                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(CartActivity.this);
                notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

                Intent i = new Intent(CartActivity.this, StatusActivity.class);
                startActivity(i);
            }

        }
        });
    }

    void storeDataInArrays(){
        if(model.getCount() ==  0) {
            Toast.makeText(this, "Cart is empty", Toast.LENGTH_SHORT).show();
        }else{
            while (model.moveToNext()){
                id.add(helper.getid(model));
                foodname.add(helper.getfoodname(model));
                qty.add(helper.getqty(model));
                foodprice.add(helper.getfoodprice(model));
                Double Cost = Double.valueOf(helper.getfoodprice(model));
                Double Qty = Double.valueOf(helper.getqty(model));
                TotalCost += Cost * Qty;
            }
        }
    }

    void calculate(){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setMinimumIntegerDigits(1);
        df.setMinimumFractionDigits(2);
        df.setMaximumFractionDigits(2);
        tcost = df.format(TotalCost);
        String total = "$" + tcost;
        totalcostT.setText(total);
        Double Points = TotalCost * 10.0;
        points = String.format("%.0f", Points);
        pointsT.setText(points);
    }

    void RecyclerViewstuff(){
        recview.setAdapter(adapter);
        recview.setLayoutManager(new LinearLayoutManager(CartActivity.this));
        }

    @Override
    public void onItemClick(int position) {
        db.collection("foodstock").document(foodname.get(position)).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        String FSimg = documentSnapshot.getString("foodimg");
                        String FSname = documentSnapshot.getString("foodname");
                        String FSprice = documentSnapshot.getString("foodprice");
                        String FSexpiry = documentSnapshot.getString("expiry");
                        String FSdesc = documentSnapshot.getString("desc");
                        Intent i = new Intent(CartActivity.this, DetailOrderActivity.class);
                        i.putExtra("FIMG",FSimg);
                        i.putExtra("FNAME",FSname);
                        i.putExtra("FPRICE",FSprice);
                        i.putExtra("FEXPIRY",FSexpiry);
                        i.putExtra("DESC",FSdesc);
                        startActivity(i);
                        finish();

                    } else {
                        Toast.makeText(CartActivity.this, "Item does not exist", Toast.LENGTH_SHORT);
                    }
            }

        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e){
                        Toast.makeText(CartActivity.this, "Error", Toast.LENGTH_SHORT);
                    }
                  });
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

}


