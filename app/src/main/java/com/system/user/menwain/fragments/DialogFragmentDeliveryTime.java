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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentDeliveryTime extends DialogFragment implements View.OnClickListener {
    TextView mConfirm,mTitleView;
    ImageView mCloseBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_delivery_time,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mConfirm = view.findViewById(R.id.confirm_btn_delivery_time);
        mTitleView = view.findViewById(R.id.title_view);
        mCloseBtn = view.findViewById(R.id.close_back_view);

        mTitleView.setText("Delivery time");
        mConfirm.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);

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
                DialogFragmentPayNow dialogFragmentPayNow = new DialogFragmentPayNow();
                dialogFragmentPayNow.show(getFragmentManager(),"Method Fragment");
                dismiss();
                break;
        }
    }
}
