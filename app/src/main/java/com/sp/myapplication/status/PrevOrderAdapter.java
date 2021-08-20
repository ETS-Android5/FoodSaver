package com.sp.myapplication.status;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sp.myapplication.R;

import java.util.ArrayList;

public class PrevOrderAdapter extends
        RecyclerView.Adapter<PrevOrderAdapter.ViewHolder> {

    private Context context;
    private ArrayList foodname, qty;

    PrevOrderAdapter(Context context, ArrayList foodname, ArrayList qty){
        this.context = context;
        this.foodname = foodname;
        this.qty = qty;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.prevorder_item, parent, false);

        return new ViewHolder(view);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        holder.idT.setText(String.valueOf(id.get(position))); index is causing issues
        holder.foodnameT.setText(String.valueOf(foodname.get(position)));
        holder.qtyT.setText(String.valueOf(qty.get(position)));
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return foodname.size();
    }

    // ... constructor and member variables
    public class ViewHolder extends RecyclerView.ViewHolder{
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView foodnameT, qtyT; //id is left out

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            //idT = itemView.findViewById(R.id.index);
            foodnameT = itemView.findViewById(R.id.ordernum);
            qtyT = itemView.findViewById(R.id.cost_phlder);
        }

    }

}
