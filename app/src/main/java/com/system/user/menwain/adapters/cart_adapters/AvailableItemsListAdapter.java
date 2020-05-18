package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.system.user.menwain.fragments.cart.AvailNotAvailItemsListsFragment;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.category.CategoryFragment;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AvailableItemsListAdapter extends RecyclerView.Adapter<AvailableItemsListAdapter.ItemsListViewHolder> {
    private List<AvailNotAvailResponse.Datum.Available> avail_items_list;
    Context context;
    Preferences prefrences;
    private Bundle bundle;
    private RecyclerClickInterface clickInterface;
    int addAmount, current_amount, final_amount;
    int pos;
    int total_amount = Integer.parseInt(AvailNotAvailItemsListsFragment.mTotalAmount.getText().toString());
    public static List<Integer> quantity_list = new ArrayList<>();
    public static List<Integer> amount_list = new ArrayList<>();

    public AvailableItemsListAdapter(Context context, List<AvailNotAvailResponse.Datum.Available> avail_items_list, RecyclerClickInterface clickInterface) {
        this.avail_items_list = avail_items_list;
        this.context = context;
        this.clickInterface = clickInterface;
        prefrences = new Preferences(context);
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
            holder.mProductNameView.setText(avail_items_list.get(position).getName());
            holder.mStoreName.setText(avail_items_list.get(position).getBrand());
            holder.mAmount.setText(avail_items_list.get(position).getStoreprice().toString());
            quantity_list.clear();
            amount_list.clear();
            for (int i = 0; i < avail_items_list.size(); i++) {
                quantity_list.add(Integer.valueOf(holder.mAvilNotAvailItemsView.getText().toString()));
                amount_list.add(Integer.valueOf(holder.mAmount.getText().toString()));
            }
        }
        final String currentItems = holder.mAvilNotAvailItemsView.getText().toString();

        final int[] count = {Integer.parseInt(currentItems)};
        holder.mIncreaseAvailItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] = count[0] + 1;
                holder.mAvilNotAvailItemsView.setText(String.valueOf(count[0]));
                addAmount = avail_items_list.get(position).getStoreprice();
                current_amount = Integer.parseInt(holder.mAmount.getText().toString());
                final_amount = addAmount + current_amount;
                pos = holder.getAdapterPosition();

                for (int i = 0; i < avail_items_list.size(); i++) {
                    if (position == pos) {
                        holder.mAmount.setText(final_amount + "");
                        quantity_list.remove(pos);
                        quantity_list.add(pos, Integer.valueOf(holder.mAvilNotAvailItemsView.getText().toString()));
                        amount_list.remove(pos);
                        amount_list.add(pos, Integer.valueOf(holder.mAmount.getText().toString()));
                    }
                }
//                Log.e("quantity", quantity_list.size() + "");
                total_amount = total_amount + avail_items_list.get(position).getStoreprice();
                AvailNotAvailItemsListsFragment.mTotalAmount.setText(total_amount + "");
            }
        });
        holder.mDecreaseAvailItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String val = holder.mAvilNotAvailItemsView.getText().toString();
                addAmount = avail_items_list.get(position).getStoreprice();
                current_amount = Integer.parseInt(holder.mAmount.getText().toString());
                final_amount = current_amount - addAmount;
                pos = holder.getAdapterPosition();

                if (Integer.valueOf(val) > 1) {
                    count[0] = count[0] - 1;
                    holder.mAvilNotAvailItemsView.setText(count[0] + "");

                    for (int i = 0; i < avail_items_list.size(); i++) {
                        if (position == pos) {
                            holder.mAmount.setText(final_amount + "");
                            quantity_list.remove(pos);
                            quantity_list.add(pos, Integer.valueOf(holder.mAvilNotAvailItemsView.getText().toString()));
                            amount_list.remove(pos);
                            amount_list.add(pos, Integer.valueOf(holder.mAmount.getText().toString()));
                        }
                    }
                    total_amount = total_amount - avail_items_list.get(position).getStoreprice();
                    AvailNotAvailItemsListsFragment.mTotalAmount.setText(total_amount + "");
                } else {
                    holder.mAvilNotAvailItemsView.setText("1");
                    holder.mAmount.setText(avail_items_list.get(position).getStoreprice().toString());
                }
            }
        });
        holder.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle = new Bundle();
                prefrences.setBottomNavStatus(6);
                CategoryFragment fragment = new CategoryFragment();
                FragmentTransaction transaction = ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction();
                bundle.putInt("product_id", avail_items_list.get(position).getId());
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
