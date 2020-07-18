package com.system.user.menwain.fragments.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.activities.MapsActivity;
import com.system.user.menwain.adapters.cart_adapters.PayNaoAdapter;
import com.system.user.menwain.interfaces.RecyclerClickInterface;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.adapters.cart_adapters.DelivieryAddressesAdapter;
import com.system.user.menwain.responses.cart.PaymentTypesResponse;
import com.system.user.menwain.responses.cart.UserAddressListResponse;
import com.system.user.menwain.utils.URLs;
import com.system.user.menwain.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryAddressFragment extends Fragment implements View.OnClickListener, RecyclerClickInterface {

    private TextView mTitleView, mConfirmBtn, mPayNow, mPayLater, tvSelectRadius;
    private ImageView mBackView, mAddNewAddress;
    private String[] address;
    private Double latitude, longitude;
    private RecyclerView recyclerViewAddress, recyclerViewPayNow, recyclerViewPayLater;
    DelivieryAddressesAdapter delivieryAddressesAdapter;
    private PayNaoAdapter payNaoAdapter;
    private CardView mSearchViewAddress, addNewAddress, selectRadius;
    private SharedPreferences.Editor editor;
    Preferences prefrences;
    private Bundle bundle;
    private RadioButton rbDeliverToAddress, rbPreparePickUp, rbCashOnDelivery, rbPreparePickFromStore, rbWalkInStore;
    private RadioGroup rgPayNow, rgPayLater;
    private int rBtnPaymentStatus, delivery_address_id;
    private Dialog dialog;
    private List<UserAddressListResponse.Addresslist> addressList = new ArrayList<UserAddressListResponse.Addresslist>();
    private List<PaymentTypesResponse.DataPayNow> pay_now_list = new ArrayList<PaymentTypesResponse.DataPayNow>();
    private CheckBox checkBox;
    private NumberPicker numberPicker;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delivey_address, container, false);
        prefrences = new Preferences(getContext());
        dialog = Utils.dialog(getContext());
        prefrences.setPaymentStatus(1);
        prefrences.setPayRBtnStatus(1);
        bundle = new Bundle();
        rBtnPaymentStatus = prefrences.getPayRBtnStatus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


//        setRadioButtonChecked();
        getUserAddress();

        addNewAddress = view.findViewById(R.id.add_address_card_view);
        addNewAddress.setOnClickListener(this);
        selectRadius = view.findViewById(R.id.select_radius);
        selectRadius.setOnClickListener(this);

        checkBox = view.findViewById(R.id.cb_select_radius);
        checkBox.setOnClickListener(this);
        if (prefrences.getOrderStatus() == 1) {
            checkBox.setChecked(true);
            selectRadius.setVisibility(View.VISIBLE);
        } else {
            checkBox.setChecked(false);
            selectRadius.setVisibility(View.GONE);
        }
        tvSelectRadius = view.findViewById(R.id.tv_select_radius);
        rbDeliverToAddress = view.findViewById(R.id.rb_deliver_to_address);
        rbDeliverToAddress.setOnClickListener(this);
        rbPreparePickUp = view.findViewById(R.id.rb_prepare_pick_fr_store);
        rbPreparePickUp.setOnClickListener(this);

        if (rBtnPaymentStatus == 1) {
            rbPreparePickUp.setChecked(false);
            rbDeliverToAddress.setChecked(true);
        } else if (rBtnPaymentStatus == 2) {
            rbPreparePickUp.setChecked(true);
            rbDeliverToAddress.setChecked(false);
        }

        rbCashOnDelivery = view.findViewById(R.id.rb_cash_on_delivery);
        rbCashOnDelivery.setOnClickListener(this);
        rbPreparePickFromStore = view.findViewById(R.id.rb_prepare_and_pick);
        rbPreparePickFromStore.setOnClickListener(this);
        rbWalkInStore = view.findViewById(R.id.rb_Walk_in_tore);
        rbWalkInStore.setOnClickListener(this);

        rgPayNow = view.findViewById(R.id.rg_pay_now);
        rgPayLater = view.findViewById(R.id.rg_pay_later);
        mBackView = view.findViewById(R.id.iv_back_delivery_address);
        mAddNewAddress = view.findViewById(R.id.add_new_address);
        mConfirmBtn = view.findViewById(R.id.confirm_btn);
        mPayNow = view.findViewById(R.id.pay_now_delivery_adr);
        mPayLater = view.findViewById(R.id.pay_later_delivery_adr);

        mConfirmBtn.setOnClickListener(this);
        mBackView.setOnClickListener(this);
        mPayNow.setOnClickListener(this);
        mPayLater.setOnClickListener(this);
        mAddNewAddress.setOnClickListener(this);


        recyclerViewAddress = view.findViewById(R.id.recycler_view_delivery_address);
        recyclerViewAddress.setHasFixedSize(true);
        recyclerViewAddress.setLayoutManager(new LinearLayoutManager(getContext()));

        delivieryAddressesAdapter = new DelivieryAddressesAdapter(getContext(), addressList, this);
        recyclerViewAddress.setAdapter(delivieryAddressesAdapter);
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                int address_id = addressList.get(position).getId();
                deleteAddress(position, address_id);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerViewAddress);

        numberPicker = view.findViewById(R.id.number_picker);
        numberPicker.setMaxValue(0);
        numberPicker.setMaxValue(60);
        numberPicker.setWrapSelectorWheel(true);
        numberPicker.setValue(prefrences.getRadius());
        numberPicker.setFormatter(new NumberPicker.Formatter() {
            @Override
            public String format(int value) {
                return String.format("%02d", value);
            }
        });
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                prefrences.setRadius(newVal);
            }
        });
        return view;
    }

    private void deleteAddress(final int position, final int address_id) {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(Request.Method.DELETE, URLs.delete_address_url + address_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getContext(), "Address has been deleted.", Toast.LENGTH_SHORT).show();
                //Remove swiped item from list and notify the RecyclerView
                addressList.remove(position);
                delivieryAddressesAdapter.notifyItemRemoved(position);
                delivieryAddressesAdapter.notifyItemRangeChanged(position, addressList.size());
                delivieryAddressesAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("address_error", error.toString());
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
                map.put("id", address_id + "");
                return map;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserAddress();
    }

    @Override
    public void interfaceOnClick(View view, int position) {
        delivery_address_id = addressList.get(position).getId();
        prefrences.setDeliveryAddressId(delivery_address_id);
        latitude = Double.valueOf(addressList.get(position).getLatitude());
        longitude = Double.valueOf(addressList.get(position).getLongitude());
    }

    @Override
    public void onClick(View view) {
        ItemsAvailabilityStoresFragment itemsAvailabilityStoresFragment = new ItemsAvailabilityStoresFragment();
        String backStateName = itemsAvailabilityStoresFragment.getClass().getName();
        int id = view.getId();
        if (id == R.id.pay_now_delivery_adr) {
            rgPayNow.setVisibility(View.VISIBLE);
            rgPayLater.setVisibility(View.GONE);
            prefrences.setPaymentStatus(1);
            prefrences.setPayRBtnStatus(1);
            mPayNow.setBackgroundResource(R.drawable.bg_store_btn_colored);
            mPayLater.setBackgroundResource(0);
            mPayNow.setTextColor(Color.parseColor("#FFFFFF"));
            mPayLater.setTextColor(Color.parseColor("#004040"));
        } else if (id == R.id.pay_later_delivery_adr) {
            rgPayNow.setVisibility(View.GONE);
            rgPayLater.setVisibility(View.VISIBLE);
            prefrences.setPaymentStatus(2);
            prefrences.setPayRBtnStatus(3);
            mPayLater.setBackgroundResource(R.drawable.bg_store_btn_colored);
            mPayNow.setBackgroundResource(0);
            mPayNow.setTextColor(Color.parseColor("#004040"));
            mPayLater.setTextColor(Color.parseColor("#FFFFFF"));
        } else if (id == R.id.rb_deliver_to_address) {
            prefrences.setPayRBtnStatus(1);
            rbPreparePickUp.setChecked(false);
            rbDeliverToAddress.setChecked(true);
        } else if (id == R.id.rb_prepare_pick_fr_store) {
            prefrences.setPayRBtnStatus(2);
            rbPreparePickUp.setChecked(true);
            rbDeliverToAddress.setChecked(false);
        } else if (id == R.id.rb_cash_on_delivery) {
            prefrences.setPayRBtnStatus(3);
            rbWalkInStore.setChecked(false);
            rbPreparePickFromStore.setChecked(false);
            rbCashOnDelivery.setChecked(true);
        } else if (id == R.id.rb_prepare_and_pick) {
            prefrences.setPayRBtnStatus(4);
            rbWalkInStore.setChecked(false);
            rbPreparePickFromStore.setChecked(true);
            rbCashOnDelivery.setChecked(false);
        } else if (id == R.id.rb_Walk_in_tore) {
            prefrences.setPayRBtnStatus(5);
            rbWalkInStore.setChecked(true);
            rbPreparePickFromStore.setChecked(false);
            rbCashOnDelivery.setChecked(false);
        } else if (id == R.id.cb_select_radius) {
            if (!checkBox.isChecked()) {
                checkBox.setChecked(false);
                prefrences.setOrderStatus(0);
                selectRadius.setVisibility(View.GONE);
            } else {
                checkBox.setChecked(true);
                prefrences.setOrderStatus(1);
                selectRadius.setVisibility(View.VISIBLE);
            }
        } else if (id == R.id.confirm_btn) {
            prefrences.setCartFragStatus(2);
            prefrences.setDeliveryAddressId(delivery_address_id);
            ItemsAvailabilityStoresFragment storesFragment = new ItemsAvailabilityStoresFragment();
            FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            prefrences.setDeliverAddress(Double.valueOf(latitude) + " " + Double.valueOf(longitude));
            storesFragment.setArguments(bundle);
            transaction.replace(R.id.nav_host_fragment, storesFragment).addToBackStack(null).commit();
        } else if (id == R.id.iv_back_delivery_address) {
            prefrences.setCartFragStatus(0);
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new CartFragment()).addToBackStack(null).commit();
        } else if (id == R.id.add_address_card_view) {
            Intent intent = new Intent(getContext(), MapsActivity.class);
            intent.putExtra("address_id", -1);
            startActivity(intent);
        }

    }

    private void getUserAddress() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.GET, URLs.get_user_address_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
              /*  PaymentTypesResponse paymentTypesResponse = gson.fromJson(response,PaymentTypesResponse.class);
                for (int i = 0;i<paymentTypesResponse.getDataPayNow().size();i++){
                    pay_now_list.add(paymentTypesResponse.getDataPayNow().get(i));
                }*/


                UserAddressListResponse addressListResponse = gson.fromJson(response, UserAddressListResponse.class);
                addressList.clear();
                for (int i = 0; i < addressListResponse.getAddresslist().size(); i++) {
                    addressList.add(addressListResponse.getAddresslist().get(i));
                }
                addressList.size();
                delivieryAddressesAdapter.notifyDataSetChanged();
                Log.i("Address_response", response);
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Address_error", error.toString());
                dialog.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put("Authorization", "Bearer " + prefrences.getToken());
                headerMap.put("Accept", "application/json");
                return headerMap;
            }
        };
        requestQueue.add(request);
        request.setRetryPolicy(new DefaultRetryPolicy(10000,0,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}
