package com.system.user.menwain.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import com.system.user.menwain.ItemsAvailabilityStoresActivity;
import com.system.user.menwain.ItemsListActivity;
import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ItemsAvailabilityStoresAdapter  extends RecyclerView.Adapter<ItemsAvailabilityStoresAdapter.ItemViewHolder> {
private int [] marts;
Context context;
    public ItemsAvailabilityStoresAdapter(int[] marts, Context context) {
        this.marts = marts;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_available_item_store,parent,false);
        ItemViewHolder itemViewHolder= new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.mMartImageView.setImageResource(marts[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new  Intent(context,ItemsListActivity.class);
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

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView mMartImageView;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mMartImageView  = itemView.findViewById(R.id.mart_logo_view);
        }
    }

}
