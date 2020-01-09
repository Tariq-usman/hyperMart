package com.system.user.menwain.adapters.more_adapters.orders_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.system.user.menwain.Prefrences;
import com.system.user.menwain.fragments.more.orders.ItemReviewFragment;
import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsItemViewHolder>{
    private int [] products;
    Context context;
    Prefrences prefrences;
    public OrderDetailsAdapter(Context context, int[] products) {
        this.products = products;
        this.context = context;
        prefrences = new Prefrences(context);
    }

    @NonNull
    @Override
    public OrderDetailsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_order_details,parent,false);
        OrderDetailsItemViewHolder favouriteItemViewHolder = new OrderDetailsItemViewHolder(view);
        return favouriteItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailsItemViewHolder holder, int position) {
        holder.mProduct.setImageResource(products[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setMoreOrdersFragStatus(3);
                ItemReviewFragment fragment = new ItemReviewFragment();
                FragmentTransaction transaction = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment,fragment).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public static class OrderDetailsItemViewHolder extends RecyclerView.ViewHolder{
        ImageView mProduct;
        public OrderDetailsItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mProduct = itemView.findViewById(R.id.order_details_product_view);
        }
    }
}
