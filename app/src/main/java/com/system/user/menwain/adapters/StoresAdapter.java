package com.system.user.menwain.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.StoresViewHolder> {
    private String [] productsName;

    public StoresAdapter(String[] productsName) {
        this.productsName = productsName;
    }

    @NonNull
    @Override
    public StoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_stores,parent,false);
        StoresViewHolder categoryViewHolder = new StoresViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StoresViewHolder holder, int position) {
        holder.mProductNameView.setText(productsName[position]);
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class StoresViewHolder extends RecyclerView.ViewHolder{
        TextView mProductNameView;
        public StoresViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductNameView=itemView.findViewById(R.id.store_name_view);
        }
    }
}
