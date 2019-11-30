package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListDetailsAdapter extends RecyclerView.Adapter<ListDetailsAdapter.ListDetailsViewHolder> {
    private int [] products;
    Context context;
    public ListDetailsAdapter(Context applicationContext, int[] products) {
        this.products = products;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public ListDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_list_details, parent, false);
        ListDetailsViewHolder listDetailsViewHolder = new ListDetailsViewHolder(view);
        return listDetailsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListDetailsViewHolder holder, int position) {
            holder.mProductImage.setImageResource(products[position]);
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public static class ListDetailsViewHolder extends RecyclerView.ViewHolder {
        ImageView mProductImage;

        public ListDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductImage = itemView.findViewById(R.id.product_image_view);
        }
    }
}
