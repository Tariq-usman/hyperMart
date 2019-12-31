package com.system.user.menwain.adapters.home_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ExploreShopItemsListAdapter extends RecyclerView.Adapter<ExploreShopItemsListAdapter.ItemsListViewHolder> {
    Context context;
    private String[] productsName;
    private int[] products;
    public ExploreShopItemsListAdapter(Context context, String[] productsName, int[] products) {
        this.context = context;
        this.productsName = productsName;
        this.products = products;
    }

    @NonNull
    @Override
    public ItemsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_all_items_list,parent,false);
        ItemsListViewHolder viewHolder = new ItemsListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsListViewHolder holder, int position) {
        holder.ivAllItemsList.setImageResource(products[position]);
        holder.tvAllItemsList.setText(productsName[position]);
    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public class ItemsListViewHolder extends RecyclerView.ViewHolder{
        private TextView tvAllItemsList;
        private ImageView ivAllItemsList;
        public ItemsListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAllItemsList = itemView.findViewById(R.id.tv_all_items_list);
            ivAllItemsList = itemView.findViewById(R.id.image_view_all_items_list);
        }
    }
}
