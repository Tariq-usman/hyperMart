package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersDeliveredAdapter extends RecyclerView.Adapter<OrdersDeliveredAdapter.ItemViewHolder>{
    private String [] amount;
    private String [] order_status;
    Context context;

    public OrdersDeliveredAdapter(Context context, String[] amount, String[] order_status) {
        this.context = context;
        this.amount = amount;
        this.order_status = order_status;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders_delivered,parent,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.mAmount.setText(amount[position]);
        holder.mOrderStatus.setText(order_status[position]);
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

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView mAmount,mOrderStatus;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mOrderStatus = itemView.findViewById(R.id.tv_delivered_order_status);
            mAmount = itemView.findViewById(R.id.tv_total_amount_order_processing);
        }
    }
}
