package com.system.user.menwain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.ItemDetailsActivity;
import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterItemsAdapter extends RecyclerView.Adapter<FilterItemsAdapter.FilterItemViewHolder> {
    private String[] productsName;
    Context context;
    private int[] items;
    private String [] storesName;

    public FilterItemsAdapter(String[] productsName, Context context, int[] items, String[] storesName) {
        this.productsName = productsName;
        this.context = context;
        this.items = items;
        this.storesName = storesName;
    }

    @NonNull
    @Override
    public FilterItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_items, parent, false);
        FilterItemViewHolder categoryViewHolder = new FilterItemViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FilterItemViewHolder holder, int position) {
        holder.mProductNameView.setText(productsName[position]);
        holder.mFilteProduct.setImageResource(items[position]);
        holder.mStoreName.setText(storesName[position]);
        final int[] count = {0};
        holder.mIncreaseItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count[0] = count[0] + 1;
                holder.mItemCounter.setText(""+count[0]);
            }
        });
        holder.mDecreaseItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String num =holder.mItemCounter.getText().toString();
                if (Integer.valueOf(num) > 0) {
                    count[0] = count[0] - 1;
                    holder.mItemCounter.setText("" + count[0]);
                }else if (holder.mItemCounter.getText().toString().length()==0){
                    holder.mItemCounter.setText(0);
                }
            }
        });

        holder.mFilteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
//                             FragmentManager fragmentManager =   ((AppCompatActivity)context).getSupportFragmentManager();
//                             fragmentManager.beginTransaction().replace(R.id.nav_host_fragment, new ItemsFragment()).addToBackStack(null).commit();
//                AppCompatActivity activity = (AppCompatActivity) view.getContext();
//                Fragment myFragment = new ItemsFragment();
//                activity.getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, myFragment).addToBackStack(null).commit();


            }
        });
    }

    @Override
    public int getItemCount() {
        return productsName.length;
    }

    public static class FilterItemViewHolder extends RecyclerView.ViewHolder {
        TextView mProductNameView, mStoreName, mItemCounter;
        ImageView mFilteProduct, mIncreaseItems, mDecreaseItems;
        int count = 0;
        public FilterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mFilteProduct = itemView.findViewById(R.id.view_filter_product);
            mProductNameView = itemView.findViewById(R.id.filter_product_name_view);
            mIncreaseItems = itemView.findViewById(R.id.increase_items);
            mDecreaseItems = itemView.findViewById(R.id.decrees_item);
            mStoreName = itemView.findViewById(R.id.filter_store_name);
            mItemCounter = itemView.findViewById(R.id.items_counter);

        }
    }
}
