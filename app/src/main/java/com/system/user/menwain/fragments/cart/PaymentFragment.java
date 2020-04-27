package com.system.user.menwain.fragments.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
    private EditText etCardHolderName, etCardNumber, etCVC, etExpiry, etZipCode, etBillingAddress;
    private ImageView mBackBtnPay, mBarCodeScanner;
    private Preferences prefrences;
    private CardView mSearchView;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        customProgressDialog(getContext());

        tvPayableAmount = view.findViewById(R.id.tv_payable_amount);
        etCardHolderName = view.findViewById(R.id.et_card_holder_name);
        etCardNumber = view.findViewById(R.id.et_card_number);
        etCVC = view.findViewById(R.id.et_cvc);
        etExpiry = view.findViewById(R.id.et_expairy);
        etZipCode = view.findViewById(R.id.et_zip_code);
        etBillingAddress = view.findViewById(R.id.et_billing_address);

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
                prefrences.setCartFragStatus(3);
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AvailNotAvailItemsListsFragment())
                        .addToBackStack(null).commit();

            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCard();
                /*getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment())
                        .addToBackStack(null).commit();*/
                prefrences.setCartFragStatus(0);
            }
        });
        return view;
    }

    private void addCard() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URLs.add_card_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                DialogFragmentSaveList dialogFragmentSaveList = new DialogFragmentSaveList();
                dialogFragmentSaveList.show(getFragmentManager(), "Purchasing Method");
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("pay_error",error.toString());
                progressDialog.dismiss();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headerMap = new HashMap<>();
                headerMap.put("Authorization","Bearer "+prefrences.getToken());
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("holder_name",etCardHolderName.getText().toString().trim());
                map.put("card_number",etCardNumber.getText().toString().trim());
                map.put("zip_code",etZipCode.getText().toString().trim());
                map.put("cvv",etCVC.getText().toString().trim());
                map.put("expiry",etExpiry.getText().toString().trim());
                map.put("is_favorite",0+"");
                map.put("issuing_company","null");
                map.put("card_bill_address",etBillingAddress.getText().toString().trim());
                return map;

            }
        };
        requestQueue.add(request);
    }
    public void customProgressDialog(Context context) {
        progressDialog = new ProgressDialog(context);
        // Setting Message
        progressDialog.setMessage("Loading...");
        // Progress Dialog Style Spinner
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // Fetching max value
        progressDialog.getMax();
    }

}
