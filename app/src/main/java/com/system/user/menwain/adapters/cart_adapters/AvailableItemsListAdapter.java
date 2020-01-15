package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.AvailNotAvailItemsListsFragment;
import com.system.user.menwain.fragments.category.ItemsFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableItemsListAdapter extends RecyclerView.Adapter<AvailableItemsListAdapter.ItemsListViewHolder> {
    private String[] productsName;
    Context context;
    Prefrences prefrences;
    public AvailableItemsListAdapter(String[] productsName, Context context) {
        this.productsName = productsName;
        this.context = context;
        prefrences = new Prefrences(context);
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
        holder.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefrences.setBottomNavStatus(6);
                ItemsFragment fragment = new ItemsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class ItemsListViewHolder extends RecyclerView.ViewHolder {
        TextView mProductNameView,mAvilNotAvailItemsView;
        private ImageView mIncreaseAvailItems,mDecreaseAvailItems,ivProduct;

        public ItemsListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.iv_product);
            mProductNameView = (TextView) itemView.findViewById(R.id.available_items_list_product_name_view);
            mAvilNotAvailItemsView = itemView.findViewById(R.id.view_avil_not_avil_items);
            mIncreaseAvailItems = itemView.findViewById(R.id.increase_available_items);
            mDecreaseAvailItems = itemView.findViewById(R.id.decrees_available_items);
        }
    }
}
