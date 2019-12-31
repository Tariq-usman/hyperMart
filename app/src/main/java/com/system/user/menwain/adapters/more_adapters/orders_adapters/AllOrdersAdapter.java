package com.system.user.menwain.adapters.more_adapters.orders_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.more.orders.OrderDetailsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrdersAdapter.OrdersItemViewHolder>{
    private String [] amount;
    Context context;

    public AllOrdersAdapter(Context context, String[] amount) {
        this.amount = amount;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_all_orders,parent,false);
        OrdersItemViewHolder favouriteItemViewHolder = new OrdersItemViewHolder(view);
        return favouriteItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersItemViewHolder holder, int position) {
        holder.mAmount.setText(amount[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OrderDetailsFragment fragment = new OrderDetailsFragment();
                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment,fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return amount.length;
    }

    public static class OrdersItemViewHolder extends RecyclerView.ViewHolder{
        TextView mAmount;
        public OrdersItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mAmount = itemView.findViewById(R.id.tv_total_amount);
        }
    }
}
