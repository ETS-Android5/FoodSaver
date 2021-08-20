package com.sp.myapplication.cart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sp.myapplication.R;

import java.util.ArrayList;

public class CartAdapter extends
        RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private ArrayList id, foodname, qty, foodprice;
    private OnItemList onItemList;

    CartAdapter(Context context, ArrayList id, ArrayList foodname, ArrayList qty, ArrayList foodprice, OnItemList onItemList){
        this.context = context;
        this.id = id;
        this.foodname = foodname;
        this.qty = qty;
        this.foodprice = foodprice;
        this.onItemList = onItemList;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cartitem, parent, false);

        return new ViewHolder(view, onItemList);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.idT.setText(String.valueOf(id.get(position))); index is causing issues
        holder.foodnameT.setText(String.valueOf(foodname.get(position)));
        holder.qtyT.setText(String.valueOf(qty.get(position)));
        holder.foodpriceT.setText(String.valueOf(foodprice.get(position)));

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return id.size();
    }

    // ... constructor and member variables
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView foodnameT, foodpriceT, qtyT; //id is left out
        OnItemList onItemList;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView, OnItemList onItemList) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            //idT = itemView.findViewById(R.id.index);
            foodnameT = itemView.findViewById(R.id.ordernum);
            foodpriceT = itemView.findViewById(R.id.foodprice);
            qtyT = itemView.findViewById(R.id.cost_phlder);
            this.onItemList = onItemList;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemList.onItemClick(getAdapterPosition());
        }
    }

    public interface OnItemList{
        void onItemClick(int position);
    }
}
