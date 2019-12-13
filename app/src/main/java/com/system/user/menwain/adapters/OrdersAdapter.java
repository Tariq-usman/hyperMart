package com.system.user.menwain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.activities.OrderDetailsActivity;
import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersItemViewHolder>{
    private String [] amount;
    Context context;

    public OrdersAdapter(Context context, String[] amount) {
        this.amount = amount;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders,parent,false);
        OrdersItemViewHolder favouriteItemViewHolder = new OrdersItemViewHolder(view);
        return favouriteItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersItemViewHolder holder, int position) {
        //holder.mAmount.setText(amount[position]);
      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return amount.length;
    }

    public static class OrdersItemViewHolder extends RecyclerView.ViewHolder{
        TextView mAmount;
        public OrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
          //  mAmount = itemView.findViewById(R.id.amount_view);
        }
    }
}
