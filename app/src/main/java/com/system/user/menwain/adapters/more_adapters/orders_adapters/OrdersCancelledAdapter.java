package com.system.user.menwain.adapters.more_adapters.orders_adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.more.orders.OrderDetailsFragment;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.responses.more.orders.CancelledOrdersResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OrdersCancelledAdapter extends RecyclerView.Adapter<OrdersCancelledAdapter.ItemViewHolder>{
    private List<CancelledOrdersResponse.Allorders.Datum> canceled_orders_list;
    Context context;
    private Preferences preferences;
    private Bundle bundle;
    public OrdersCancelledAdapter(Context context, List<CancelledOrdersResponse.Allorders.Datum> canceled_orders_list) {
        this.context = context;
        this.canceled_orders_list = canceled_orders_list;
        preferences = new Preferences(context);
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_orders_delivered,parent,false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, final int position) {
        holder.tvOrderNo.setText(canceled_orders_list.get(position).getId().toString());
        holder.tvOrderStatus.setText(canceled_orders_list.get(position).getOrderStatus());
        String date_time = canceled_orders_list.get(position).getDateTime();
        String[] split_date_time = date_time.split(" ");
        String date = split_date_time[0];
        holder.tvOrderDate.setText(date);
        holder.tvTotalPrice.setText(canceled_orders_list.get(position).getTotalPrice() +" SAR");
        Glide.with(holder.ivStoreImage.getContext()).load(canceled_orders_list.get(position).getStore().getImage()).into(holder.ivStoreImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                preferences.setMoreOrdersFragStatus(2);
                preferences.setMoreOrdersStatus(4);
                OrderDetailsFragment fragment = new OrderDetailsFragment();
                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                preferences.setMoreOrderId(canceled_orders_list.get(position).getId());
                preferences.setMoreOrdersStatusName("cancelled");
                transaction.replace(R.id.nav_host_fragment,fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return canceled_orders_list.size();
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
