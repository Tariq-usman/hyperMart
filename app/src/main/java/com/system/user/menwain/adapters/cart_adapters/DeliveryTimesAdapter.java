package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentTimeSlots;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.responses.cart.StoreTimeSLotsResponse;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DeliveryTimesAdapter extends RecyclerView.Adapter<DeliveryTimesAdapter.ViewHolder> {
    Context context;
    private List<StoreTimeSLotsResponse.List> delivery_times_list;
    private int last_position = 0;
    private RecyclerClickInterface clickInterface;

    public DeliveryTimesAdapter(Context context, List<StoreTimeSLotsResponse.List> delivery_times_list) {
        this.context = context;
        this.delivery_times_list = delivery_times_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_delivery_time, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (delivery_times_list.get(position).getSlots().size() == 0) {
            Log.e("Null_slot", "null");
        } else {
            for (int i = 0; i < delivery_times_list.get(position).getSlots().size(); i++) {
                holder.tvTime.setText(delivery_times_list.get(i).getSlots().get(i).getFrom() + " - " + delivery_times_list.get(i).getSlots().get(i).getTo());
            }
        }
        if (last_position == position) {
            holder.tvTime.setTextColor(Color.parseColor("#00c1bd"));
            DialogFragmentTimeSlots.time_slot_id = delivery_times_list.get(position).getSlots().get(position).getId();
            DialogFragmentTimeSlots.selecte_time = delivery_times_list.get(position).getSlots().get(position).getFrom() + ":" + delivery_times_list.get(position).getSlots().get(position).getTo();
            Log.e("time_id", DialogFragmentTimeSlots.time_slot_id + "");
            last_position=0;
        } else {
            holder.tvTime.setTextColor(Color.parseColor("#004040"));
        }
    }

    @Override
    public int getItemCount() {
        return delivery_times_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tv_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    last_position = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
