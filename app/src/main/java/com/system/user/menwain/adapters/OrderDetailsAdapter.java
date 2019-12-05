package com.system.user.menwain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.system.user.menwain.activities.ItemReviewActivity;
import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.OrderDetailsItemViewHolder>{
    private int [] products;
    Context context;
    public OrderDetailsAdapter(Context context, int[] products) {
        this.products = products;
        this.context = context;
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
                Intent intent = new Intent(context, ItemReviewActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
