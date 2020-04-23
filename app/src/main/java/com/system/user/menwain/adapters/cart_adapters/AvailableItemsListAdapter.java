package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.category.CategoryItemsFragment;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AvailableItemsListAdapter extends RecyclerView.Adapter<AvailableItemsListAdapter.ItemsListViewHolder> {
    private List<AvailNotAvailResponse.Datum.Available> avail_items_list;
    Context context;
    Prefrences prefrences;
    private Bundle bundle;

    public AvailableItemsListAdapter(Context context, List<AvailNotAvailResponse.Datum.Available> avail_items_list) {
        this.avail_items_list = avail_items_list;
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
    public void onBindViewHolder(@NonNull final ItemsListViewHolder holder, final int position) {
        if (!avail_items_list.isEmpty()) {
            Glide.with(holder.ivProduct.getContext()).load(avail_items_list.get(position).getImage()).into(holder.ivProduct);
            holder.mProductNameView.setText(avail_items_list.get(position).getBrand());
            holder.mStoreName.setText(avail_items_list.get(position).getName());
            holder.mAmount.setText(avail_items_list.get(position).getAvgPrice().toString());
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
                if (Integer.valueOf(val) > 1) {
                    count[0] = count[0] - 1;
                    holder.mAvilNotAvailItemsView.setText(count[0] + "");
                } else {
                    holder.mAvilNotAvailItemsView.setText("1");
                }
            }
        });
        holder.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                prefrences.setBottomNavStatus(6);
                CategoryItemsFragment fragment = new CategoryItemsFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle.putInt("product_id",avail_items_list.get(position).getId());
                fragment.setArguments(bundle);
                transaction.replace(R.id.nav_host_fragment, fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (avail_items_list != null) {
            return avail_items_list.size();
        } else {
            return 0;
        }
    }

    public static class ItemsListViewHolder extends RecyclerView.ViewHolder {
        private TextView mProductNameView, mStoreName, mAmount, mAvilNotAvailItemsView;
        private ImageView mIncreaseAvailItems, mDecreaseAvailItems, ivProduct;

        public ItemsListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.iv_product);
            mProductNameView = (TextView) itemView.findViewById(R.id.available_items_list_product_name_view);
            mStoreName = itemView.findViewById(R.id.tv_store_name_avail_not_avail_items);
            mAmount = itemView.findViewById(R.id.tv_amount_avail_not_avail_items);
            mAvilNotAvailItemsView = itemView.findViewById(R.id.view_avil_not_avil_items);
            mIncreaseAvailItems = itemView.findViewById(R.id.increase_available_items);
            mDecreaseAvailItems = itemView.findViewById(R.id.decrees_available_items);
        }
    }
}
