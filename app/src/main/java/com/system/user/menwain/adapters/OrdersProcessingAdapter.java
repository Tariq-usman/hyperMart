package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrdersProcessingAdapter extends RecyclerView.Adapter<OrdersProcessingAdapter.OrdersItemViewHolder>{
    private String [] amount;
    private String [] order_status;
    Context context;

    public OrdersProcessingAdapter(Context context, String[] amount, String[] order_status) {
        this.context = context;
        this.amount = amount;
        this.order_status = order_status;
    }

    @NonNull
    @Override
    public OrdersItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders_processing,parent,false);
        OrdersItemViewHolder favouriteItemViewHolder = new OrdersItemViewHolder(view);
        return favouriteItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersItemViewHolder holder, int position) {
        holder.mAmount.setText(amount[position]);
        holder.mOrderStatus.setText(order_status[position]);
      /*  holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OrderDetailsFragment.class);
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
        private TextView mAmount,mOrderStatus;
        public OrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mOrderStatus = itemView.findViewById(R.id.tv_order_status);
            mAmount = itemView.findViewById(R.id.tv_total_amount_order_processing);
        }
    }
}
