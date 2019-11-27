package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsListAdapter extends RecyclerView.Adapter<ItemsListAdapter.ItemsListViewHolder> {
    private String[] productsName;
    Context context;
    public ItemsListAdapter(String[] productsName, Context context) {
        this.productsName = productsName;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_list, parent, false);
        ItemsListViewHolder itemsListViewHolder = new ItemsListViewHolder(view);
        return itemsListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsListViewHolder holder, int position) {
        if(productsName[position] != null) {

            holder.mProductNameView.setText(productsName[position]);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                Intent intent =new  Intent(context, ListDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class ItemsListViewHolder extends RecyclerView.ViewHolder {
        TextView mProductNameView;

        public ItemsListViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductNameView = (TextView) itemView.findViewById(R.id.available_items_list_product_name_view);
        }
    }
}
