package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableItemsListAdapter extends RecyclerView.Adapter<AvailableItemsListAdapter.ItemsListViewHolder> {
    private String[] productsName;
    Context context;
    public AvailableItemsListAdapter(String[] productsName, Context context) {
        this.productsName = productsName;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_available_items_list, parent, false);
        ItemsListViewHolder itemsListViewHolder = new ItemsListViewHolder(view);
        return itemsListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemsListViewHolder holder, int position) {
        if(productsName[position] != null) {

            holder.mProductNameView.setText(productsName[position]);
        }
        final String currentItems = holder.mAvilNotAvailItemsView.getText().toString();
        final int[] count = {Integer.parseInt(currentItems)};
        holder.mIncreaseAvailItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] = count[0] + 1;
                holder.mAvilNotAvailItemsView.setText(String.valueOf(count[0]));

            }
        });
        holder.mDecreaseAvailItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String val = holder.mAvilNotAvailItemsView.getText().toString();
                if (Integer.valueOf(val)>1){
                    count[0] = count[0] - 1;
                    holder.mAvilNotAvailItemsView.setText(count[0]+"");
                }else {
                    holder.mAvilNotAvailItemsView.setText("1");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class ItemsListViewHolder extends RecyclerView.ViewHolder {
        TextView mProductNameView,mAvilNotAvailItemsView;
        ImageView mIncreaseAvailItems,mDecreaseAvailItems;

        public ItemsListViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductNameView = (TextView) itemView.findViewById(R.id.available_items_list_product_name_view);
            mAvilNotAvailItemsView = itemView.findViewById(R.id.view_avil_not_avil_items);
            mIncreaseAvailItems = itemView.findViewById(R.id.increase_available_items);
            mDecreaseAvailItems = itemView.findViewById(R.id.decrees_available_items);
        }
    }
}
