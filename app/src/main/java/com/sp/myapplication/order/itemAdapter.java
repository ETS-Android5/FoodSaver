package com.sp.myapplication.order;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.sp.myapplication.R;

import org.jetbrains.annotations.NotNull;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class itemAdapter extends FirestoreRecyclerAdapter<Item,itemAdapter.itemHolder> {
    public itemAdapter(@NonNull @NotNull FirestoreRecyclerOptions<Item> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull itemHolder holder, int position, @NonNull @NotNull Item model) {
        Glide.with(holder.foodimg.getContext()).load(model.getfoodimg()).into(holder.foodimg);
        holder.foodprice.setText(model.getfoodprice());
        holder.foodname.setText(model.getfoodname());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(holder.card.getContext(),DetailOrderActivity.class);
                i.putExtra("FIMG",model.getfoodimg());
                i.putExtra("FNAME",model.getfoodname());
                i.putExtra("FPRICE",model.getfoodprice());
                i.putExtra("FEXPIRY",model.getexpiry());
                i.putExtra("DESC",model.getdesc());
                holder.card.getContext().startActivity(i);
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public itemHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new itemHolder(v);
    }

    class itemHolder extends RecyclerView.ViewHolder {
        CardView card;
        ImageView foodimg;
        TextView foodprice;
        TextView foodname;

    public itemHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        foodimg = itemView.findViewById(R.id.foodcatimg);
        foodprice = itemView.findViewById(R.id.foodprice);
        foodname = itemView.findViewById(R.id.ordernum);
        card = itemView.findViewById(R.id.card);
        }


    }

}
