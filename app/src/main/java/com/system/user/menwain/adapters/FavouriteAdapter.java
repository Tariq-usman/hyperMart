package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.FavouriteItemViewHolder>{
    private int [] products;
    Context context;
    public FavouriteAdapter(Context context, int[] products) {
        this.products = products;
        this.context = context;
    }

    @NonNull
    @Override
    public FavouriteItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_favourite,parent,false);
        FavouriteItemViewHolder favouriteItemViewHolder = new FavouriteItemViewHolder(view);
        return favouriteItemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteItemViewHolder holder, int position) {
        holder.mProduct.setImageResource(products[position]);
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public static class FavouriteItemViewHolder extends RecyclerView.ViewHolder{
        ImageView mProduct;
        public FavouriteItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mProduct = itemView.findViewById(R.id.product_view);
        }
    }
}
