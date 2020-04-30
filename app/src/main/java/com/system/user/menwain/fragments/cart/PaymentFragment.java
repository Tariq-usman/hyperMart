package com.system.user.menwain.fragments.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentSaveList;
import com.system.user.menwain.utils.URLs;

import java.util.HashMap;
import java.util.Map;

public class PaymentFragment extends Fragment {

    TextView mConfirm, tvPayableAmount;
    private TextView etCardHolderName, etCardNumber, etCVC, etExpiry, etZipCode, etBillingAddress;
    private ImageView mBackBtnPay, mBarCodeScanner;
    private Preferences prefrences;
    private CardView mSearchView;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        customDialog(getContext());
        prefrences = new Preferences(getContext());
        bundle=this.getArguments();

        tvPayableAmount = view.findViewById(R.id.tv_payable_amount);
        tvPayableAmount.setText(prefrences.getTotalAmount() + prefrences.getShippingCost() + "");
        etCardHolderName = view.findViewById(R.id.et_card_holder_name);
        etCardNumber = view.findViewById(R.id.et_card_number);
        etCVC = view.findViewById(R.id.et_cvc);
        etExpiry = view.findViewById(R.id.et_expairy);
        etZipCode = view.findViewById(R.id.et_zip_code);
        etBillingAddress = view.findViewById(R.id.et_billing_address);
        if (bundle!=null){
            etCardHolderName.setText(bundle.getString("card_holder_name"));
            etCardNumber.setText(bundle.getString("card_number"));
            etCVC.setText(bundle.getString("card_cvc"));
            etExpiry.setText(bundle.getString("card_expiry"));
            etZipCode.setText(bundle.getString("zip_code"));
            etBillingAddress.setText(bundle.getString("billing_address"));
        }

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
        prefrences = new Preferences(getContext());
        mConfirm = view.findViewById(R.id.proceed_btn_payment);

        mBackBtnPay = getActivity().findViewById(R.id.iv_back);
        mBackBtnPay.setVisibility(View.VISIBLE);
        mBackBtnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                prefrences.setCartFragStatus(4);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AddCardFragment())
                        .addToBackStack(null).commit();

            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragmentSaveList dialogFragmentSaveList = new DialogFragmentSaveList();
                dialogFragmentSaveList.show(getFragmentManager(), "Purchasing Method");
                prefrences.setCartFragStatus(0);
            }
        });
        return view;
    }

    public void customDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.layout_loading_dialog);
        }
        dialog = builder.create();
    }

}
