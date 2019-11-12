package com.system.user.menwain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.ItemDetailsActivity;
import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FilterItemsAdapter extends RecyclerView.Adapter<FilterItemsAdapter.FilterItemViewHolder> {
    private String[] productsName;
    Context context;

    public FilterItemsAdapter(String[] productsName, Context context) {
        this.productsName = productsName;
        this.context = context;
    }

    @NonNull
    @Override
    public FilterItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_filter_items, parent, false);
        FilterItemViewHolder categoryViewHolder = new FilterItemViewHolder(view);
        return categoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterItemViewHolder holder, int position) {
        holder.mProductNameView.setText(productsName[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
        TextView mProductNameView;

        public FilterItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mProductNameView = itemView.findViewById(R.id.filter_product_name_view);
        }
    }
}
