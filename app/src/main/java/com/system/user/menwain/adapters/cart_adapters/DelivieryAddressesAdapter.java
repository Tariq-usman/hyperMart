package com.system.user.menwain.adapters.cart_adapters;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.system.user.menwain.R;
import com.system.user.menwain.activities.MapsActivity;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.responses.cart.PaymentTypesResponse;
import com.system.user.menwain.responses.cart.UserAddressListResponse;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static androidx.recyclerview.widget.ItemTouchHelper.LEFT;

public class DelivieryAddressesAdapter extends RecyclerView.Adapter<DelivieryAddressesAdapter.DeliveryAddressViewHolder> {

    List<UserAddressListResponse.Addresslist> addressList;
    Context context;
    private RecyclerClickInterface clickInterface;
    private int lastPositon = 0;

    public DelivieryAddressesAdapter(Context applicationContext, List<UserAddressListResponse.Addresslist> addressList, RecyclerClickInterface clickInterface) {
        this.addressList = addressList;
        this.clickInterface = clickInterface;
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

        if (addressList.size() > 0) {
            if (lastPositon == position) {
                holder.mAddressesView.setBackgroundColor(Color.parseColor("#004040"));
                holder.mMainAddress.setTextColor(Color.parseColor("#FFFFFF"));
                holder.mEditBtn.setImageResource(R.drawable.ic_edit_white);
                clickInterface.interfaceOnClick(holder.itemView,position);
                holder.mEditBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mapIntent = new Intent(context, MapsActivity.class);
                        mapIntent.putExtra("address_id",addressList.get(position).getId());
                        mapIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(mapIntent);
                    }
                });
            } else {
                holder.mAddressesView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                holder.mMainAddress.setTextColor(Color.parseColor("#004040"));
                holder.mEditBtn.setImageResource(R.drawable.ic_edit);
            }
        }
        holder.mMainAddress.setText(addressList.get(position).getAddressLine1());
    }




    @Override
    public int getItemCount() {
        return addressList.size();
    }

    public class DeliveryAddressViewHolder extends RecyclerView.ViewHolder {
        private TextView mMainAddress;
        private EditText mAddress;
        private ImageView mAddNewBtn, mEditBtn;
        private CardView mCardView;
        private FrameLayout mAddressesView;

        public DeliveryAddressViewHolder(@NonNull View itemView) {
            super(itemView);
            mMainAddress = itemView.findViewById(R.id.main_address_view);
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
