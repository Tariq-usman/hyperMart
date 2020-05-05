package com.system.user.menwain.adapters.more_adapters.orders_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.responses.more.orders.DeleveredOrdersResponse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrdersDeliveredAdapter extends RecyclerView.Adapter<OrdersDeliveredAdapter.ItemViewHolder>{
    private List<DeleveredOrdersResponse.Allorders.Datum> delivered_orders_list;
    Context context;

    public OrdersDeliveredAdapter(Context context, List<DeleveredOrdersResponse.Allorders.Datum> delivered_orders_list) {
        this.context = context;
        this.delivered_orders_list = delivered_orders_list;
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
        holder.tvOrderNo.setText(delivered_orders_list.get(position).getId().toString());
        holder.tvOrderStatus.setText(delivered_orders_list.get(position).getOrderStatus());
        String date_time = delivered_orders_list.get(position).getDateTime();
        String[] split_date_time = date_time.split(" ");
        String date = split_date_time[0];
        holder.tvOrderDate.setText(date);
        holder.tvTotalPrice.setText(delivered_orders_list.get(position).getTotalPrice() +" SAR");
        Glide.with(holder.ivStoreImage.getContext()).load(delivered_orders_list.get(position).getStore().getImage()).into(holder.ivStoreImage);
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
        return delivered_orders_list.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTotalPrice,tvOrderNo,tvOrderStatus,tvOrderDate;
        private ImageView ivStoreImage;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNo = itemView.findViewById(R.id.tv_order_no);
            tvOrderStatus = itemView.findViewById(R.id.tv_delivered_order_status);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_amount_order);
            ivStoreImage = itemView.findViewById(R.id.iv_store_image_deleiver);
        }
    }
}
