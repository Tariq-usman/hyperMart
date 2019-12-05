package com.system.user.menwain.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.activities.ItemsListActivity;
import com.system.user.menwain.R;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
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
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        holder.mMartImageView.setImageResource(marts[position]);
        holder.mDistanceView.setText(distance.get(position).toString());
        holder.mSortByPrice.setText(String.valueOf(price.get(position)));
        String pos = holder.mDistanceView.getText().toString();
        Double abc = distance.get(position);
        for (int i =0; i<marts.length; i++){
            if (Double.valueOf(abc)<= 10){
                holder.mStatusColorView.setBackgroundColor(Color.parseColor("#36F43F"));
            }
            else if (Double.valueOf(abc)>10 && Double.valueOf(abc)<=15) {
                holder.mStatusColorView.setBackgroundColor(Color.parseColor("#FFFFEB3B"));
            }else {
                holder.mStatusColorView.setBackgroundColor(Color.parseColor("#FFF44336"));

            }
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ItemsListActivity.class);
                intent.putExtra("price",holder.mSortByPrice.getText().toString());
                intent.putExtra("distance",holder.mDistanceView.getText().toString());
                intent.putExtra("image_url",marts[position]);

              /*  int color = ((ColorDrawable)holder.mStatusColorView.getBackground()).getColor();
                Log.i("color", String.valueOf(color));*/
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
//                 ((Activity)context).finish();

            }
        });
    }

    @Override
    public int getItemCount() {
        return marts.length;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView mMartImageView;
        private TextView mDistanceView,mSortByPrice;
        private View mStatusColorView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mMartImageView = itemView.findViewById(R.id.mart_logo_view);
            mDistanceView = itemView.findViewById(R.id.distance_view);
            mSortByPrice = itemView.findViewById(R.id.sort_by_price);
            mStatusColorView = itemView.findViewById(R.id.show_status_color_view);
        }
    }

}
