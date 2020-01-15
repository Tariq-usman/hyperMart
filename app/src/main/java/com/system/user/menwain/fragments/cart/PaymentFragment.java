package com.system.user.menwain.fragments.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.system.user.menwain.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentPurchasingMethod;

public class PaymentFragment extends Fragment {

    TextView mConfirm,mPaymentTitle;
    private ImageView mBackBtnPay,mBarCodeScanner;
    Prefrences prefrences;
    private CardView mSearchView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment,container,false);
        mSearchView = getActivity().findViewById(R.id.search_view);
        mSearchView.setVisibility(View.INVISIBLE);
        mBarCodeScanner = getActivity().findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });
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
                DialogFragmentPurchasingMethod dialogFragmentPurchasingMethod = new DialogFragmentPurchasingMethod();
                dialogFragmentPurchasingMethod.show(getFragmentManager(),"Purchasing Method");
                /*getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment())
                        .addToBackStack(null).commit();*/
                prefrences.setCartFragStatus(0);
            }
        });
        return view;
    }
}
