package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExploreShopItemsGridAdapter extends RecyclerView.Adapter<ExploreShopItemsGridAdapter.AllItemsGridViewHolder> {
    Context context;
    private String[] productsName;
    private int[] products;
    public ExploreShopItemsGridAdapter(Context context, String[] productsName, int[] products) {
        this.context = context;
        this.productsName = productsName;
        this.products = products;
    }

    @NonNull
    @Override
    public AllItemsGridViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_all_items_grid,parent,false);
        AllItemsGridViewHolder viewHolder = new AllItemsGridViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AllItemsGridViewHolder holder, int position) {
        holder.ivAllItemsGrid.setImageResource(products[position]);
        holder.tvAllItemsGrid.setText(productsName[position]);
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public class AllItemsGridViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAllItemsGrid;
        private ImageView ivAllItemsGrid;
        public AllItemsGridViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAllItemsGrid = itemView.findViewById(R.id.tv_all_items_grid);
            ivAllItemsGrid = itemView.findViewById(R.id.image_view_all_items_grid);
        }
    }
}
