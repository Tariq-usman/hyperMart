package com.system.user.menwain.fragments.cart.dialog_fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.fragments.cart.AddCardFragment;
import com.system.user.menwain.fragments.cart.AvailNotAvailItemsListsFragment;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentSaveList;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.utils.URLs;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DialogFragmentAddCard extends DialogFragment {

    TextView mConfirm, tvPayableAmount;
    private EditText etCardHolderName, etCardNumber, etCVC, etExpiry, etZipCode, etBillingAddress;
    private ImageView mBackBtnPay, mBarCodeScanner;
    private Preferences prefrences;
    private CardView mSearchView;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    int count = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_add_card, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog(getContext());

        etCardHolderName = view.findViewById(R.id.et_card_holder_name_add_card);
        etCardNumber = view.findViewById(R.id.et_card_number_add_card);
        etCVC = view.findViewById(R.id.et_cvc_add_card);
        etExpiry = view.findViewById(R.id.et_expairy_add_card);
        etZipCode = view.findViewById(R.id.et_zip_code_add_card);
        etBillingAddress = view.findViewById(R.id.et_billing_address_add_card);

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
        mConfirm = view.findViewById(R.id.confirm_btn_add_card);

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



        etCardNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int inputlength = etCardNumber.getText().toString().length();
                if (count <= inputlength && inputlength == 4 ||
                        inputlength == 9 || inputlength == 14){
                    etCardNumber.setText(etCardNumber.getText().toString() + " ");

                    int pos = etCardNumber.getText().length();
                    etCardNumber.setSelection(pos);
                }else if (count >= inputlength && (inputlength == 4 ||
                        inputlength == 9 || inputlength == 14)) {
                    etCardNumber.setText(etCardNumber.getText().toString()
                            .substring(0, etCardNumber.getText()
                                    .toString().length() - 1));

                    int pos = etCardNumber.getText().length();
                    etCardNumber.setSelection(pos);
                }
                count = etCardNumber.getText().toString().length();
            }
        });

        etExpiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String working = s.toString();
                boolean isValid = true;
                etExpiry.setError(null);
                if (working.length() == 2 && before == 0) {
                    if (Integer.parseInt(working) < 1 || Integer.parseInt(working) > 12) {
                        isValid = false;
                    } else {
                        working += "/";
                        etExpiry.setText(working);
                        etExpiry.setSelection(working.length());
                    }
                } else if (working.length() == 5 && before == 0) {
                    String enteredYear = working.substring(3);

                    DateFormat df = new SimpleDateFormat("yy"); // Just the year, with 2 digits
                    String formattedDate = df.format(Calendar.getInstance().getTime());
                    int currentYear = Integer.parseInt(formattedDate);
                    if (Integer.parseInt(enteredYear) < currentYear) {
                        isValid = false;
                    }
                } else if (working.length() != 5) {
                    isValid = false;
                } else
                    isValid = true;

                if (!isValid) {
                    etExpiry.setError("Enter a valid date: MM/YY");
                } else {
                    etExpiry.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etCardHolderName.getText().toString().isEmpty()) {
                    etCardHolderName.setError("Enter name");
                } else if (etCardNumber.getText().toString().isEmpty()) {
                    etCardNumber.setError("Enter card number");
                } else if (etCVC.getText().toString().isEmpty()) {
                    etCVC.setError("Enter CVC");
                } else if (etExpiry.getText().toString().isEmpty()) {
                    etExpiry.setError("Enter Expiry");
                } else if (etZipCode.getText().toString().isEmpty()) {
                    etZipCode.setError("Enter ZipCode");
                } else if (etBillingAddress.getText().toString().isEmpty()) {
                    etBillingAddress.setError("Enter Billing Address");
                } else {
                    addCard();
                }
                /*getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment())
                        .addToBackStack(null).commit();*/
//                prefrences.setCartFragStatus(0);
            }
        });
        return view;
    }

    private void addCard() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URLs.add_card_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new AddCardFragment()).commit();
                dismiss();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("pay_error", error.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + prefrences.getToken());
                return headerMap;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("holder_name", etCardHolderName.getText().toString().trim());
                map.put("card_number", etCardNumber.getText().toString().trim());
                map.put("zip_code", etZipCode.getText().toString().trim());
                map.put("cvv", etCVC.getText().toString().trim());
                map.put("expiry", etExpiry.getText().toString().trim());
                map.put("is_favorite", 0 + "");
                map.put("issuing_company", "null");
                map.put("card_bill_address", etBillingAddress.getText().toString().trim());
                return map;

            }
        };
        requestQueue.add(request);
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
