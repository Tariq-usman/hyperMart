package com.system.user.menwain.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.activities.PaymentActivity;
import com.system.user.menwain.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentPurchasingMethod extends DialogFragment implements View.OnClickListener {
    TextView mConfirm,mTitleView;
    ImageView mCloseBtn;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_purchasing_method,container,false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mConfirm = view.findViewById(R.id.confirm_btn_purchasing_method);
        mTitleView = view.findViewById(R.id.title_view);
        mCloseBtn = view.findViewById(R.id.close_back_view);

        mTitleView.setText("In Store Purchase");
        mCloseBtn.setOnClickListener(this);
        mConfirm.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        if (id == R.id.confirm_btn_purchasing_method){
            startActivity(new Intent(getContext(), PaymentActivity.class));
            dismiss();
        }else if (id == R.id.close_back_view){
            DialogFragmentDeliveryTime dialogFragmentDeliveryTime = new DialogFragmentDeliveryTime();
            dialogFragmentDeliveryTime.show(getFragmentManager(),"Delivery time");
            dismiss();
        }

    }
}
