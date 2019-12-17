package com.system.user.menwain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.activities.OrderDetailsActivity;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.ItemsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder> {
    private String[] orderNumbers;
    Context context;

    public OrderHistoryAdapter(Context applicationContext, String[] orderNumbers) {
        this.context = applicationContext;
        this.orderNumbers = orderNumbers;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_order_hostory, parent, false);
        OrderViewHolder orderViewHolder = new OrderViewHolder(view);
        return orderViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderViewHolder holder, int position) {
        holder.mOrderNo.setText(orderNumbers[position]);
        holder.mOrderNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = holder.mOrderNo.getText().toString().trim();
                Toast.makeText(context, "Order No:"+value, Toast.LENGTH_SHORT).show();
            }
        });
        holder.mViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailsActivity fragment = new OrderDetailsActivity();
                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment,fragment).commit();
                //((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return orderNumbers.length;
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView mOrderNo,mViewOrder;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            mOrderNo = itemView.findViewById(R.id.order_no_view);
            mViewOrder = itemView.findViewById(R.id.view_order);
        }
    }
}
