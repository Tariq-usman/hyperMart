package com.system.user.menwain.fragments.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.Prefrences;
import com.system.user.menwain.R;

public class PaymentFragment extends Fragment {

    TextView mConfirm,mPaymentTitle;
    private ImageView mBackBtnPay,mMenuPay;
    Prefrences prefrences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment,container,false);

        prefrences =new Prefrences(getContext());
        mConfirm = view.findViewById(R.id.proceed_btn_payment);

        mBackBtnPay = getActivity().findViewById(R.id.iv_back);
        mBackBtnPay.setVisibility(View.VISIBLE);
        mBackBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setCartFragStatus(3);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,new AvailNotAvailItemsListsFragment())
                        .addToBackStack(null).commit();

            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefrences.setCartFragStatus(0);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment())
                        .addToBackStack(null).commit();
                mBackBtnPay.setVisibility(View.INVISIBLE);
            }
        });
        return view;
    }
}
