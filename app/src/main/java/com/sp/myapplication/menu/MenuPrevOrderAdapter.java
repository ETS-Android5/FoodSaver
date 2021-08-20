package com.sp.myapplication.menu;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.sp.myapplication.R;
import com.sp.myapplication.order.DetailOrderActivity;
import com.sp.myapplication.order.Item;

import org.jetbrains.annotations.NotNull;

// FirebaseRecyclerAdapter is a class provided by
// FirebaseUI. it provides functions to bind, adapt and show
// database contents in a Recycler View
public class MenuPrevOrderAdapter extends FirestoreRecyclerAdapter<Order, MenuPrevOrderAdapter.orderHolder> {
    public MenuPrevOrderAdapter(@NonNull @NotNull FirestoreRecyclerOptions<Order> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull @NotNull MenuPrevOrderAdapter.orderHolder holder, int position, @NonNull @NotNull Order model) {
        holder.OrderNum.setText(String.valueOf(model.getOrderid()));
        holder.Date.setText(model.getDate());
        holder.Cost.setText("$" + model.getTotal());
        if(model.getCompletion() == 0)
            holder.Status.setText("ORDERED");
        else
            holder.Status.setText("COLLECTED");
    }

    @NonNull
    @NotNull
    @Override
    public orderHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.prevorder_order, parent, false);
        return new MenuPrevOrderAdapter.orderHolder(v);
    }

    class orderHolder extends RecyclerView.ViewHolder {
        CardView card;
        TextView OrderNum, Date, Cost, Status;

        public orderHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            OrderNum = itemView.findViewById(R.id.ordernum);
            Date = itemView.findViewById(R.id.date_phlder);
            Cost = itemView.findViewById(R.id.cost_phlder);
            Status = itemView.findViewById(R.id.status_phlder);
            card = itemView.findViewById(R.id.ordercard);
        }


    }

}