package com.system.user.menwain.adapters.more_adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedStoresNamesAdapter extends RecyclerView.Adapter<SelectedStoresNamesAdapter.SelectedStoresViewHolder> {
Context context;
String [] productsName;
public int lastSelectedItem = -1;
    public SelectedStoresNamesAdapter(Context context, String[] productsName) {
        this.context = context;
        this.productsName =productsName;
    }

    @NonNull
    @Override
    public SelectedStoresViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_selected_stores_names,parent,false);
        SelectedStoresViewHolder viewHolder = new SelectedStoresViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedStoresViewHolder holder, int position) {
        holder.mStoreName.setText(productsName[position]);
        if (lastSelectedItem == position){
            holder.mStoreName.setBackgroundResource(R.drawable.items_bg_selected);
            holder.mStoreName.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.mStoreName.setBackgroundResource(R.drawable.items_bg_unselected);
            holder.mStoreName.setTextColor(Color.parseColor("#004040"));
        }
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public class SelectedStoresViewHolder extends RecyclerView.ViewHolder{
        private TextView mStoreName;
        public SelectedStoresViewHolder(@NonNull View itemView) {
            super(itemView);
            mStoreName = itemView.findViewById(R.id.filter_store_name_view);
            mStoreName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedItem= getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
