package com.system.user.menwain.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.adapters.DeliveryTimesAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class DialogFragmentDeliveryTime extends DialogFragment implements View.OnClickListener {
    TextView mConfirm,mTitleView;
    ImageView mCloseBtn;
    private RecyclerView recyclerView;
    private String [] delivery_times = {"10:00 - 11:30","11:30 - 12:00","1:30 - 2:30","04:00 - 06:00"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_delivery_time,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mConfirm = view.findViewById(R.id.confirm_btn_delivery_time);
        mTitleView = view.findViewById(R.id.title_view);
        mCloseBtn = view.findViewById(R.id.close_back_view);
        recyclerView = view.findViewById(R.id.recycler_view_delivery_time);

        mTitleView.setText("Delivery time");
        mConfirm.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.HORIZONTAL,false));
        recyclerView.setAdapter(new DeliveryTimesAdapter(getContext(),delivery_times));

        /*mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragmentPurchasingMethod dialogFragmentPurchasingMethod = new DialogFragmentPurchasingMethod();
                dialogFragmentPurchasingMethod.show(getFragmentManager(),"Purchasing Method");
                dismiss();
            }
        });*/
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.confirm_btn_delivery_time:
                DialogFragmentPurchasingMethod dialogFragmentPurchasingMethod = new DialogFragmentPurchasingMethod();
                dialogFragmentPurchasingMethod.show(getFragmentManager(),"Purchasing Method");
                dismiss();
                break;
            case R.id.close_back_view:
                /*DialogFragmentPayMethod dialogFragmentPayNow = new DialogFragmentPayMethod();
                dialogFragmentPayNow.show(getFragmentManager(),"Method Fragment");*/
                dismiss();
                break;
        }
    }
}
