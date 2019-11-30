package com.system.user.menwain.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SelectedItemsNamesAdapter extends RecyclerView.Adapter<SelectedItemsNamesAdapter.SelectedItemsViewHolder> {
Context context;
String [] productsName;
public int lastSelectedItem = -1;
    public SelectedItemsNamesAdapter(Context context, String[] productsName) {
        this.context = context;
        this.productsName =productsName;
    }

    @NonNull
    @Override
    public SelectedItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_selected_items_names,parent,false);
        SelectedItemsViewHolder viewHolder = new SelectedItemsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedItemsViewHolder holder, int position) {
        holder.mItemsName.setText(productsName[position]);
        if (lastSelectedItem == position){
            holder.mItemsName.setBackgroundResource(R.drawable.items_bg_selected);
            holder.mItemsName.setTextColor(Color.parseColor("#FFFFFF"));
        }else {
            holder.mItemsName.setBackgroundResource(R.drawable.items_bg_unselected);
            holder.mItemsName.setTextColor(Color.parseColor("#004040"));
        }
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public class SelectedItemsViewHolder extends RecyclerView.ViewHolder{
        TextView mItemsName;
        public SelectedItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemsName = itemView.findViewById(R.id.filter_item_name_view);
            mItemsName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastSelectedItem= getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
