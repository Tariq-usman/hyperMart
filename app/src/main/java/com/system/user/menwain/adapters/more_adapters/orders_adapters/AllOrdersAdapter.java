package com.system.user.menwain.adapters.more_adapters.orders_adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.more.orders.OrderDetailsFragment;
import com.system.user.menwain.responses.more.orders.AllOrdersResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.OrdersItemViewHolder>{
    List<AllOrdersResponse.Allorders.Datum> all_orders_list;
    Context context;
    Preferences prefrences;
    Bundle bundle;

    public AllOrdersAdapter(Context context, List<AllOrdersResponse.Allorders.Datum> all_orders_list) {
        this.all_orders_list = all_orders_list;
        this.context = context;
        prefrences = new Preferences(context);
    }

    @NonNull
    @Override
    public OrdersItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_orders,parent,false);
        OrdersItemViewHolder favouriteItemViewHolder = new OrdersItemViewHolder(view);
        return favouriteItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersItemViewHolder holder, final int position) {
        holder.tvOrderNo.setText(all_orders_list.get(position).getSecretCode().toString());
        holder.tvOrderStatus.setText(all_orders_list.get(position).getOrderStatus());
        String date_time = all_orders_list.get(position).getDateTime();
        String[] split_date_time = date_time.split(" ");
        String date = split_date_time[0];
        holder.tvOrderDate.setText(all_orders_list.get(position).getPreferredDeliveryDate());
        holder.tvTotalPrice.setText(all_orders_list.get(position).getTotalPrice() +" SAR");
        Glide.with(holder.ivStoreImage.getContext()).load(all_orders_list.get(position).getStore().getImage()).into(holder.ivStoreImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreOrdersFragStatus(2);
                prefrences.setMoreOrdersStatus(1);
                OrderDetailsFragment fragment = new OrderDetailsFragment();
                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                prefrences.setMoreOrderId(all_orders_list.get(position).getId());
                prefrences.setMoreOrdersStatusName("pending");
                transaction.replace(R.id.nav_host_fragment,fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return all_orders_list.size();
    }

    public static class OrdersItemViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTotalPrice,tvOrderNo,tvOrderStatus,tvOrderDate;
        private ImageView ivStoreImage;
        public OrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderNo = itemView.findViewById(R.id.tv_order_no_all_orders);
            tvOrderStatus = itemView.findViewById(R.id.tv_ordr_status_all_orders);
            tvOrderDate = itemView.findViewById(R.id.tv_order_date_all_orders);
            tvTotalPrice = itemView.findViewById(R.id.tv_total_amount_all_orders);
            ivStoreImage = itemView.findViewById(R.id.iv_store_orders);

        }
    }
}
