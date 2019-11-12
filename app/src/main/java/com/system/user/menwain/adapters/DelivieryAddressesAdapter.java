package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DelivieryAddressesAdapter extends RecyclerView.Adapter<DelivieryAddressesAdapter.DeliveryAddressViewHolder> {

    private String [] address;
    Context context;
    public DelivieryAddressesAdapter(String[] address, Context applicationContext) {
        this.address = address;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public DeliveryAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_delivery_address,parent,false);
        DeliveryAddressViewHolder deliveryAddressViewHolder = new DeliveryAddressViewHolder(view);
        return deliveryAddressViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeliveryAddressViewHolder holder, int position) {
        holder.mMainAddress.setText(address[position]);
    }

    @Override
    public int getItemCount() {
        return address.length;
    }

    public static class DeliveryAddressViewHolder extends RecyclerView.ViewHolder{
        TextView mMainAddress;
        public DeliveryAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            mMainAddress = itemView.findViewById(R.id.main_address_view);
        }
    }
}
