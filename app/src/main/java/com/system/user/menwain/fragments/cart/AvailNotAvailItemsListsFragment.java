package com.system.user.menwain.fragments.cart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
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
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.system.user.menwain.adapters.cart_adapters.AvailableItemsListSelectedStoreRadiusAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilitySelectedStoresAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresAdapter;
import com.system.user.menwain.adapters.cart_adapters.ItemsAvailabilityStoresRadiusAdapter;
import com.system.user.menwain.others.Preferences;
import com.system.user.menwain.R;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentSaveList;
import com.system.user.menwain.fragments.cart.dialog_fragments.DialogFragmentTimeSlots;
import com.system.user.menwain.responses.cart.AvailNotAvailRadiusResponse;
import com.system.user.menwain.responses.cart.AvailNotAvailResponse;
import com.system.user.menwain.responses.cart.CalculateShippingCostResponse;
import com.system.user.menwain.responses.cart.SelectedStoreProductsResponse;
import com.system.user.menwain.utils.URLs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AvailNotAvailItemsListsFragment extends Fragment implements View.OnClickListener {
    private TextView mAvailable, mNotAvailable;
    public static TextView mTotalAmount, tvStoreName, mAvailItems, mNotAvailItmes, mSaved;
    private ImageView mBackBtn;
    private LinearLayout mConfirmBtn;
    private int store_id, avail, not_avial;
    public String price, distance, final_dist;
    String[] split_dist, dist;
    Bundle bundle;
    Preferences prefrences;
    private int pay_status;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private List<AvailNotAvailResponse.Datum.Available> avail_items_list = ItemsAvailabilityStoresAdapter.available_list;
    private List<AvailNotAvailRadiusResponse.Datum.Available> avail_items_list_radius = ItemsAvailabilityStoresRadiusAdapter.available_list;
    private List<SelectedStoreProductsResponse.Datum.Available> selected_store_avail_items_list_radius = ItemsAvailabilitySelectedStoresAdapter.available_list;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_avail_not_avial_items_lists, container, false);
        customDialog(getContext());
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
        prefrences = new Preferences(getContext());
        getParentFragmentManager().beginTransaction().replace(R.id.container_items_list, new AvailableItemsFragment()).commit();

        tvStoreName = view.findViewById(R.id.store_name_avial_not_avial);
        tvStoreName.setText(prefrences.getStoreName());
        mTotalAmount = view.findViewById(R.id.tv_total_amount_avial_items);
        mTotalAmount.setText(price);

        mAvailable = view.findViewById(R.id.available_items);
        mNotAvailable = view.findViewById(R.id.not_available_items);
        mBackBtn = view.findViewById(R.id.iv_back_avail_not_avail);
        mConfirmBtn = view.findViewById(R.id.confirm_btn_items_list);
        mAvailItems = view.findViewById(R.id.count_avail_items);
        mAvailItems.setText(avail + "");
        mNotAvailItmes = view.findViewById(R.id.count_not_avail_items);
        mNotAvailItmes.setText(not_avial + "");
        mSaved = view.findViewById(R.id.tv_saved);

        mAvailable.setOnClickListener(this);
        mNotAvailable.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        mBackBtn.setOnClickListener(this);


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
            int avail_items = Integer.parseInt(mAvailItems.getText().toString());
            if (avail_items == 0) {
                Toast.makeText(getContext(), "" + getString(R.string.no_items_available), Toast.LENGTH_SHORT).show();
            } else {
                pay_status = prefrences.getPayRBtnStatus();
                prefrences.setTotalAmount(Integer.parseInt(mTotalAmount.getText().toString()));
                calculateShippingCost();
            }
        } else if (id == R.id.iv_back_avail_not_avail) {
            prefrences.setCartFragStatus(2);
            getFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ItemsAvailabilityStoresFragment()).addToBackStack(null).commit();
        }
    }

    private void calculateShippingCost() {
        dialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        final Gson gson = new GsonBuilder().create();
        StringRequest request = new StringRequest(Request.Method.POST, URLs.calcualte_shipping_cost, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                CalculateShippingCostResponse shippingCostResponse = gson.fromJson(response, CalculateShippingCostResponse.class);
                prefrences.setShippingCost(shippingCostResponse.getShippingcost());

                if (pay_status == 5) {
                    getParentFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new RouteToStoreFragment()).addToBackStack(null).commit();
                    /*DialogFragmentSaveList dialogFragmentSaveList = new DialogFragmentSaveList();
                    dialogFragmentSaveList.show(getFragmentManager(), "Purchasing Method");*/
                } else {
                    //if (pay_status == 2){
                    DialogFragmentTimeSlots deliveryTime = new DialogFragmentTimeSlots();
                    deliveryTime.show(getParentFragmentManager(), "Select Method");
                }
                dialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("AvailNotAvailError", error.toString());
                try {
                    if (error instanceof TimeoutError) {
                        Toast.makeText(getContext(), getString(R.string.network_timeout), Toast.LENGTH_LONG).show();
                    } else if (error instanceof AuthFailureError) {
                        Toast.makeText(getContext(), getString(R.string.authentication_error), Toast.LENGTH_LONG).show();
                    } else if (error instanceof ServerError) {
                        Toast.makeText(getContext(), getString(R.string.server_error), Toast.LENGTH_LONG).show();
                    } else if (error instanceof NetworkError || error instanceof NoConnectionError) {
                        Toast.makeText(getContext(), getString(R.string.no_network_found), Toast.LENGTH_LONG).show();
                    } else {
                    }
                    dialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dialog.dismiss();
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

    public void customDialog(Context context) {
        builder = new AlertDialog.Builder(context);
        builder.setCancelable(false); // if you want user to wait for some process to finish,
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setView(R.layout.layout_loading_dialog);
        }
        dialog = builder.create();
    }

}
