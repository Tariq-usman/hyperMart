package com.system.user.menwain.fragments.cart.dialog_fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.ItemsAvailabilityStoresFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogFragmentPayMethod extends DialogFragment implements View.OnClickListener {
    private TextView mConfirm, mTitleView;
    private ImageView mCloseBtn;
    private RadioButton rbPayNow, rbPayLater;
    Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_pay_method, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        rbPayLater = view.findViewById(R.id.rb_pay_later);
        rbPayNow = view.findViewById(R.id.rb_pay_now);
        mConfirm = view.findViewById(R.id.confirm_dialog_one);
        mTitleView = view.findViewById(R.id.title_view);
        mCloseBtn = view.findViewById(R.id.close_back_view);
        bundle = new Bundle();

        mTitleView.setText("Select method");
        mConfirm.setOnClickListener(this);
        mCloseBtn.setOnClickListener(this);

        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm_dialog_one:
                if (rbPayNow.isChecked()){
                    bundle.putBoolean("pay_now",true);
                }else if (rbPayLater.isChecked()){
                    bundle.putBoolean("pay_later", true);
                }
                ItemsAvailabilityStoresFragment storesFragment = new ItemsAvailabilityStoresFragment();
                storesFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, storesFragment).addToBackStack(null).commit();
                dismiss();
                break;
            case R.id.close_back_view:
                dismiss();
                break;
        }

    }
}
