package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.responses.cart.StoreTimeSLotsResponse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeliveryTimesAdapter extends RecyclerView.Adapter<DeliveryTimesAdapter.ViewHolder> {
    Context context;
    private List<StoreTimeSLotsResponse.List> delivery_times_list;
    private int last_position=-1;
    private RecyclerClickInterface clickInterface;
    public DeliveryTimesAdapter(Context context, List<StoreTimeSLotsResponse.List> delivery_times_list,RecyclerClickInterface clickInterface) {
        this.context = context;
        this.clickInterface = clickInterface;
        this.delivery_times_list = delivery_times_list;
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
        holder.tvTime.setText(delivery_times_list.get(position).getFrom()+" - " +delivery_times_list.get(position).getTo());
        if (last_position == position){
            holder.tvTime.setTextColor(Color.parseColor("#00c1bd"));
            clickInterface.interfaceOnClick(holder.itemView,position);
        }else {
            holder.tvTime.setTextColor(Color.parseColor("#004040"));
        }
    }

    @Override
    public int getItemCount() {
        return delivery_times_list.size();
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
