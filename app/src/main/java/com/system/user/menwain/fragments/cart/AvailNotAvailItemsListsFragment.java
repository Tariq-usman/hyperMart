package com.system.user.menwain.fragments.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.others.Prefrences;
import com.system.user.menwain.R;
import com.system.user.menwain.activities.ScanActivity;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentSaveList;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentDeliveryTime;
import com.system.user.menwain.local_db.viewmodel.CartViewModel;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;
import com.system.user.menwain.responses.cart.CalculateShippingCostResponse;
import com.system.user.menwain.utils.URLs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvailNotAvailItemsListsFragment extends Fragment implements View.OnClickListener {
    private CartViewModel cartViewModel;
    private TextView mAvailable, mNotAvailable, mTitle, mTotalAmount, mPrice, mDistance, mAvailItems, mNotAvailItmes;
    private View mShowStatusColor;
    private ImageView mBackBtn, mBarCodeScanner, mMartLogoView;
    private LinearLayout mConfirmBtn;
    private int store_id, avail, not_avial;
    public String price, distance, final_dist;
    String[] split_dist, dist;
    SharedPreferences availPreferences, notAvailPrefrences;
    Bundle bundle;
    private Boolean pay_now, pay_later = false;
    public static Boolean isCheck = false;
    Prefrences prefrences;
    private int pay_status;
    private CardView mSearchView;
    private ProgressDialog progressDialog;
    private List<AvailNotAvailResponse.Datum.Available> avail_items_list = ItemsAvailabilityStoresAdapter.available_list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avail_not_avial_items_lists, container, false);
        customProgressDialog(getContext());
        bundle = this.getArguments();
        if (bundle != null) {
            store_id = bundle.getInt("store_id", 0);
            avail = Integer.parseInt(bundle.getString("available", ""));
            not_avial = Integer.parseInt(bundle.getString("not_available", ""));
            price = bundle.getString("price", "");
            distance = bundle.getString("distance", "");
            split_dist = distance.split(" ");
            final_dist = split_dist[0];
            dist = final_dist.split(".");
        } else {
            store_id = 0;
            avail = 0;
            not_avial = 0;
            price = "0";
            distance = "0";
        }
        prefrences = new Prefrences(getContext());
        getFragmentManager().beginTransaction().replace(R.id.container_items_list, new AvailableItemsFragment()).commit();

        mSearchView = getActivity().findViewById(R.id.search_view);
        mSearchView.setVisibility(View.VISIBLE);
        mBarCodeScanner = getActivity().findViewById(R.id.bar_code_code_scanner_home);
        mBarCodeScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ScanActivity.class);
                startActivity(intent);
            }
        });

        mTotalAmount = view.findViewById(R.id.tv_total_amount_avial_items);
        mTotalAmount.setText(price);
        /*cartViewModel = ViewModelProviders.of(AvailNotAvailItemsListsFragment.this).get(CartViewModel.class);
        cartViewModel.getTotalCartPrice().observe(this, new Observer<Float>() {
            @Override
            public void onChanged(Float aFloat) {
                if (aFloat != null) {
                    mTotalAmount.setText(aFloat.toString());
                } else {
                    mTotalAmount.setText(00.0 + "");
                }
            }
        });*/

        mAvailable = view.findViewById(R.id.available_items);
        mNotAvailable = view.findViewById(R.id.not_available_items);
        mBackBtn = getActivity().findViewById(R.id.iv_back);
        mConfirmBtn = view.findViewById(R.id.confirm_btn_items_list);
        mAvailItems = view.findViewById(R.id.count_avail_items);
        mAvailItems.setText(avail + "");
        mNotAvailItmes = view.findViewById(R.id.count_not_avail_items);
        mNotAvailItmes.setText(not_avial + "");

        mAvailable.setOnClickListener(this);
        mNotAvailable.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);
        mBackBtn.setVisibility(View.VISIBLE);

        /*availPreferences = getActivity().getSharedPreferences("avail_length", Activity.MODE_PRIVATE);
        notAvailPrefrences = getActivity().getSharedPreferences("not_avail_items", Activity.MODE_PRIVATE);
        available_items = availPreferences.getString("available", "");
        not_available_items = notAvailPrefrences.getString("not_available", "");
        Log.i("available", String.valueOf(available_items));

        if (!available_items.isEmpty()) {
            mAvailItems.setText(String.valueOf(available_items));
            mNotAvailItmes.setText(String.valueOf(not_available_items));
        }*/
        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.available_items) {
            mAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_avail_items));
            mAvailable.setTextColor(Color.parseColor("#FFFFFF"));
            mAvailItems.setTextColor(Color.parseColor("#FFFFFF"));
            mAvailItems.setBackgroundResource(R.drawable.bg_avail_not_avail_item_selected);

            mNotAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_not_avail_items));
            mNotAvailItmes.setBackgroundResource(R.drawable.bg_avail_not_avail_item_unselected);
            mNotAvailItmes.setTextColor(Color.parseColor("#004040"));
            mNotAvailable.setTextColor(Color.parseColor("#004040"));
            getFragmentManager().beginTransaction().replace(R.id.container_items_list, new AvailableItemsFragment()).commit();
        } else if (id == R.id.not_available_items) {
            mNotAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_avail_items));
            mNotAvailable.setTextColor(Color.parseColor("#FFFFFF"));
            mNotAvailItmes.setTextColor(Color.parseColor("#FFFFFF"));
            mNotAvailItmes.setBackgroundResource(R.drawable.bg_avail_not_avail_item_selected);

            mAvailable.setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_not_avail_items));
            mAvailable.setTextColor(Color.parseColor("#004040"));
            mAvailItems.setTextColor(Color.parseColor("#004040"));
            mAvailItems.setBackgroundResource(R.drawable.bg_avail_not_avail_item_unselected);
            getFragmentManager().beginTransaction().replace(R.id.container_items_list, new NotAvailableItemsFragment()).commit();
        } else if (id == R.id.confirm_btn_items_list) {
            pay_status = prefrences.getPayRBtnStatus();
            if (avail_items_list.size()>0){

            calculateShippingCost();}else {
                Toast.makeText(getContext(), "There is no avialable items!", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.iv_back) {
            prefrences.setCartFragStatus(2);
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ItemsAvailabilityStoresFragment()).addToBackStack(null).commit();
            mBackBtn.setVisibility(View.GONE);
        }
    }

    private void calculateShippingCost() {
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.calcualte_shipping_cost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CalculateShippingCostResponse shippingCostResponse = gson.fromJson(response,CalculateShippingCostResponse.class);
                prefrences.setShippingCost(shippingCostResponse.getShippingcost());
                if (pay_status == 5) {
                    DialogFragmentSaveList dialogFragmentSaveList = new DialogFragmentSaveList();
                    dialogFragmentSaveList.show(getFragmentManager(), "Purchasing Method");
                } else {
                    //if (pay_status == 2){
                    DialogFragmentDeliveryTime deliveryTime = new DialogFragmentDeliveryTime();
                    deliveryTime.show(getFragmentManager(), "Select Method");
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AvailNotAvailError", error.toString());
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("storeid", String.valueOf(prefrences.getStoreId()));
                map.put("distance", distance);
                map.put("totalprice", price);
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
