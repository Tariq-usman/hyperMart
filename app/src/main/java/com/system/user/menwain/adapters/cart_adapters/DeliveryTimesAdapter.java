package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DeliveryTimesAdapter extends RecyclerView.Adapter<DeliveryTimesAdapter.ViewHolder> {
    Context context;
    private String[] delivery_times;
    private int last_position=-1;
    public DeliveryTimesAdapter(Context context, String[] delivery_times) {
        this.context = context;
        this.delivery_times = delivery_times;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_delivery_time,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tvTime.setText(delivery_times[position]);
        if (last_position == position){
            holder.tvTime.setTextColor(Color.parseColor("#00c1bd"));
        }else {
            holder.tvTime.setTextColor(Color.parseColor("#004040"));
        }
    }

    @Override
    public int getItemCount() {
        return delivery_times.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    last_position=getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
