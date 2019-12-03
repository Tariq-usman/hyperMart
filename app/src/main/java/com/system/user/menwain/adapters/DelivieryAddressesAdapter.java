package com.system.user.menwain.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.activities.MainActivity;
import com.system.user.menwain.activities.MapsActivity;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
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
    public void onBindViewHolder(@NonNull final DeliveryAddressViewHolder holder, int position) {
        if (address.length == position){
            holder.mMainAddress.setText("Add New");
            holder.mAddress.setVisibility(View.INVISIBLE);
            holder.mAddNewBtn.setVisibility(View.VISIBLE);
            holder.mEditBtn.setVisibility(View.INVISIBLE);
            holder.mAddNewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mapIntent = new Intent(context, MapsActivity.class);
                    /*
                    Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");*/
                    mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mapIntent);

                }
            });
            return;
        }
        holder.mMainAddress.setText(address[position]);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.mAddressesView.setBackgroundColor(Color.parseColor("#004040"));
                holder.mMainAddress.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mAddress.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mEditBtn.setImageResource(R.drawable.ic_editwhite);
            }
        });




    }

    @Override
    public int getItemCount() {
        return address.length+1;
    }

    public static class DeliveryAddressViewHolder extends RecyclerView.ViewHolder{
        TextView mMainAddress,mAddress;
        ImageView mAddNewBtn,mEditBtn;
        CardView mCardView;
        LinearLayout mAddressesView;
        public DeliveryAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            mAddNewBtn = itemView.findViewById(R.id.add_new_address);
            mMainAddress = itemView.findViewById(R.id.main_address_view);
            mAddress = itemView.findViewById(R.id.address_view);
            mEditBtn = itemView.findViewById(R.id.edit_address);
            mCardView = itemView.findViewById(R.id.addresses_card_view);
            mAddressesView = itemView.findViewById(R.id.addresses_layout);
        }
    }
}
