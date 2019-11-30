package com.system.user.menwain.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.activities.ItemsListActivity;
import com.system.user.menwain.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAvailabilityStoresAdapter extends RecyclerView.Adapter<ItemsAvailabilityStoresAdapter.ItemViewHolder> {
    private int[] marts;
    Context context;
    List<Double> distance;
    List<Integer> price;

    public ItemsAvailabilityStoresAdapter(int[] marts, List<Double> distance, List<Integer> price, Context context) {
        this.marts = marts;
        this.context = context;
        this.distance = distance;
        this.price = price;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_available_item_store, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.mMartImageView.setImageResource(marts[position]);
        holder.mDistanceView.setText(distance.get(position).toString());
        holder.mSortByPrice.setText(String.valueOf(price.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemsListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                // ((Activity)context).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return marts.length;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView mMartImageView;
        private TextView mDistanceView,mSortByPrice;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mMartImageView = itemView.findViewById(R.id.mart_logo_view);
            mDistanceView = itemView.findViewById(R.id.distance_view);
            mSortByPrice = itemView.findViewById(R.id.sort_by_price);
        }
    }

}
