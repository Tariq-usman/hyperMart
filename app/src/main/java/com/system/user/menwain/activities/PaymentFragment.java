package com.system.user.menwain.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.R;
import com.system.user.menwain.fragments.CartFragment;

public class PaymentFragment extends Fragment {

    TextView mConfirm,mPaymentTitle;
    private ImageView mBackBtnPay,mMenuPay;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment,container,false);


        mConfirm = view.findViewById(R.id.proceed_btn_payment);
      /*  mMenuPay = getActivity().findViewById(R.id.iv_open_drawer);
        mMenuPay.setVisibility(View.GONE);*/
        mPaymentTitle = getActivity().findViewById(R.id.toolbar_title);
        mPaymentTitle.setText("Payment");

        mBackBtnPay = getActivity().findViewById(R.id.iv_back);
        mBackBtnPay.setVisibility(View.VISIBLE);
        mBackBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new ItemsListFragment())
                        .addToBackStack(null).commit();
                mPaymentTitle.setText("Items List");

            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment())
                        .addToBackStack(null).commit();
                //startActivity(new Intent(getContext(),MainActivity.class));
               // getActivity().finish();
            }
        });
        return view;
    }
}
