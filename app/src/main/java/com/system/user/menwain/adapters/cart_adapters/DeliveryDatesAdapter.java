package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.system.user.menwain.R;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.responses.cart.StoreTimeSLotsResponse;

import java.util.List;

public class DeliveryDatesAdapter extends RecyclerView.Adapter<DeliveryDatesAdapter.ViewHolder> {
    Context context;
    private List<StoreTimeSLotsResponse.List> delivery_dates_list;
    private int last_position=0;
    private RecyclerClickInterface clickInterface;
    public DeliveryDatesAdapter(Context context, List<StoreTimeSLotsResponse.List> delivery_dates_list, RecyclerClickInterface clickInterface) {
        this.context = context;
        this.clickInterface = clickInterface;
        this.delivery_dates_list = delivery_dates_list;
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
        holder.tvTime.setText(delivery_dates_list.get(position).getDate());
        if (last_position == position){
            holder.tvTime.setTextColor(Color.parseColor("#00c1bd"));
            clickInterface.interfaceOnClick(holder.itemView,position);
        }else {
            holder.tvTime.setTextColor(Color.parseColor("#004040"));
        }
    }

    @Override
    public int getItemCount() {
        return delivery_dates_list.size();
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
