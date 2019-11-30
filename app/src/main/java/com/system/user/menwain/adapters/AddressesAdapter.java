package com.system.user.menwain.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AddressesAdapter extends RecyclerView.Adapter<AddressesAdapter.AddressesViewHolder> {

    private String [] address;
    Context context;
    public AddressesAdapter(String[] address, Context applicationContext) {
        this.address = address;
        this.context = applicationContext;
    }

    @NonNull
    @Override
    public AddressesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_delivery_address,parent,false);
        AddressesViewHolder deliveryAddressViewHolder = new AddressesViewHolder(view);
        return deliveryAddressViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddressesViewHolder holder, int position) {
        if (address.length == position){
            holder.mMainAddress.setText("Add New");
            holder.mAddress.setVisibility(View.INVISIBLE);
            holder.mAddNewBtn.setVisibility(View.VISIBLE);
            holder.mEditBtn.setVisibility(View.INVISIBLE);
            return;
        }
        holder.mMainAddress.setText(address[position]);
    }

    @Override
    public int getItemCount() {
        return address.length+1;
    }

    public static class AddressesViewHolder extends RecyclerView.ViewHolder{
        TextView mMainAddress,mAddress;
        ImageView mAddNewBtn,mEditBtn;
        public AddressesViewHolder(@NonNull View itemView) {
            super(itemView);
            mAddNewBtn = itemView.findViewById(R.id.add_new_address);
            mMainAddress = itemView.findViewById(R.id.main_address_view);
            mAddress = itemView.findViewById(R.id.address_view);
            mEditBtn = itemView.findViewById(R.id.edit_address);
        }
    }
}
