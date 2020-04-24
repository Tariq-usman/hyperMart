package com.system.user.menwain.adapters.cart_adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.system.user.menwain.R;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.responses.cart.PaymentTypesResponse;

import java.util.List;

public class PayNaoAdapter extends RecyclerView.Adapter<PayNaoAdapter.PayNowViewHolder> {

    List<PaymentTypesResponse.DataPayNow.Paymenttype> pay_now_list;
    Context context;
    private RecyclerClickInterface clickInterface;
    private int lastPositon = 0;

    public PayNaoAdapter(Context applicationContext, List<PaymentTypesResponse.DataPayNow.Paymenttype> addressList, RecyclerClickInterface clickInterface) {
        this.pay_now_list = addressList;
        this.clickInterface = clickInterface;
        this.context = applicationContext;
        //texts = new String[address.length];
    }

    @NonNull
    @Override
    public PayNowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_pay_now_later, parent, false);
        PayNowViewHolder payNowViewHolder = new PayNowViewHolder(view);
        return payNowViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PayNowViewHolder holder, final int position) {
        holder.radioButton.setText(pay_now_list.get(position).getPaymentMethodDescription());
        if (lastPositon == position) {
            holder.radioButton.setChecked(true);
            clickInterface.interfaceOnClick(holder.itemView, position);
        } else {
            holder.radioButton.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return pay_now_list.size();
    }

    public class PayNowViewHolder extends RecyclerView.ViewHolder {
        private RadioButton radioButton;

        public PayNowViewHolder(@NonNull View itemView) {
            super(itemView);
            radioButton = itemView.findViewById(R.id.radio_button);

            radioButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    lastPositon = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });
        }
    }
}
