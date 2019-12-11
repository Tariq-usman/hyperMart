package com.system.user.menwain.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.activities.MapsActivity;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class DelivieryAddressesAdapter extends RecyclerView.Adapter<DelivieryAddressesAdapter.DeliveryAddressViewHolder> {

    String[] texts;
    private String[] address;
    Context context;
    private int lastPositon = -1;

    public DelivieryAddressesAdapter(String[] address, Context applicationContext) {
        this.address = address;
        this.context = applicationContext;
        //texts = new String[address.length];
    }

    @NonNull
    @Override
    public DeliveryAddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_delivery_address, parent, false);
        DeliveryAddressViewHolder deliveryAddressViewHolder = new DeliveryAddressViewHolder(view);
        return deliveryAddressViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DeliveryAddressViewHolder holder, final int position) {
        if (address.length == position) {
            holder.mMainAddress.setText("Add New");
            holder.mAddress.setVisibility(View.INVISIBLE);
            holder.mAddNewBtn.setVisibility(View.VISIBLE);
            holder.mEditBtn.setVisibility(View.INVISIBLE);
            holder.mAddNewBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mapIntent = new Intent(context, MapsActivity.class);
                    mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(mapIntent);

                }
            });
            return;
        }
        if (lastPositon == position) {
            holder.mAddressesView.setBackgroundColor(Color.parseColor("#004040"));
            holder.mMainAddress.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mAddress.setTextColor(Color.parseColor("#FFFFFF"));
            holder.mEditBtn.setImageResource(R.drawable.ic_edit_white);
        } else {
            holder.mAddressesView.setBackgroundColor(Color.parseColor("#FFFFFF"));
            holder.mMainAddress.setTextColor(Color.parseColor("#004040"));
            holder.mAddress.setTextColor(Color.parseColor("#004040"));
            holder.mEditBtn.setImageResource(R.drawable.ic_edit);
        }
        holder.mMainAddress.setText(address[position]);
        /*holder.mAddress.setText(address[position]);
        holder.mAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                texts[position] = charSequence.toString();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });*/

    }

    @Override
    public int getItemCount() {
        return address.length + 1;
    }

    public class DeliveryAddressViewHolder extends RecyclerView.ViewHolder {
        private TextView mMainAddress, mAddress;
        private ImageView mAddNewBtn, mEditBtn;
        private CardView mCardView;
        private LinearLayout mAddressesView;

        public DeliveryAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            mAddNewBtn = itemView.findViewById(R.id.add_new_address);
            mMainAddress = itemView.findViewById(R.id.main_address_view);
            mAddress = itemView.findViewById(R.id.address_view);
            mEditBtn = itemView.findViewById(R.id.edit_address);
            mCardView = itemView.findViewById(R.id.addresses_card_view);
            mAddressesView = itemView.findViewById(R.id.addresses_layout);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    lastPositon = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
