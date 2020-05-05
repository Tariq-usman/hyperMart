package com.system.user.menwain.fragments.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class PaymentFragment extends Fragment {

    TextView mConfirm, tvPayableAmount;
    private EditText etCardHolderName, etCardNumber, etCVC, etExpiry, etZipCode, etCardName, etBillingAddress;
    private LinearLayout updateCardLayout;
    private ImageView mBackBtnPay, mBarCodeScanner;
    private Preferences prefrences;
    private CardView mSearchView;
    private AlertDialog.Builder builder;
    private Dialog dialog;
    private Bundle bundle;
    private boolean isUpdate = false;
    int count = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        customDialog(getContext());
        prefrences = new Preferences(getContext());
        bundle = this.getArguments();

        tvPayableAmount = view.findViewById(R.id.tv_payable_amount);
        tvPayableAmount.setText(prefrences.getTotalAmount() + prefrences.getShippingCost() + "");

        updateCardLayout = view.findViewById(R.id.update_card_layout);
        etCardHolderName = view.findViewById(R.id.et_card_holder_name);
        etCardNumber = view.findViewById(R.id.et_card_number);
        etCVC = view.findViewById(R.id.et_cvc);
        etExpiry = view.findViewById(R.id.et_expairy);
        etZipCode = view.findViewById(R.id.et_zip_code);
        etCardName = view.findViewById(R.id.et_issuing_compeny);
        etBillingAddress = view.findViewById(R.id.et_billing_address);

        if (bundle != null) {
            etCardHolderName.setText(bundle.getString("card_holder_name"));
            etCardNumber.setText(bundle.getString("card_number"));
            etCVC.setText(bundle.getString("card_cvc"));
            etExpiry.setText(bundle.getString("card_expiry"));
            etZipCode.setText(bundle.getString("zip_code"));
            etCardName.setText(bundle.getString("card_name"));
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


        updateCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isUpdate = true;
                updateCardLayout.setVisibility(View.GONE);
                mConfirm.setText("Update");

                etCardHolderName.setFocusable(true);
                etCardHolderName.setCursorVisible(true);
                etCardHolderName.setClickable(true);
                etCardHolderName.setFocusableInTouchMode(true);

                etCardNumber.setFocusable(true);
                etCardNumber.setCursorVisible(true);
                etCardNumber.setClickable(true);
                etCardNumber.setFocusableInTouchMode(true);

                etCVC.setFocusable(true);
                etCVC.setCursorVisible(true);
                etCVC.setClickable(true);
                etCVC.setFocusableInTouchMode(true);

                etExpiry.setFocusable(true);
                etExpiry.setCursorVisible(true);
                etExpiry.setClickable(true);
                etExpiry.setFocusableInTouchMode(true);

                etZipCode.setFocusable(true);
                etZipCode.setCursorVisible(true);
                etZipCode.setClickable(true);
                etZipCode.setFocusableInTouchMode(true);

                etCardName.setFocusable(true);
                etCardName.setCursorVisible(true);
                etCardName.setClickable(true);
                etCardName.setFocusableInTouchMode(true);

                etBillingAddress.setFocusable(true);
                etBillingAddress.setCursorVisible(true);
                etBillingAddress.setClickable(true);
                etBillingAddress.setFocusableInTouchMode(true);

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
                if (isUpdate == true) {
                    int card_id = prefrences.getCardId();
                    updateCurrentCard(card_id);
                } else {
                    DialogFragmentSaveList dialogFragmentSaveList = new DialogFragmentSaveList();
                    dialogFragmentSaveList.show(getFragmentManager(), "Purchasing Method");
                    prefrences.setCartFragStatus(0);
                }
            }
        });
        return view;
    }

    private void updateCurrentCard(int card_id) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.POST, URLs.update_user_card_url + card_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Card has been updated", Toast.LENGTH_SHORT).show();
                isUpdate = false;
                updateCardLayout.setVisibility(View.VISIBLE);
                mConfirm.setText("Pay Now");

                etCardHolderName.setFocusable(false);
                etCardHolderName.setCursorVisible(false);
                etCardHolderName.setClickable(false);
                etCardHolderName.setFocusableInTouchMode(false);

                etCardNumber.setFocusable(false);
                etCardNumber.setCursorVisible(false);
                etCardNumber.setClickable(false);
                etCardNumber.setFocusableInTouchMode(false);

                etCVC.setFocusable(false);
                etCVC.setCursorVisible(false);
                etCVC.setClickable(false);
                etCVC.setFocusableInTouchMode(false);

                etExpiry.setFocusable(false);
                etExpiry.setCursorVisible(false);
                etExpiry.setClickable(false);
                etExpiry.setFocusableInTouchMode(false);

                etZipCode.setFocusable(false);
                etZipCode.setCursorVisible(false);
                etZipCode.setClickable(false);
                etZipCode.setFocusableInTouchMode(false);

                etCardName.setFocusable(false);
                etCardName.setCursorVisible(false);
                etCardName.setClickable(false);
                etCardName.setFocusableInTouchMode(false);

                etBillingAddress.setFocusable(false);
                etBillingAddress.setCursorVisible(false);
                etBillingAddress.setClickable(false);
                etBillingAddress.setFocusableInTouchMode(false);
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("update_error", error.toString());
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
                map.put("issuing_company", etCardName.getText().toString().trim());
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
